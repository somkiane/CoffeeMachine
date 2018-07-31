package coffee.machine.reporting;

import static coffee.machine.order.Money.euro;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import coffee.machine.order.Order;
import coffee.machine.order.Money;
import coffee.machine.order.OrderRepository;

public class Report {

	private final OrderRepository orderRepository;

	public Report(final OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	public final void print(final PrintStream printStream) {
		final List<Order> orderList = orderRepository.listDrinkOrders();
		printStream.println(format(orderList));
	}

	private String format(final List<Order> orderList) {
		String headerLine =         "+---------------------+-------+-----------------+-----------------+\n" + 
                                    "| Drink               | Qty   | u. price (euro) | SubTotal (euro) |\n" + 
                                    "+---------------------+-------+-----------------+-----------------+\n";
		String detailLineTemplate = "| %-19s | %5s | %15s | %15s |\n";
		String totalLineTemplate =  "+---------------------+-------+-----------------+-----------------+\n" + 
                                    "|                                   Total Sales = %15s |\n" + 
                                    "+-----------------------------------------------------------------+\n";
		
		String detailLines = "";
		DrinksTracker tracker = new DrinksTracker(orderList);
		Money total = euro(0);
		
		for (DrinkCounter drinkCounter : tracker.listDrinkCounter()) {
			Money subTotal = drinkCounter.drinkPrice.times(drinkCounter.count());
			
			detailLines += String.format(detailLineTemplate, 
					drinkCounter.drink, 
					drinkCounter.count(),
					drinkCounter.drinkPrice,
					subTotal) ;
			total = total.plus(subTotal);
		}
		
		return headerLine + detailLines + String.format(totalLineTemplate, total);
	}
	
	class DrinksTracker {

		private final Map<String, DrinkCounter> tracker = new HashMap<>();
		private final List<DrinkCounter> counterList = new ArrayList<>();
		
		public DrinksTracker(final List<Order> orderList) {
			for (Order order : orderList) {
				increment(order.drink(), order.drinkPrice());
			}
		}

		private void increment(final String drink, final Money drinkPrice) {
			if (!tracker.containsKey(drink)) {
				DrinkCounter value = new DrinkCounter(drink, drinkPrice);
				tracker.put(drink, value);
				counterList.add(value);
			}
			tracker.get(drink).incrementByOne();
		}

		public Collection<DrinkCounter> listDrinkCounter() {
			return Collections.unmodifiableCollection(counterList);
		}

	}

	class DrinkCounter {

		public final String drink;
		public final Money drinkPrice;
		private int count;
		
		public DrinkCounter(final String drink, final Money unitPrice) {
			this.drink = drink;
			this.drinkPrice = unitPrice;
			count = 0;
		}

		public void incrementByOne() {
			count++;
		}
		
		public int count() {
			return count;
		}
	}
}
