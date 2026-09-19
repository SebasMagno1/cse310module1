package tracker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PortfolioGUI extends JFrame {

	private Portfolio portfolio;
	
	private JTextField symbolField;
	private JTextField sharesField;
	private JTextField purchasePriceField;
	private JTextField currentPriceField;
	
	private JTable stockTable;
	private DefaultTableModel tableModel;
	
	private JLabel totalValueLabel;
	private JLabel profitLossLabel;
	
	public PortfolioGUI() {
	
	    portfolio = new Portfolio();
	
	    setTitle("Stock Portfolio Tracker");
	    setSize(900, 600);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);
	
	    createInterface();
	}
	
	private void createInterface() {
	
	    setLayout(new BorderLayout(10, 10));
	
	    // =========================
	    // TITLE
	    // =========================
	
	    JLabel titleLabel = new JLabel(
	            "STOCK PORTFOLIO TRACKER",
	            SwingConstants.CENTER
	    );
	
	    titleLabel.setFont(
	            new Font("Arial", Font.BOLD, 26)
	    );
	
	    add(titleLabel, BorderLayout.NORTH);
	
	
	    // =========================
	    // INPUT PANEL
	    // =========================
	
	    JPanel inputPanel = new JPanel(
	            new GridLayout(2, 4, 10, 10)
	    );
	
	    symbolField = new JTextField();
	    sharesField = new JTextField();
	    purchasePriceField = new JTextField();
	    currentPriceField = new JTextField();
	
	    inputPanel.add(new JLabel("Symbol"));
	    inputPanel.add(new JLabel("Shares"));
	    inputPanel.add(new JLabel("Purchase Price"));
	    inputPanel.add(new JLabel("Current Price"));
	
	    inputPanel.add(symbolField);
	    inputPanel.add(sharesField);
	    inputPanel.add(purchasePriceField);
	    inputPanel.add(currentPriceField);
	
	    JPanel topPanel = new JPanel(
	            new BorderLayout()
	    );
	
	    topPanel.add(inputPanel, BorderLayout.CENTER);
	
	    JButton addButton = new JButton("Add Stock");
	
	    topPanel.add(
	            addButton,
	            BorderLayout.SOUTH
	    );
	
	    add(
	            topPanel,
	            BorderLayout.CENTER
	    );
	
	
	    // =========================
	    // TABLE
	    // =========================
	
	    String[] columns = {
	            "Symbol",
	            "Shares",
	            "Purchase Price",
	            "Current Price",
	            "Value",
	            "Profit/Loss"
	    };
	
	    tableModel = new DefaultTableModel(columns, 0);
	
	    stockTable = new JTable(tableModel);
	
	    JScrollPane scrollPane =
	            new JScrollPane(stockTable);
	
	    add(
	            scrollPane,
	            BorderLayout.SOUTH
	    );
	
	
	    // =========================
	    // BUTTON PANEL
	    // =========================
	
	    JPanel buttonPanel = new JPanel();
	
	    JButton deleteButton =
	            new JButton("Delete Selected");
	
	    JButton updateButton =
	            new JButton("Update Price");
	
	    JButton saveButton =
	            new JButton("Save");
	
	    JButton loadButton =
	            new JButton("Load");
	
	    buttonPanel.add(deleteButton);
	    buttonPanel.add(updateButton);
	    buttonPanel.add(saveButton);
	    buttonPanel.add(loadButton);
	
	
	    // =========================
	    // TOTALS
	    // =========================
	
	    totalValueLabel =
	            new JLabel("Total Value: $0.00");
	
	    profitLossLabel =
	            new JLabel("Profit/Loss: $0.00");
	
	    buttonPanel.add(totalValueLabel);
	    buttonPanel.add(profitLossLabel);
	
	    add(
	            buttonPanel,
	            BorderLayout.SOUTH
	    );
	
	
	    // =========================
	    // BUTTON ACTIONS
	    // =========================
	
	    addButton.addActionListener(e -> addStock());
	
	    deleteButton.addActionListener(
	            e -> deleteStock()
	    );
	
	    updateButton.addActionListener(
	            e -> updatePrice()
	    );
	
	    saveButton.addActionListener(
	            e -> savePortfolio()
	    );
	
	    loadButton.addActionListener(
	            e -> loadPortfolio()
	    );
	}
	
	
	// =========================
	// ADD STOCK
	// =========================
	
	private void addStock() {
	
	    try {
	
	        String symbol =
	                symbolField.getText().trim();
	
	        int shares =
	                Integer.parseInt(
	                        sharesField.getText()
	                );
	
	        double purchasePrice =
	                Double.parseDouble(
	                        purchasePriceField.getText()
	                );
	
	        double currentPrice =
	                Double.parseDouble(
	                        currentPriceField.getText()
	                );
	
	
	        // Conditionals
	
	        if (symbol.isEmpty()) {
	
	            JOptionPane.showMessageDialog(
	                    this,
	                    "Please enter a stock symbol."
	            );
	
	            return;
	        }
	
	        if (shares <= 0) {
	
	            JOptionPane.showMessageDialog(
	                    this,
	                    "Shares must be greater than zero."
	            );
	
	            return;
	        }
	
	        if (purchasePrice < 0 ||
	                currentPrice < 0) {
	
	            JOptionPane.showMessageDialog(
	                    this,
	                    "Prices cannot be negative."
	            );
	
	            return;
	        }
	
	
	        Stock stock =
	                new Stock(
	                        symbol.toUpperCase(),
	                        shares,
	                        purchasePrice,
	                        currentPrice
	                );
	
	        portfolio.addStock(stock);
	
	        refreshTable();
	
	        clearFields();
	
	
	    } catch (NumberFormatException e) {
	
	        JOptionPane.showMessageDialog(
	                this,
	                "Please enter valid numbers."
	        );
	    }
	}
	
	
	// =========================
	// REFRESH TABLE
	// =========================
	
	private void refreshTable() {
	
	    tableModel.setRowCount(0);
	
	    for (Stock stock : portfolio.getStocks()) {
	
	        Object[] row = {
	
	                stock.getSymbol(),
	
	                stock.getShares(),
	
	                String.format(
	                        "$%.2f",
	                        stock.getPurchasePrice()
	                ),
	
	                String.format(
	                        "$%.2f",
	                        stock.getCurrentPrice()
	                ),
	
	                String.format(
	                        "$%.2f",
	                        stock.getValue()
	                ),
	
	                String.format(
	                        "$%.2f",
	                        stock.getProfitLoss()
	                )
	        };
	
	        tableModel.addRow(row);
	    }
	
	    updateTotals();
	}
	
	
	// =========================
	// UPDATE TOTALS
	// =========================
	
	private void updateTotals() {
	
	    double totalValue =
	            portfolio.calculateTotalValue();
	
	    double profitLoss =
	            portfolio.calculateTotalProfitLoss();
	
	    totalValueLabel.setText(
	            String.format(
	                    "Total Value: $%.2f",
	                    totalValue
	            )
	    );
	
	    profitLossLabel.setText(
	            String.format(
	                    "Profit/Loss: $%.2f",
	                    profitLoss
	            )
	    );
	}
	
	
	// =========================
	// DELETE STOCK
	// =========================
	
	private void deleteStock() {
	
	    int selectedRow =
	            stockTable.getSelectedRow();
	
	    if (selectedRow == -1) {
	
	        JOptionPane.showMessageDialog(
	                this,
	                "Please select a stock."
	        );
	
	        return;
	    }
	
	    portfolio.removeStock(selectedRow);
	
	    refreshTable();
	}
	
	
	// =========================
	// UPDATE PRICE
	// =========================
	
	private void updatePrice() {
	
	    int selectedRow =
	            stockTable.getSelectedRow();
	
	    if (selectedRow == -1) {
	
	        JOptionPane.showMessageDialog(
	                this,
	                "Please select a stock."
	        );
	
	        return;
	    }
	
	    String input =
	            JOptionPane.showInputDialog(
	                    this,
	                    "Enter new current price:"
	            );
	
	    if (input == null) {
	        return;
	    }
	
	    try {
	
	        double newPrice =
	                Double.parseDouble(input);
	
	        if (newPrice < 0) {
	
	            JOptionPane.showMessageDialog(
	                    this,
	                    "Price cannot be negative."
	            );
	
	            return;
	        }
	
	        Stock stock =
	                portfolio.getStocks()
	                        .get(selectedRow);
	
	        stock.setCurrentPrice(newPrice);
	
	        refreshTable();
	
	    } catch (NumberFormatException e) {
	
	        JOptionPane.showMessageDialog(
	                this,
	                "Please enter a valid price."
	        );
	    }
	}
	
	
	// =========================
	// CLEAR INPUTS
	// =========================
	
	private void clearFields() {
	
	    symbolField.setText("");
	    sharesField.setText("");
	    purchasePriceField.setText("");
	    currentPriceField.setText("");
	}
	
	
	// =========================
	// SAVE FILE
	// =========================
	
	private void savePortfolio() {
	
	    try {
	
	        FileWriter writer =
	                new FileWriter("portfolio.txt");
	
	        for (Stock stock :
	                portfolio.getStocks()) {
	
	            writer.write(
	                    stock.getSymbol() + "," +
	                    stock.getShares() + "," +
	                    stock.getPurchasePrice() + "," +
	                    stock.getCurrentPrice() +
	                    "\n"
	            );
	        }
	
	        writer.close();
	
	        JOptionPane.showMessageDialog(
	                this,
	                "Portfolio saved successfully!"
	        );
	
	    } catch (IOException e) {
	
	        JOptionPane.showMessageDialog(
	                this,
	                "Error saving portfolio."
	        );
	    }
	}
	
	
	// =========================
	// LOAD FILE
	// =========================
	
	private void loadPortfolio() {
	
	    try {
	
	        File file =
	                new File("portfolio.txt");
	
	        Scanner fileScanner =
	                new Scanner(file);
	
	        portfolio.getStocks().clear();
	
	        while (fileScanner.hasNextLine()) {
	
	            String line =
	                    fileScanner.nextLine();
	
	            String[] data =
	                    line.split(",");
	
	            String symbol = data[0];
	
	            int shares =
	                    Integer.parseInt(data[1]);
	
	            double purchasePrice =
	                    Double.parseDouble(data[2]);
	
	            double currentPrice =
	                    Double.parseDouble(data[3]);
	
	            Stock stock =
	                    new Stock(
	                            symbol,
	                            shares,
	                            purchasePrice,
	                            currentPrice
	                    );
	
	            portfolio.addStock(stock);
	        }
	
	        fileScanner.close();
	
	        refreshTable();
	
	        JOptionPane.showMessageDialog(
	                this,
	                "Portfolio loaded successfully!"
	        );
	
	    } catch (Exception e) {
	
	        JOptionPane.showMessageDialog(
	                this,
	                "Could not load portfolio.txt"
	        );
	    }
	}

}
