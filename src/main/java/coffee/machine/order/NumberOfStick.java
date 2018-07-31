package coffee.machine.order;

public enum NumberOfStick {
	NO_STICK(""), 
	ONE_STICK("0");

	public final String codeRepresentation;

	private NumberOfStick(final String stickCode) {
		codeRepresentation = stickCode;
	}
}