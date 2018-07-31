package coffee.machine.order;

public class Order {

	private final Drink drink;
	private final SugarQuantity sugarQuantity;
	private final Money insertedAmount;

	public Order(final Drink drink, final SugarQuantity sugarQuantity,
			final Money insertedAmount) {
		this.drink = drink;
		this.sugarQuantity = sugarQuantity;
		this.insertedAmount = insertedAmount;
	}

	public final boolean hasNoSufficientAmount() {
		return missingAmount().isStrictlyPositive();
	}
	
	public final String drink() {
		return drink.name;
	}
	
	public final Money drinkPrice() {
		return drink.price;
	}

	public final String drinkCode() {
		return drink.codeRepresentation;
	}
	
	public final String numberOfSugarCode() {
		return sugarQuantity.codeRepresentation;
	}

	public final Money missingAmount() {
		return drink.missingAmountFrom(insertedAmount);
	}

}
