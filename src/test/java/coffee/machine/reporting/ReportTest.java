package coffee.machine.reporting;

import static coffee.machine.OrderBuilder.anOrderOf;
import static coffee.machine.order.Drink.ORANGE_JUICE;
import static coffee.machine.order.Drink.TEA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.PrintStream;

import org.junit.Test;

import coffee.machine.order.Order;
import coffee.machine.order.OrderRepository;

public class ReportTest {
	@Test
	public void 
	should_print_that_two_teas_and_one_orange_juice_was_ordred_for_a_total_sale_of_1_euro_4_cents() {
		
		Order orangeOrder = anOrderOf(ORANGE_JUICE).at(ORANGE_JUICE.price);
		Order teaOrder = anOrderOf(TEA).at(TEA.price);
		Order teaOrder2 = anOrderOf(TEA).at(TEA.price);
		
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
		
		OrderRepository orderRepository = new OrderRepository();
		orderRepository.record(orangeOrder);
		orderRepository.record(teaOrder);
		orderRepository.record(teaOrder2);
		new Report(orderRepository ).print(printStreamMock);
		
		verify(printStreamMock).println(expectedOutput);
	}

}
