package tracker;

import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
	
	    SwingUtilities.invokeLater(() -> {
	
	        PortfolioGUI gui =
	                new PortfolioGUI();
	
	        gui.setVisible(true);
	    });
	}

}
