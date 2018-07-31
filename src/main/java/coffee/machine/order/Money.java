package coffee.machine.order;

import static java.math.BigDecimal.valueOf;

import java.math.BigDecimal;

public class Money {

	private BigDecimal amount;

	private Money(double amount){
		this.amount = valueOf(amount);
	}
	
	private Money(BigDecimal amount) {
		this.amount = amount;
	}

	public static Money euro(double amount) {
		return new Money(amount);
	}

	public Money subtract(Money someAmount) {
		return new Money(amount.subtract(someAmount.amount));
	}

	public Money times(int count) {
		return new Money(amount.multiply(valueOf(count)));
	}

	public Money plus(Money someMoney) {
		return new Money(amount.add(someMoney.amount));
	}

	public boolean isStrictlyPositive() {
		return amount.signum() > 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!getClass().equals(obj.getClass())) {
			return false;
		} 
			
		Money money = (Money) obj;
		return amount.equals(money.amount);
	}
	
	@Override
	public int hashCode() {
		return amount.hashCode();
	}

	@Override
	public String toString() {
		return String.valueOf(amount);
	}

}
