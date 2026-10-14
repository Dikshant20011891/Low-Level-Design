package designPattern;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*The Observer Design Pattern is a behavioral pattern that defines a one-to-many 
dependency between objects so that when one object (the subject) changes 
its state, all its dependents (observers) are automatically notified and updated.
*/

interface StockObserver{
	void onPriceUpdate(StockExchange exchange);
}

class StockExchange{
	private final Map<String,Double> prices = new HashMap<>();
	private final List<StockObserver> observers = new ArrayList<>();
	private String lastUpdatedSymbol;
	
	public void registerObserver(StockObserver observer) {
		observers.add(observer);
	}
	
	public void removeObserver(StockObserver observer) {
		observers.remove(observer);
	}
	
	private void notifyObservers() {
		for(var observer : observers) {
			observer.onPriceUpdate(this);
		}
	}
	
	public void updatePrice(String symbol,double price) {
		prices.put(symbol,price);
		lastUpdatedSymbol = symbol;
		System.out.println("\nExchange : " + symbol + " updated to $ " + price);
		notifyObservers();
	}
	
	public double getPrice(String symbol) {
		return prices.getOrDefault(symbol, 0.0);
	}
	
	public String getLastUpdatedSymbol() {
		return lastUpdatedSymbol;
	}
}

class PriceDisplay implements StockObserver{
	@Override
	public void onPriceUpdate(StockExchange exchange) {
		String symbol = exchange.getLastUpdatedSymbol();
		System.out.println("Display -> " + symbol + ": $" + exchange.getPrice(symbol));
	}
}

class AlertService implements StockObserver{

	private final Map<String,Double> thresholds = new HashMap<>();
	
	void setAlert(String symbol,double threshold) {
		thresholds.put(symbol, threshold);
	}
	@Override
	public void onPriceUpdate(StockExchange exchange) {
		String symbol = exchange.getLastUpdatedSymbol();
		if(thresholds.containsKey(symbol)) {
			double threshold = thresholds.get(symbol);
			double price = exchange.getPrice(symbol);
			
			if(price >= threshold)
				System.out.println("ALERT ALERT ALERT ALERT");
		}
	}
	
}

class TradingBot implements StockObserver{
	private final Map<String,Double> previousPrices = new HashMap<>();

	@Override
	public void onPriceUpdate(StockExchange exchange) {
		String symbol = exchange.getLastUpdatedSymbol();
		double currentPrice = exchange.getPrice(symbol);
		double previousPrice = previousPrices.getOrDefault(symbol, currentPrice);
		
		if(currentPrice > previousPrice)
			System.out.println("HOLD");
		else if(currentPrice < previousPrice)
			System.out.println("BUY");
		
		previousPrices.put(symbol,currentPrice);
	}
	
	
}
public class Observer {
	public static void main(String[] args) {
		StockExchange exchange = new StockExchange();

        PriceDisplay display = new PriceDisplay();
        AlertService alerts = new AlertService();
        TradingBot bot = new TradingBot();

        exchange.registerObserver(display);
        exchange.registerObserver(alerts);
        exchange.registerObserver(bot);

        alerts.setAlert("AAPL", 180.0);
        alerts.setAlert("GOOG", 140.0);

        exchange.updatePrice("AAPL", 175.50);
        exchange.updatePrice("GOOG", 138.25);
        exchange.updatePrice("AAPL", 182.00);
        exchange.updatePrice("GOOG", 141.75);
	}
}
