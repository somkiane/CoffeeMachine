package coffee.machine.order;

import static coffee.machine.order.Drink.CHOCOLATE;
import static coffee.machine.order.Drink.COFFEE;
import static coffee.machine.order.Drink.EXTRA_HOT_CHOCOLATE;
import static coffee.machine.order.Drink.EXTRA_HOT_COFFEE;
import static coffee.machine.order.Drink.EXTRA_HOT_TEA;
import static coffee.machine.order.Drink.ORANGE_JUICE;
import static coffee.machine.order.Drink.TEA;
import static coffee.machine.order.Money.euro;
import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

public class DrinkTest {

	@Test
	public void name_and_code_representation_of_drinks(){
		
		assertThat(TEA.name).isEqualTo("Tea");
		assertThat(TEA.codeRepresentation).isEqualTo("T");
		assertThat(TEA.price).isEqualTo(euro(0.4));
		
		assertThat(COFFEE.name).isEqualTo("Coffee");
		assertThat(COFFEE.codeRepresentation).isEqualTo("C");
		assertThat(COFFEE.price).isEqualTo(euro(0.6));
		
		assertThat(CHOCOLATE.name).isEqualTo("Chocolate");
		assertThat(CHOCOLATE.codeRepresentation).isEqualTo("H");
		assertThat(CHOCOLATE.price).isEqualTo(euro(0.5));
		
		assertThat(ORANGE_JUICE.name).isEqualTo("Orange Juice");
		assertThat(ORANGE_JUICE.codeRepresentation).isEqualTo("O");
		assertThat(ORANGE_JUICE.price).isEqualTo(euro(0.6));
		
		assertThat(EXTRA_HOT_TEA.name).isEqualTo("Extra Hot Tea");
		assertThat(EXTRA_HOT_TEA.codeRepresentation).isEqualTo("Th");
		assertThat(EXTRA_HOT_TEA.price).isEqualTo(euro(0.4));
		
		assertThat(EXTRA_HOT_COFFEE.name).isEqualTo("Extra Hot Coffee");
		assertThat(EXTRA_HOT_COFFEE.codeRepresentation).isEqualTo("Ch");
		assertThat(EXTRA_HOT_COFFEE.price).isEqualTo(euro(0.6));
		
		assertThat(EXTRA_HOT_CHOCOLATE.name).isEqualTo("Extra Hot Chocolate");
		assertThat(EXTRA_HOT_CHOCOLATE.codeRepresentation).isEqualTo("Hh");
		assertThat(EXTRA_HOT_CHOCOLATE.price).isEqualTo(euro(0.5));
		
	}
	
	@Test
	public void calculate_missing_money() {
		assertThat(TEA.missingAmountFrom(euro(0.1))).isEqualTo(euro(0.3));
		assertThat(TEA.missingAmountFrom(euro(0.5))).isEqualTo(euro(-0.1));
	}
}
