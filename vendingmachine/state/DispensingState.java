package vendingmachine.state;

import vendingmachine.VendingMachine;
import vendingmachine.enums.Coin;

public class DispensingState extends VendingMachineState{
	public DispensingState(VendingMachine machine) {
		super(machine);
	}

	@Override
	public void insertCoin(Coin coin) {
		System.out.println("Currently dispensing. Please wait");
	}

	@Override
	public void selectItem(String code) {
		System.out.println("Currently dispensing. Please wait");
	}

	@Override
	public void dispense() {
		// already triggered by has money
	}

	@Override
	public void refund() {
		// TODO Auto-generated method stub
		System.out.println("Dispensing in progress. Refund not allowed");
	}
}
