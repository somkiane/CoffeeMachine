package coffee.machine.order;

import static coffee.machine.order.Money.euro;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class MoneyTest {

	@Test
	public void multiplication() {
		Money five = euro(5);
		assertThat(five.times(2)).isEqualTo(euro(10));
	}

	@Test
	public void addition() {
		Money five = euro(5);
		Money six = euro(6);
		assertThat(five.plus(six)).isEqualTo(euro(11));
	}

	@Test
	public void subtraction() {
		Money five = euro(5);
		Money six = euro(6);
		assertThat(six.subtract(five)).isEqualTo(euro(1));
	}

	@Test
	public void equality() {
		assertThat(euro(5)).isEqualTo(euro(5));
		assertThat(euro(6)).isNotEqualTo(euro(5));
		assertThat(euro(6)).isNotEqualTo(new Object());
	}
	
	@Test
	public void to_string() {
		assertThat(euro(-5).toString()).isEqualTo("-5.0");
		assertThat(euro(5.23).toString()).isEqualTo("5.23");
		
	}
	
	@Test
	public void sign() {
		assertThat(euro(-5).isStrictlyPositive()).isFalse();
		assertThat(euro(5).isStrictlyPositive()).isTrue();
		
	}

}
