package coffee.machine;

import static coffee.machine.order.SugarQuantity.ZERO_SUGAR;
import coffee.machine.order.Drink;
import coffee.machine.order.Money;
import coffee.machine.order.Order;
import coffee.machine.order.SugarQuantity;

public class OrderBuilder {

	private Drink drink;
	private SugarQuantity sugarQty = ZERO_SUGAR;
	
	public OrderBuilder(Drink drink) {
		this.drink = drink;
	}

	public static OrderBuilder anOrderOf(Drink drink) {
		return new OrderBuilder(drink);
	}

	public Order at(Money insertedAmount) {
		return new Order(drink, sugarQty, insertedAmount);
	}

	public OrderBuilder with(SugarQuantity sugarQty) {
		this.sugarQty = sugarQty;
		return this;
	}

}
