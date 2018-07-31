package coffee.machine.order;

import static coffee.machine.order.SugarQuantity.ONE_SUGAR;
import static coffee.machine.order.SugarQuantity.TWO_SUGARS;
import static coffee.machine.order.SugarQuantity.ZERO_SUGAR;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class SugarQuantityTest {
	
	@Test
	public void number_of_sugar_should_determine_the_number_of_stick() {
		assertThat(ZERO_SUGAR.codeRepresentation).isEqualTo(":");
		assertThat(ONE_SUGAR.codeRepresentation).isEqualTo("1:0");
		assertThat(TWO_SUGARS.codeRepresentation).isEqualTo("2:0");
	}
}
