package tracker;

public class Stock {
	private String symbol;
	private int shares;
	private double purchasePrice;
	private double currentPrice;
	
	public Stock(String symbol, int shares, double purchasePrice, double currentPrice) {
	    this.symbol = symbol;
	    this.shares = shares;
	    this.purchasePrice = purchasePrice;
	    this.currentPrice = currentPrice;
	}
	
	public String getSymbol() {
	    return symbol;
	}
	
	public int getShares() {
	    return shares;
	}
	
	public double getPurchasePrice() {
	    return purchasePrice;
	}
	
	public double getCurrentPrice() {
	    return currentPrice;
	}
	
	public void setCurrentPrice(double currentPrice) {
	    this.currentPrice = currentPrice;
	}
	
	public double getValue() {
	    return shares * currentPrice;
	}
	
	public double getProfitLoss() {
	    return (currentPrice - purchasePrice) * shares;
	}

}
