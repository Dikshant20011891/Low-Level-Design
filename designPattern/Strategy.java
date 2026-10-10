package designPattern;

/*The Strategy Pattern defines a family of algorithms, encapsulates each one, and 
makes them interchangeable. Strategy lets the algorithm vary independently from 
clients that use it.*/

interface Payment{
	Boolean pay(double amount);
}

class CreditCardPayment implements Payment{
	private final String cardNumber;
	private final String expiryDate;
	
	public CreditCardPayment(String cardNumber,String expiryDate) {
		this.cardNumber = cardNumber;
		this.expiryDate = expiryDate;
	}
	
	public Boolean pay(double amount) {
		System.out.println("Charging " + amount + " to credit card ending in "
				+ cardNumber.substring(cardNumber.length() - 4));
		return true;
	}
}

class PayPalPayment implements Payment{
	private final String email;
	
	public PayPalPayment(String email) {
		this.email = email;
	}
	
	public Boolean pay(double amount) {
		System.out.println("Sending " + amount + " via PayPal to  "
				+ email);
		return true;
	}
}

class CryptoPayment implements Payment{
	private final String walletAddress;
	
	public CryptoPayment(String walletAddress) {
		this.walletAddress = walletAddress;
	}
	
	public Boolean pay(double amount) {
		System.out.println("Transferring " + amount + " in Cyrpt to "
				+ walletAddress);
		return true;
	}
}

class CheckoutService{
	private Payment payment;
	
	public CheckoutService(Payment payment) {
		this.payment = payment;
	}
	
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	public boolean checkout(double amount) {
		return payment.pay(amount);
	}
}

public class Strategy {
	public static void main(String[] args) {
		CheckoutService checkout = 
				new CheckoutService(new CreditCardPayment("112782738187837", "12/26"));
		
		checkout.checkout(1000);
		
		checkout.setPayment(new PayPalPayment("admin@email.com"));
		checkout.checkout(2000);
		
		checkout.setPayment(new CryptoPayment("1600 Pennsylvania Avenue NW, Washington, DC"));
		checkout.checkout(3000);
	}
}

/*
 * Bad Code
 * 
 * class CheckoutService { public boolean checkout(String paymentType, double
 * amount, String cardNumber, String expiryDate, String email, String
 * walletAddress) {
 * 
 * if (paymentType.equalsIgnoreCase("CREDIT_CARD")) {
 * 
 * System.out.println("Charging " + amount + " to credit card ending in " +
 * cardNumber.substring(cardNumber.length() - 4));
 * 
 * return true;
 * 
 * } else if (paymentType.equalsIgnoreCase("PAYPAL")) {
 * 
 * System.out.println("Sending " + amount + " via PayPal to " + email);
 * 
 * return true;
 * 
 * } else if (paymentType.equalsIgnoreCase("CRYPTO")) {
 * 
 * System.out.println("Transferring " + amount + " in Crypto to " +
 * walletAddress);
 * 
 * return true;
 * 
 * } else {
 * 
 * System.out.println("Invalid payment method"); return false; } } }
 * 
 * public class Strategy { public static void main(String[] args) {
 * CheckoutService checkout = new CheckoutService();
 * 
 * checkout.checkout("CREDIT_CARD", 1000, "112782738187837", "12/26", null,
 * null);
 * 
 * checkout.checkout("PAYPAL", 2000, null, null, "admin@email.com", null);
 * 
 * checkout.checkout("CRYPTO", 3000, null, null, null, "crypto-wallet-123"); } }
 */