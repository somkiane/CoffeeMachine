package coffee.machine.order;

import static coffee.machine.order.Money.euro;

public enum Drink {

	TEA("Tea", "T", euro(0.4)), 
	COFFEE("Coffee", "C", euro(0.6)), 
	CHOCOLATE("Chocolate", "H", euro(0.5)),
	ORANGE_JUICE("Orange Juice", "O", euro(0.6)),
	EXTRA_HOT_TEA("Extra Hot Tea", "Th", euro(0.4)), 
	EXTRA_HOT_COFFEE("Extra Hot Coffee", "Ch", euro(0.6)), 
	EXTRA_HOT_CHOCOLATE("Extra Hot Chocolate", "Hh", euro(0.5));
	
	public final String name;
	public final String codeRepresentation;
	public final Money price;
	
	private Drink(final String name, final String drinkCode, final Money price) {
		this.name = name;
		this.codeRepresentation = drinkCode;
		this.price = price;
	}

	public Money missingAmountFrom(final Money amount) {
		return price.subtract(amount);
	}
	
}