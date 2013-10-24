package ee.ut.math.tvt.salessystem.ui.panels;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.NoSuchElementException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Purchase pane + shopping cart tabel UI.
 */
public class PurchaseItemPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	// Text field on the dialogPane
	private JTextField barCodeField;
	private JTextField quantityField;
	private JTextField nameField;
	private JTextField priceField;

	// Drop-down menu and list of stock items
	private JComboBox<String> menu;
	private List<StockItem> wareHouse;

	// Order confirmation panel buttons and textfields
	private JButton acceptOrder;
	private JButton cancelOrder;
	private JTextField totalSum;
	private JTextField paidSum;
	private JTextField changeSum;

	private JButton addItemButton;

	// Warehouse model
	private SalesSystemModel model;

	/**
	 * Constructs new purchase item panel.
	 * 
	 * @param model
	 *            composite model of the warehouse and the shopping cart.
	 */
	public PurchaseItemPanel(SalesSystemModel model) {
		this.model = model;
		setLayout(new GridBagLayout());

		add(drawDialogPane(), getDialogPaneConstraints());
		add(drawPurchasePane(), getPurchasePaneConstraints());
		add(drawBasketPane(), getBasketPaneConstraints());
		getComponents()[1].setVisible(false);
		setEnabled(false);
	}

	// shopping cart pane
	private JComponent drawBasketPane() {

		// Create the basketPane
		JPanel basketPane = new JPanel();
		basketPane.setLayout(new GridBagLayout());
		basketPane.setBorder(BorderFactory.createTitledBorder("Shopping cart"));

		// Create the table, put it inside a scollPane,
		// and add the scrollPane to the basketPanel.
		JTable table = new JTable(model.getCurrentPurchaseTableModel());
		JScrollPane scrollPane = new JScrollPane(table);

		basketPane.add(scrollPane, getBacketScrollPaneConstraints());

		return basketPane;
	}

	private JComponent drawPurchasePane() {
		JPanel panel = new JPanel(new GridLayout(4, 2));
		panel.setBorder(BorderFactory.createTitledBorder("Payment"));

		totalSum = new JTextField();
		paidSum = new JTextField();
		changeSum = new JTextField();

		acceptOrder = new JButton("Accept");
		cancelOrder = new JButton("Cancel");

		// Total sum of purchase
		panel.add(new JLabel("Total sum:"));
		panel.add(totalSum);
		totalSum.setEditable(false);

		// Amount paid
		panel.add(new JLabel("Paid amount:"));
		panel.add(paidSum);
		paidSum.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {}

			public void insertUpdate(DocumentEvent arg0) {
				calculateChange();
			}

			public void removeUpdate(DocumentEvent arg0) {
				calculateChange();
			}
		});

		// Change amount
		panel.add(new JLabel("Change amount:"));
		panel.add(changeSum);
		changeSum.setEditable(false);

		// Buttons for confirming/canceling order
		panel.add(acceptOrder);
		acceptOrder.setEnabled(false);
		
		panel.add(cancelOrder);
		cancelOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addItemButton.setEnabled(true);
				getComponents()[1].setVisible(false);
				resetPurchase();
			}
		});
		
		return panel;
	}

	public JTextField getPaidSum() {
		return paidSum;
	}

	public JButton getAcceptOrder() {
		return acceptOrder;
	}

	// purchase dialog
	private JComponent drawDialogPane() {

		// Create the panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6, 2));
		panel.setBorder(BorderFactory.createTitledBorder("Product"));

		// Create drop-down product selection menu
		menu = new JComboBox<String>();
		menu.addItem("");
		wareHouse = model.getSalesDomainController().loadWarehouseState();
		for (StockItem item : wareHouse) {
			menu.addItem(item.getName());
		}

		// Initialize the textfields
		barCodeField = new JTextField();
		quantityField = new JTextField("1");
		nameField = new JTextField();
		priceField = new JTextField();

		// Fill the dialog fields if the selected item in the menu changes
		menu.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (menu.isEnabled() == true) {
					fillDialogFields();
				}
			}
		});

		nameField.setEditable(false);
		priceField.setEditable(false);
		barCodeField.setEditable(false);

		// == Add components to the panel

		// - drop-down menu
		panel.add(new JLabel("Selection:"));
		panel.add(menu);

		// - bar code
		panel.add(new JLabel("Bar code:"));
		panel.add(barCodeField);

		// - amount
		panel.add(new JLabel("Amount:"));
		panel.add(quantityField);

		// - name
		panel.add(new JLabel("Name:"));
		panel.add(nameField);

		// - price
		panel.add(new JLabel("Price:"));
		panel.add(priceField);

		// Create and add the button
		addItemButton = new JButton("Add to cart");
		addItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addItemEventHandler();
			}
		});

		panel.add(addItemButton);

		return panel;
	}

	// Fill dialog with data from the "database".
	public void fillDialogFields() {
		StockItem stockItem = getStockItemByBarcode();

		if (stockItem != null) {
			barCodeField.setText(String.valueOf(stockItem.getId()));
			nameField.setText(stockItem.getName());
			String priceString = String.valueOf(stockItem.getPrice());
			priceField.setText(priceString);
		} else {
			reset();
		}
	}

	// Search the warehouse for a StockItem with the bar code entered
	// to the barCode textfield.
	private StockItem getStockItemByBarcode() {
		String itemName = (String) menu.getSelectedItem();
		try {
			for (StockItem item : wareHouse) {
				if (item.getName() == itemName) {
					int code = item.getId().intValue();
					return model.getWarehouseTableModel().getItemById(code);
				}
			}
			return null;
		} catch (NumberFormatException ex) {
			return null;
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	/**
	 * Add new item to the cart.
	 */
	public void addItemEventHandler() {
		// add chosen item to the shopping cart.
		StockItem stockItem = getStockItemByBarcode();
		if (stockItem != null) {
			int quantity;
			try {
				quantity = Integer.parseInt(quantityField.getText());
			} catch (NumberFormatException ex) {
				quantity = 1;
			}
			// Check if warehouse has enough items available for purchase.
			int orderTotal = quantity;
			List<SoldItem> cartItems = model.getCurrentPurchaseTableModel()
					.getTableRows();
			for (SoldItem item : cartItems) {
				if (stockItem.getName() == item.getName()) {
					orderTotal += item.getQuantity();
					break;
				}
			}
			if (orderTotal > stockItem.getQuantity()) {
				JOptionPane.showMessageDialog(null,
						"Not enough items in stock.", "Warning",
						JOptionPane.WARNING_MESSAGE);
			} else {
				model.getCurrentPurchaseTableModel().addItem(
						new SoldItem(stockItem, quantity));
			}
		}
	}
	
	
	// Calculate the amount of change depending on the amount paid.
	public void calculateChange() {
		try {
			if (Double.parseDouble(paidSum.getText()) >= Double
					.parseDouble(totalSum.getText())) {
				double change = Double.parseDouble(paidSum.getText())
						- Double.parseDouble(totalSum.getText());
				changeSum.setText((new Double((double)Math.round(change*100)/100)).toString());
				acceptOrder.setEnabled(true);
			} else {
				acceptOrder.setEnabled(false);
				changeSum.setText("");
			}

		} catch (NumberFormatException ex) {
			acceptOrder.setEnabled(false);
			changeSum.setText("");
		}
	}

	/**
	 * Sets whether or not this component is enabled.
	 */
	@Override
	public void setEnabled(boolean enabled) {
		this.menu.setEnabled(enabled);
		this.addItemButton.setEnabled(enabled);
		this.barCodeField.setEnabled(enabled);
		this.quantityField.setEnabled(enabled);
	}

	/**
	 * Reset dialog fields.
	 */
	public void reset() {
		barCodeField.setText("");
		quantityField.setText("1");
		nameField.setText("");
		priceField.setText("");
	}
	
	public void resetPurchase() {
		totalSum.setText("");
		paidSum.setText("");
		changeSum.setText("");
	}

	/*
	 * === Ideally, UI's layout and behavior should be kept as separated as
	 * possible. If you work on the behavior of the application, you don't want
	 * the layout details to get on your way all the time, and vice versa. This
	 * separation leads to cleaner, more readable and better maintainable code.
	 * 
	 * In a Swing application, the layout is also defined as Java code and this
	 * separation is more difficult to make. One thing that can still be done is
	 * moving the layout-defining code out into separate methods, leaving the
	 * more important methods unburdened of the messy layout code. This is done
	 * in the following methods.
	 */

	// Formatting constraints for the dialogPane
	private GridBagConstraints getDialogPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.anchor = GridBagConstraints.WEST;
		gc.weightx = 0.2;
		gc.weighty = 0d;
		gc.gridwidth = 1;
		gc.fill = GridBagConstraints.NONE;

		return gc;
	}

	// Formatting constraints for the purchasePane
	private GridBagConstraints getPurchasePaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.anchor = GridBagConstraints.CENTER;
		gc.weightx = 0.2;
		gc.weighty = 0d;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.NONE;

		return gc;
	}

	// Formatting constraints for the basketPane
	private GridBagConstraints getBasketPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.anchor = GridBagConstraints.SOUTH;
		gc.weightx = 0.2;
		gc.weighty = 1.0;
		gc.gridx = 0;
		gc.gridy = 1;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.BOTH;

		return gc;
	}

	private GridBagConstraints getBacketScrollPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;

		return gc;
	}

	public JComboBox<String> getMenu() {
		return menu;
	}

	public JButton getAddItemButton() {
		return addItemButton;
	}

	public JTextField getTotalSum() {
		return totalSum;
	}

}
