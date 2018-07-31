package coffee.machine.order;

import static coffee.machine.order.NumberOfStick.NO_STICK;
import static coffee.machine.order.NumberOfStick.ONE_STICK;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class NumberOfStickTest {

	@Test
	public void code_representation_of_number_of_stick() {
		assertThat(NO_STICK.codeRepresentation).isEqualTo("");
		assertThat(ONE_STICK.codeRepresentation).isEqualTo("0");
	}
}
