package designPattern;

/*The Chain of Responsibility Design Pattern is a behavioral pattern that lets 
you pass requests along a chain of handlers, allowing each handler to decide 
whether to process the request or pass it to the next handler in the chain.*/

class CashRequest{
	public int amount;
	
	public CashRequest(int amount) {
		this.amount = amount;
	}
}

interface CashHandler{
	void setNext(CashHandler next);
	void dispense(CashRequest requent);
}

abstract class BaseClassHandler implements CashHandler{
	protected CashHandler next;
	protected int denomination;
	
	public BaseClassHandler(int denomination) {
		this.denomination = denomination;
	}
	
	@Override
	public void setNext(CashHandler next) {
		this.next = next;
	}
	
	@Override
	public void dispense(CashRequest request) {
		if(request.amount >= denomination) {
			int noteCount = request.amount/denomination;
			request.amount = request.amount % denomination;
			System.out.println("Dispensing " + noteCount + " x $" + denomination);
		}
		
		forward(request);
	}
	
	protected void forward(CashRequest request) {
		if(next != null)
			next.dispense(request);
	}
}

class HundredDollarHandler extends BaseClassHandler{
	public HundredDollarHandler(){ super(100);}
}

class FiftyDollarHandler extends BaseClassHandler{
	public FiftyDollarHandler(){ super(50);}
}

class TwentyDollarHandler extends BaseClassHandler{
	public TwentyDollarHandler(){ super(20);}
}

class TenDollarHandler extends BaseClassHandler{
	public TenDollarHandler(){ super(10);}
}

public class ChainOfResponsibility {
	public static void main(String[] args) {
		HundredDollarHandler hundreds = new HundredDollarHandler();
		FiftyDollarHandler fifties = new FiftyDollarHandler();
		TwentyDollarHandler twenties = new TwentyDollarHandler();
		TenDollarHandler tens = new TenDollarHandler();
		
		hundreds.setNext(fifties);
		fifties.setNext(twenties);
		twenties.setNext(tens);
		
		System.out.println("---- Withdrawing $380 ----");
		CashRequest req = new CashRequest(380);
		hundreds.dispense(req);
		System.out.println("Remaining: $ " + req.amount);
		
		
		System.out.println("---- Withdrawing $275 ----");
		CashRequest req2 = new CashRequest(275);
		hundreds.dispense(req2);
		System.out.println("Remaining: $ " + req2.amount);
		
	}
}
