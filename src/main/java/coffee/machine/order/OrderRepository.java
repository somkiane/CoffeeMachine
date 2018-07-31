package coffee.machine.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderRepository {

	private final List<Order> orderList = new ArrayList<>();

	public final void record(final Order order) {
		orderList.add(order);
	}

	public final List<Order> listDrinkOrders() {
		return Collections.unmodifiableList(orderList);
	}

}
