package coffee.machine.order;

import static coffee.machine.OrderBuilder.anOrderOf;
import static coffee.machine.order.Drink.TEA;
import static coffee.machine.order.Money.euro;
import static coffee.machine.order.SugarQuantity.ONE_SUGAR;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class OrderTest {

	private static Money UNSUFFICIANT_AMOUNT = euro(0.1);
			
	private Order order = anOrderOf(TEA).with(ONE_SUGAR)
			                            .at(UNSUFFICIANT_AMOUNT);

	@Test
	public void should_return_drink_name() {
		assertThat(order.drink()).isEqualTo(TEA.name);
	}
	
	@Test
	public void should_return_drink_price() {
		assertThat(order.drinkPrice()).isEqualTo(TEA.price);
	}

	@Test
	public void should_return_drink_code() {
		assertThat(order.drinkCode()).isEqualTo(TEA.codeRepresentation);
	}

	@Test
	public void should_return_number_of_sugar_code() {
		assertThat(order.numberOfSugarCode()).isEqualTo(ONE_SUGAR.codeRepresentation);
	}

	@Test
	public void should_calculateMissingAmount() {
		assertThat(order.missingAmount()).isEqualTo(
				TEA.missingAmountFrom(UNSUFFICIANT_AMOUNT));
	}
	
	@Test
	public void should_test_whether_the_amount_is_sufficient() {
		assertThat(order.hasNoSufficientAmount()).isTrue();
	}

}
