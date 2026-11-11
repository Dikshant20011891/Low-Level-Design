 package vendingmachine;

import vendingmachine.entity.Inventory;
import vendingmachine.entity.Item;
import vendingmachine.enums.Coin;
import vendingmachine.state.IdleState;
import vendingmachine.state.VendingMachineState;

public class VendingMachine {
	private final static VendingMachine INSTANCE = new VendingMachine();
	private final Inventory inventory = new Inventory();
	private VendingMachineState currentVendingMachineState;
	private int balance = 0;
	private String selectedItemCode;
	
	public VendingMachine() {
		currentVendingMachineState = new IdleState(this);
	}
	
	public static VendingMachine getInstance() {
		return INSTANCE;
	}
	
	public void insertCoin(Coin coin) {
		currentVendingMachineState.insertCoin(coin);
	}
	
	public Item addItem(String code, String name, int price, int quantity) {
		Item item = new Item(code,name,price);
		inventory.addItem(code, item, quantity);
		return item;
	}
	
	public void dispense() {
		currentVendingMachineState.dispense();
	}
	
	public void selectItem(String code) {
		currentVendingMachineState.selectItem(code);
	}
	
	public void dispenseItem() {
		Item item = inventory.getItem(selectedItemCode);
		if(balance >= item.getPrice()) {
			inventory.reduceStock(selectedItemCode);
			balance -= item.getPrice();
			System.out.println("Dispensed : " + item.getName());
			
			if(balance > 0)
				System.out.println("Returning Change: " + balance);
		}
		
		reset();
		setState(new IdleState(this));
	}
	
	public void refundBalance() {
		System.out.println("Refunding : " + balance);
	}
	
	public void reset() {
		selectedItemCode = null;
		balance = 0;
	}
	
	public void addBalance(int value) {
		balance += value;
	}
	
	public Item getSelectedItem() {
		return inventory.getItem(selectedItemCode);
	}
	
	public void setSelectedItemCode(String code) {
		this.selectedItemCode = code;
	}
	
	public void setState(VendingMachineState vendingMachineState) {
		this.currentVendingMachineState = vendingMachineState;
	}

	public int getBalance() {
		return balance;
	}

	public Inventory getInventory() {
		return inventory;
	}
}
