package coffee.machine;

import static coffee.machine.OrderBuilder.anOrderOf;
import static coffee.machine.order.Drink.ORANGE_JUICE;
import static coffee.machine.order.Drink.TEA;
import static coffee.machine.order.Money.euro;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import coffee.machine.interfaces.BeverageQuantityChecker;
import coffee.machine.interfaces.DrinkMaker;
import coffee.machine.interfaces.EmailNotifier;
import coffee.machine.order.Drink;
import coffee.machine.order.Order;
import coffee.machine.order.OrderRepository;
import coffee.machine.order.SugarQuantity;
import coffee.machine.reporting.Report;

@RunWith(JUnitParamsRunner.class)
public class CoffeeMachineOrchestratorTest {
	
	@Mock private DrinkMaker drinkMaker;
	private CoffeeMachineOrchestrator coffeeMachineOchestrator;
	private OrderRepository orderRepository;
	@Mock private BeverageQuantityChecker beverageQuantityChecker;
	@Mock private EmailNotifier emailNotifier;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		makeOrchestrator();
		stubNoDrinkShortage();
	}
	
	@Test
	@Parameters({
		"TEA, T::",
        "COFFEE, C::",
        "CHOCOLATE, H::",
        "ORANGE_JUICE, O::",
        "EXTRA_HOT_TEA, Th::",
        "EXTRA_HOT_COFFEE, Ch::",
        "EXTRA_HOT_CHOCOLATE, Hh::"
		})
	public void 
	should_send_proper_protocol_when_a_drink_is_ordered_at_exact_price(Drink drink, String protocol) {
		
		coffeeMachineOchestrator.process(anOrderOf(drink).at(drink.price));
		
		verify(drinkMaker).executeInstruction(protocol);
	}

	@Test
	@Parameters({
		"TEA, ONE_SUGAR, T:1:0",
		"COFFEE, ONE_SUGAR, C:1:0",
		"CHOCOLATE, ONE_SUGAR, H:1:0",
		"EXTRA_HOT_TEA, ONE_SUGAR, Th:1:0",
		"EXTRA_HOT_COFFEE, ONE_SUGAR, Ch:1:0",
		"EXTRA_HOT_CHOCOLATE, ONE_SUGAR, Hh:1:0",
		
		"TEA, TWO_SUGARS, T:2:0",
		"COFFEE, TWO_SUGARS, C:2:0",
		"CHOCOLATE, TWO_SUGARS, H:2:0",
		"EXTRA_HOT_TEA, TWO_SUGARS, Th:2:0",
		"EXTRA_HOT_COFFEE, TWO_SUGARS, Ch:2:0",
		"EXTRA_HOT_CHOCOLATE, TWO_SUGARS, Hh:2:0"
	})
	public void 
	should_send_proper_protocol_when_a_drink_is_ordered_with_some_sugar(Drink drink, SugarQuantity sugarQty, String protocol) {
		
		coffeeMachineOchestrator.process(anOrderOf(drink).with(sugarQty).at(drink.price));
		
		verify(drinkMaker).executeInstruction(protocol);
	}
	
	@Test
	@Parameters({
		"TEA, M:The amount of money missing is 0.4 euro(s) for your Tea.",
		"COFFEE, M:The amount of money missing is 0.6 euro(s) for your Coffee.",
		"CHOCOLATE, M:The amount of money missing is 0.5 euro(s) for your Chocolate.",
		"EXTRA_HOT_TEA, M:The amount of money missing is 0.4 euro(s) for your Extra Hot Tea.",
		"EXTRA_HOT_COFFEE, M:The amount of money missing is 0.6 euro(s) for your Extra Hot Coffee.",
		"EXTRA_HOT_CHOCOLATE, M:The amount of money missing is 0.5 euro(s) for your Extra Hot Chocolate.",
	})
	public void 
	should_send_messenge_of_unsufficient_amount_when_drink_is_ordered_with_no_amount(Drink drink, String expectedMessage) {
		
		coffeeMachineOchestrator.process(anOrderOf(drink).at(euro(0.0)));
		
		verify(drinkMaker).executeInstruction(expectedMessage);
	}

	@Test
	@Parameters({
		"TEA, M:The amount of money missing is 0.3 euro(s) for your Tea.",
		"COFFEE, M:The amount of money missing is 0.5 euro(s) for your Coffee.",
		"CHOCOLATE, M:The amount of money missing is 0.4 euro(s) for your Chocolate.",
		"EXTRA_HOT_TEA, M:The amount of money missing is 0.3 euro(s) for your Extra Hot Tea.",
		"EXTRA_HOT_COFFEE, M:The amount of money missing is 0.5 euro(s) for your Extra Hot Coffee.",
		"EXTRA_HOT_CHOCOLATE, M:The amount of money missing is 0.4 euro(s) for your Extra Hot Chocolate.",
	})
	public void 
	should_send_messenge_of_unsufficient_amount_when_drink_is_ordered_with_one_cent(Drink drink, String expectedMessage) {
		
		coffeeMachineOchestrator.process(anOrderOf(drink).at(euro(0.1)));
		
		verify(drinkMaker).executeInstruction(expectedMessage);
	}

	@Test
	public void 
	should_get_the_two_drink_orders_from_the_repository() {
		
		Order orangeOrder = anOrderOf(ORANGE_JUICE).at(ORANGE_JUICE.price);
		Order teaOrder = anOrderOf(TEA).at(TEA.price);
		
		List<Order> expectedOrders = new ArrayList<>(2);
		expectedOrders.add(orangeOrder);
		expectedOrders.add(teaOrder);

		coffeeMachineOchestrator.process(orangeOrder);
		coffeeMachineOchestrator.process(teaOrder);

		assertThat(orderRepository.listDrinkOrders()).isEqualTo(expectedOrders);
	}

	@Test
	public void 
	should_print_that_two_teas_and_one_orange_juice_was_ordred_for_a_total_sale_of_1_euro_4_cents() {
		
		Order orangeOrder = anOrderOf(ORANGE_JUICE).at(ORANGE_JUICE.price);
		Order teaOrder = anOrderOf(TEA).at(TEA.price);
		Order teaOrder2 = anOrderOf(TEA).at(TEA.price);
		
		List<Order> expectedOrders = new ArrayList<>(3);
		expectedOrders.add(orangeOrder);
		expectedOrders.add(teaOrder);
		expectedOrders.add(teaOrder2);

		coffeeMachineOchestrator.process(orangeOrder);
		coffeeMachineOchestrator.process(teaOrder);
		coffeeMachineOchestrator.process(teaOrder2);

		String expectedOutput = 
				  "+---------------------+-------+-----------------+-----------------+\n"
				+ "| Drink               | Qty   | u. price (euro) | SubTotal (euro) |\n"
				+ "+---------------------+-------+-----------------+-----------------+\n"
				+ "| Orange Juice        |     1 |             0.6 |             0.6 |\n"
				+ "| Tea                 |     2 |             0.4 |             0.8 |\n"
				+ "+---------------------+-------+-----------------+-----------------+\n"
				+ "|                                   Total Sales =             1.4 |\n"
				+ "+-----------------------------------------------------------------+\n";

		PrintStream printStreamMock = mock(PrintStream.class);
		
		new Report(orderRepository).print(printStreamMock);
		
		verify(printStreamMock).println(expectedOutput);
	}

	@Test
	public void 
	should_notify_the_user_and_send_an_email_when_there_is_a_shortage_of_tea() {
		
		stubDrinkShortage();
		
		coffeeMachineOchestrator.process(anOrderOf(TEA).at(euro(0.4)));

		verify(emailNotifier).notifyMissingDrink("Tea");
		verify(drinkMaker).executeInstruction("M:There is a shortage of Tea");
	}
	
	private void makeOrchestrator() {
		orderRepository = new OrderRepository();
		
		coffeeMachineOchestrator = new CoffeeMachineOrchestrator(
				drinkMaker,
				orderRepository, 
				beverageQuantityChecker, 
				emailNotifier);
	}
	
	private void stubNoDrinkShortage() {
		when(beverageQuantityChecker.isEmpty(anyString())).thenReturn(false);
	}
	
	private void stubDrinkShortage() {
		when(beverageQuantityChecker.isEmpty(anyString())).thenReturn(true);
	}
	
}

