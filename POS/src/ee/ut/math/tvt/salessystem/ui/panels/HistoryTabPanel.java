package ee.ut.math.tvt.salessystem.ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import ee.ut.math.tvt.salessystem.domain.controller.impl.SalesDomainControllerImpl;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.tabs.HistoryTab;

public class HistoryTabPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	// HistoryTableModel
	private SalesSystemModel model;

	
	public HistoryTabPanel(SalesSystemModel model, HistoryTab historyTab ){
		this.model = model;
		setLayout(new GridBagLayout());
		
		
		add(drawHistoryTable(), getHistoryTableConstraints());
		add(drawPurchaseHistoryTable(), getPurchaseHistoryTableConstraints());
		
	}
	
	public JComponent drawHistoryTable(){
		JPanel panel = new JPanel();
		 
	      final JTable table = new JTable(model.getHistoryTableModel());
	      // Detect clicks to HistoryTable
	      table.addMouseListener(new MouseAdapter(){
	    	  public void mouseClicked(MouseEvent e){
	    		  if(e.getClickCount() > 0){
	    			  
		    		 
		    		  // TODO - show currently selected sale in a table above the Sales History Table
	    		  
	    		  }
	    	  }
	      });

	      JTableHeader header = table.getTableHeader();
	      header.setReorderingAllowed(false);

	      JScrollPane scrollPane = new JScrollPane(table);

	      GridBagConstraints gc = new GridBagConstraints();
	      GridBagLayout gb = new GridBagLayout();
	      gc.fill = GridBagConstraints.BOTH;
	      gc.weightx = 1.0;
	      gc.weighty = 1.0;

	      panel.setLayout(gb);
	      panel.add(scrollPane, gc);

	      panel.setBorder(BorderFactory.createTitledBorder("Purchase History"));
	      return panel;
	    }
	
	public JComponent drawPurchaseHistoryTable(){
		// Create the basketPane
		JPanel basketPane = new JPanel();
		basketPane.setLayout(new GridBagLayout());
		basketPane.setBorder(BorderFactory.createTitledBorder("Purchase Details"));

		// Create the table, put it inside a scollPane,
		// and add the scrollPane to the basketPanel.
		JTable table = new JTable(model.getCurrentPurchaseTableModel());
		JScrollPane scrollPane = new JScrollPane(table);

		basketPane.add(scrollPane, getBacketScrollPaneConstraints());

		return basketPane;
	}
	
	 private GridBagConstraints getPurchaseHistoryTableConstraints(){
		 GridBagConstraints gc  = new GridBagConstraints();
		 
		 	gc.anchor = GridBagConstraints.SOUTH;
			gc.weightx = 0.2;
			gc.weighty = 1.0;
			gc.gridx = 0;
			gc.gridy = 1;
			gc.gridwidth = GridBagConstraints.REMAINDER;
			gc.fill = GridBagConstraints.BOTH;
			
			return gc;
		 
	 }
	
	 private GridBagConstraints getHistoryTableConstraints(){
		GridBagConstraints gc = new GridBagConstraints();
		 
		gc.anchor = GridBagConstraints.SOUTH;
		gc.weightx = 0.2;
		gc.weighty = 1.0;
		gc.gridx = 0;
		gc.gridy = 0;
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
	
	}


