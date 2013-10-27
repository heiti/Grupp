package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.panels.AddItemPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;


public class StockTab {

  private JButton addItem;

  private SalesSystemModel model;
  
  private AddItemPanel addToWarehouse;

  public StockTab(SalesSystemModel model) {
    this.model = model;
    addToWarehouse = new AddItemPanel(model);
  }

  // warehouse stock tab - consists of a menu and a table
  public Component draw() {
    JPanel panel = new JPanel();
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    GridBagLayout gb = new GridBagLayout();
    GridBagConstraints gc = new GridBagConstraints();
    panel.setLayout(gb);

    gc.fill = GridBagConstraints.HORIZONTAL;
    gc.anchor = GridBagConstraints.NORTH;
    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.weightx = 1.0d;
    gc.weighty = 0d;

    panel.add(drawStockMenuPane(), gc);

    gc.weighty = 1.0;
    gc.fill = GridBagConstraints.BOTH;
    panel.add(drawStockMainPane(), gc);
;
    return panel;
  }

  // warehouse menu
  private Component drawStockMenuPane() {
    JPanel panel = new JPanel();
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    GridBagConstraints gc = new GridBagConstraints();
    GridBagLayout gb = new GridBagLayout();

    panel.setLayout(gb);
    gc.anchor = GridBagConstraints.WEST;
    gc.weightx = 0;

    addItem = new JButton("     Add     ");
    addItem.addActionListener(new ActionListener(){
    	@Override
		public void actionPerformed(ActionEvent e) {
			addNewItem();
		}
    }); // Listener that calls the addNewItem method,
    gc.gridwidth = GridBagConstraints.RELATIVE;
    gc.weightx = 1.0;
    gc.gridx = 0;
    panel.add(addItem, gc);
    
    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.gridx = 1;
    panel.add(addToWarehouse.drawAddingPanel(),gc);
    
    return panel;
  }


  // table of the wareshouse stock
  private Component drawStockMainPane() {
    JPanel panel = new JPanel();

    JTable table = new JTable(model.getWarehouseTableModel());

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

    panel.setBorder(BorderFactory.createTitledBorder("Warehouse status"));
    return panel;
  }
  
  public void addNewItem(){
	  try{
		  Long id = Long.parseLong(addToWarehouse.getIdField().getText());
		  String name = addToWarehouse.getNameField().getText();
		  String desc = addToWarehouse.getDescField().getText();
		  double price = Double.parseDouble(addToWarehouse.getPriceField().getText());
		  int quantity = Integer.parseInt(addToWarehouse.getQuantityField().getText());
		  StockItem newItem = new StockItem(id, name, desc, price, quantity);
		  
		  List<StockItem> wareHouseItems = model.getWarehouseTableModel().getTableRows();
		  boolean allowedToAdd = true;
		  for (StockItem item : wareHouseItems){
			  if(id == item.getId()){
				  if(name.equals(item.getName()) && price == item.getPrice()){
						  allowedToAdd = true;				  
						  break;
					}
				  else{
					  allowedToAdd = false; 
				  }
				}
			  }
			 
		  
		  if(allowedToAdd){
			  model.getWarehouseTableModel().addItem(newItem);
			  model.getWarehouseTableModel().fireTableDataChanged();	  
		  }
		  else  JOptionPane.showMessageDialog(null,
					"Id already used for another item!", "Warning",
					JOptionPane.WARNING_MESSAGE);
		 
	  }
	  catch(NumberFormatException e){
		  JOptionPane.showMessageDialog(null,
					"Can't add new item, please check fields!", "Warning",
					JOptionPane.WARNING_MESSAGE);
	  }
	  
	  addToWarehouse.getIdField().setText("");
	  addToWarehouse.getNameField().setText("");
	  addToWarehouse.getDescField().setText("");
	  addToWarehouse.getPriceField().setText("");
	  addToWarehouse.getQuantityField().setText("");
	  
	  
  }

}
