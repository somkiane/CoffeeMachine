package coffee.machine.order;

import static coffee.machine.OrderBuilder.anOrderOf;
import static coffee.machine.order.Drink.ORANGE_JUICE;
import static coffee.machine.order.Drink.TEA;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class OrderRepositoryTest {
	
	@Test
	public void 
	should_get_the_two_drink_orders_from_the_repository() {
		
		Order orangeOrder = anOrderOf(ORANGE_JUICE).at(ORANGE_JUICE.price);
		Order teaOrder = anOrderOf(TEA).at(TEA.price);
		
		List<Order> expectedOrders = new ArrayList<>(2);
		expectedOrders.add(orangeOrder);
		expectedOrders.add(teaOrder);

		OrderRepository orderRepository = new OrderRepository();
		orderRepository.record(orangeOrder);
		orderRepository.record(teaOrder);
		
		assertThat(orderRepository .listDrinkOrders()).isEqualTo(expectedOrders);
	}

}
