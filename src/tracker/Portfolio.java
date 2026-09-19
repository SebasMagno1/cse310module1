package tracker;

import java.util.ArrayList;

public class Portfolio {

	private ArrayList<Stock> stocks;
	
	public Portfolio() {
	    stocks = new ArrayList<>();
	}
	
	public void addStock(Stock stock) {
	    stocks.add(stock);
	}
	
	public void removeStock(int index) {
	    if (index >= 0 && index < stocks.size()) {
	        stocks.remove(index);
	    }
	}
	
	public ArrayList<Stock> getStocks() {
	    return stocks;
	}
	
	public double calculateTotalValue() {
	
	    double total = 0;
	
	    for (Stock stock : stocks) {
	        total += stock.getValue();
	    }
	
	    return total;
	}
	
	public double calculateTotalProfitLoss() {
	
	    double total = 0;
	
	    for (Stock stock : stocks) {
	        total += stock.getProfitLoss();
	    }
	
	    return total;
	}

}
