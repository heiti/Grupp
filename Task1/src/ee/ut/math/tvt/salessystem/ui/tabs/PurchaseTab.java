package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.panels.PurchaseItemPanel;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.hibernate.PersistentObjectException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "Point-of-sale" in the menu).
 */
public class PurchaseTab {

	private static final Logger log = Logger.getLogger(PurchaseTab.class);

	private final SalesDomainController domainController;

	private JButton newPurchase;

	private JButton submitPurchase;

	private JButton cancelPurchase;

	private PurchaseItemPanel purchasePane;

	private SalesSystemModel model;
	

	public PurchaseTab(SalesDomainController controller, SalesSystemModel model) {
		this.domainController = controller;
		this.model = model;
	}
	/**
	 * The purchase tab. Consists of the purchase menu, current purchase dialog
	 * and shopping cart table.
	 */
	public Component draw() {
		JPanel panel = new JPanel();

		// Layout
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.setLayout(new GridBagLayout());

		// Add the purchase menu
		panel.add(getPurchaseMenuPane(), getConstraintsForPurchaseMenu());
		
		// Add the main purchase-panel
		purchasePane = new PurchaseItemPanel(model);
		purchasePane.getAcceptOrder().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				purchasePane.getComponents()[1].setVisible(false);
				acceptPaymentButtonClicked();
			}
		});

		panel.add(purchasePane, getConstraintsForPurchasePanel());
	
		return panel;
	}
	

	// The purchase menu. Contains buttons "New purchase", "Submit", "Cancel".
	private Component getPurchaseMenuPane() {
		JPanel panel = new JPanel();

		// Initialize layout
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gc = getConstraintsForMenuButtons();

		// Initialize the buttons
		newPurchase = createNewPurchaseButton();
		submitPurchase = createConfirmButton();
		cancelPurchase = createCancelButton();

		// Add the buttons to the panel, using GridBagConstraints we defined
		// above
		panel.add(newPurchase, gc);
		panel.add(submitPurchase, gc);
		panel.add(cancelPurchase, gc);

		return panel;
	}

	// Creates the button "New purchase"
	private JButton createNewPurchaseButton() {
		JButton b = new JButton("New purchase");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newPurchaseButtonClicked();
			}
		});

		return b;
	}

	// Creates the "Confirm" button
	private JButton createConfirmButton() {
		JButton b = new JButton("Confirm");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				submitPurchaseButtonClicked();
			}
		});
		b.setEnabled(false);

		return b;
	}

	// Creates the "Cancel" button
	private JButton createCancelButton() {
		JButton b = new JButton("Cancel");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelPurchaseButtonClicked();
			}
		});
		b.setEnabled(false);

		return b;
	}

	/*
	 * === Event handlers for the menu buttons (get executed when the buttons
	 * are clicked)
	 */

	/** Event handler for the <code>new purchase</code> event. */
	protected void newPurchaseButtonClicked() {
		log.info("New sale process started");
		
		// Update Menu
		try {
			purchasePane.updateMenu();
			startNewSale();
		} catch (VerificationFailedException e1) {
			
			log.error(e1.getMessage());
		}
	}

	/** Event handler for the <code>cancel purchase</code> event. */
	protected void cancelPurchaseButtonClicked() {
		log.info("Sale cancelled");
		
		
		try {
			purchasePane.getComponents()[1].setVisible(false);
			purchasePane.getMenu().setSelectedIndex(0);  // Magic happens - menu is reloaded.
			domainController.cancelCurrentPurchase();
			endSale();
			model.getCurrentPurchaseTableModel().clear();
		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
	}

	/** Event handler for the <code>accept payment</code> event. */
	protected void acceptPaymentButtonClicked() {
		log.info("Payment process started");
		
		// Adding sales to History
		
		List<SoldItem> SoldItems = model.getCurrentPurchaseTableModel().getTableRows();
		Long id = (long) model.getHistoryTableModel().getRowCount();
		HistoryItem historyItem = new HistoryItem(SoldItems,id);

		//TODO - Delete purchased goods from warehouse
		
		Session session = HibernateUtil.currentSession();
		session.beginTransaction();
		
		for(SoldItem item : SoldItems){
			item.setSaleId(historyItem.getId());
			session.persist(item);
		}
		
		// ADD HistoryItem to DB
		try{
			session.persist(historyItem);
			//session.getTransaction().commit();
		}
		catch(PersistentObjectException ex){
			session.merge(historyItem);
			//session.getTransaction().commit();
		}
		
		try{
			model.getHistoryTableModel().getData().add(historyItem);
		}
		catch(NullPointerException e){
			List<HistoryItem> historyItems = new ArrayList<>();
			historyItems.add(historyItem);
			model.getHistoryTableModel().setData(historyItems);
		}
		model.getHistoryTableModel().getTableRows().add(historyItem);
		model.getHistoryTableModel().fireTableDataChanged();
		
		// Updating GUI warehouse after sale complete
		
		Query editWarehouse = session.createQuery("from StockItem where id = :stockItemID");
		
		for(SoldItem item : SoldItems){
			// For every SoldItem decrease Quantity in DB Warehousetable
			editWarehouse.setParameter("stockItemID", item.getStockItem().getId());
			StockItem dbsItem = (StockItem) editWarehouse.uniqueResult();
			log.info("LOGOGOGOG   " + dbsItem.getName());
			dbsItem.setQuantity(dbsItem.getQuantity() - item.getQuantity());
		}
				
		session.getTransaction().commit();
		
		
//		List<SoldItem> acceptedProducts = model.getCurrentPurchaseTableModel().getTableRows();
//		model.getWarehouseTableModel().editContents(acceptedProducts);
		
				
		// Ending the sale and resetting the purchasePane	
		log.info("Sale complete");
		purchasePane.resetPurchase();
		try {
			log.debug("Contents of the current basket:\n"
					+ model.getCurrentPurchaseTableModel());
			domainController.submitCurrentPurchase(model
					.getCurrentPurchaseTableModel().getTableRows());
			endSale();
			model.getCurrentPurchaseTableModel().clear();
		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
	}
	
	// Display the payment screen, when order is confirmed
	protected void submitPurchaseButtonClicked() {
		List<SoldItem> cartItems = model.getCurrentPurchaseTableModel().getTableRows();
		if (cartItems.size() != 0) {
			double sum = 0;
			for (SoldItem item: cartItems) {
				sum += item.getSum();
			}
			purchasePane.getTotalSum().setText((new Double(sum)).toString());
			purchasePane.getAddItemButton().setEnabled(false);
			purchasePane.getComponents()[1].setVisible(true);
		}
	}

	/*
	 * === Helper methods that bring the whole purchase-tab to a certain state
	 * when called.
	 */

	// switch UI to the state that allows to proceed with the purchase
	private void startNewSale() {
		purchasePane.reset();

		purchasePane.setEnabled(true);
		submitPurchase.setEnabled(true);
		cancelPurchase.setEnabled(true);
		newPurchase.setEnabled(false);
	}

	// switch UI to the state that allows to initiate new purchase
	private void endSale() {
		purchasePane.reset();

		cancelPurchase.setEnabled(false);
		submitPurchase.setEnabled(false);
		newPurchase.setEnabled(true);
		purchasePane.setEnabled(false);
	}

	/*
	 * === Next methods just create the layout constraints objects that control
	 * the the layout of different elements in the purchase tab. These
	 * definitions are brought out here to separate contents from layout, and
	 * keep the methods that actually create the components shorter and cleaner.
	 */

	private GridBagConstraints getConstraintsForPurchaseMenu() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 0d;

		return gc;
	}

	private GridBagConstraints getConstraintsForPurchasePanel() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 1.0;

		return gc;
	}

	// The constraints that control the layout of the buttons in the purchase
	// menu
	private GridBagConstraints getConstraintsForMenuButtons() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.weightx = 0;
		gc.anchor = GridBagConstraints.CENTER;
		gc.gridwidth = GridBagConstraints.RELATIVE;

		return gc;
	}
	
}
