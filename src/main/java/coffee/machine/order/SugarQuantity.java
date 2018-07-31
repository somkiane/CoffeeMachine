package coffee.machine.order;

import static coffee.machine.order.NumberOfStick.ONE_STICK;
import static coffee.machine.order.NumberOfStick.NO_STICK;

public enum SugarQuantity {
	ZERO_SUGAR("", NO_STICK), 
	ONE_SUGAR("1", ONE_STICK), 
	TWO_SUGARS("2", ONE_STICK);
	
	public final String codeRepresentation;
	
	private SugarQuantity(final String numberOfSugarRepresentation, final NumberOfStick numberOfStick) {
		codeRepresentation = numberOfSugarRepresentation + ":" + numberOfStick.codeRepresentation;
	}
}