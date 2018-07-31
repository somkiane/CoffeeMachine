package coffee.machine;

import coffee.machine.interfaces.BeverageQuantityChecker;
import coffee.machine.interfaces.DrinkMaker;
import coffee.machine.interfaces.EmailNotifier;
import coffee.machine.order.Order;
import coffee.machine.order.Money;
import coffee.machine.order.OrderRepository;

public class CoffeeMachineOrchestrator {

	private final DrinkMaker drinkMaker;
	private final OrderRepository orderRepository;
	private final BeverageQuantityChecker beverageQuantityChecker;
	private final EmailNotifier emailNotifier;

	public CoffeeMachineOrchestrator(final DrinkMaker drinkMaker,
			final OrderRepository report,
			final BeverageQuantityChecker beverageQuantityChecker,
			final EmailNotifier emailNotifier) {

		this.drinkMaker = drinkMaker;
		this.orderRepository = report;
		this.beverageQuantityChecker = beverageQuantityChecker;
		this.emailNotifier = emailNotifier;
	}

	public final void process(final Order order) {
		
		if (!checkDrinkSupply(order.drink())) { return; }

		if (!checkOrderAmount(order)) { return; }

		makeDrink(order);
		
		recordTransaction(order);
		
	}

	private boolean checkOrderAmount(final Order order) {
		if(order.hasNoSufficientAmount()) {
			notifyMissingAmount(order.missingAmount(), order.drink());
			return false;
		}
		return true;
	}

	private void notifyDrinkShortage(final String drink) {
		sendMessageOfDrinkShortage(drink);
		sendEmailForDrinkShortage(drink);
	}

	private void recordTransaction(final Order order) {
		orderRepository.record(order);
	}

	private void makeDrink(final Order order) {
		drinkMaker.executeInstruction(order.drinkCode() + ":"
				+ order.numberOfSugarCode());
	}

	private boolean checkDrinkSupply(final String drink) {
		if (beverageQuantityChecker.isEmpty(drink)) {
			notifyDrinkShortage(drink);
			return false;
		}
		return true;
	}

	private void notifyMissingAmount(final Money missingAmount,
			final String drink) {
		drinkMaker.executeInstruction("M:The amount of money missing is "
				+ missingAmount + " euro(s) for your " + drink + ".");
	}

	private void sendMessageOfDrinkShortage(final String drink) {
		drinkMaker.executeInstruction("M:There is a shortage of " + drink);
	}

	private void sendEmailForDrinkShortage(final String drink) {
		emailNotifier.notifyMissingDrink(drink);
	}

}
