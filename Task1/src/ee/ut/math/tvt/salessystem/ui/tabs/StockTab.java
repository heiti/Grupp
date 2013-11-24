package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;
import ee.ut.math.tvt.salessystem.ui.panels.AddItemPanel;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

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
import javax.swing.table.JTableHeader;

import org.apache.log4j.Logger;
import org.hibernate.PersistentObjectException;
import org.hibernate.Query;
import org.hibernate.Session;



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

    gc.fill = GridBagConstraints.BOTH;
    gc.anchor = GridBagConstraints.NORTH;
    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.weightx = 1.0;
    gc.weighty = 1.5d;
    
    gc.gridx = 0;
    gc.gridy = 0;
    

    panel.add(drawStockMenuPane(), gc);

    gc.weighty = 10.0d;
    gc.fill = GridBagConstraints.BOTH;
    gc.gridx = 0;
    gc.gridy = 1;
    panel.add(drawStockMainPane(), gc);
    
    

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
    

    addItem = new JButton("     Add     ");
    addItem.addActionListener(new ActionListener(){
    	@Override
		public void actionPerformed(ActionEvent e) {
			addNewItem();
		}
    }); // Listener that calls the addNewItem method,
//    gc.gridwidth = GridBagConstraints.RELATIVE;
    gc.weightx = 1;
    gc.weighty = 1;
    gc.gridx = 0;
    gc.fill = GridBagConstraints.BOTH;
    
    panel.add(addItem, gc);
    
    gc.fill = GridBagConstraints.BOTH;
    gc.weightx = 5.0;
    gc.weighty = 1.0;
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
					  Logger log = Logger.getLogger(StockTableModel.class);
					  allowedToAdd = false; 
				  }
				}
			  }
			 
		  
		  if(allowedToAdd){
			  
			  Session session = HibernateUtil.currentSession();
			  session.beginTransaction();
			  
			  // query to check, if entry already exists.
			  Query existQuery = session.createQuery("FROM " +
			  		"StockItem WHERE name = :name AND price = :price AND id = :id");
			  existQuery.setParameter("name", newItem.getName());
			  existQuery.setParameter("price", newItem.getPrice());
			  existQuery.setParameter("id", newItem.getId());
			  
			  @SuppressWarnings("unchecked")
			  List<StockItem> existingItems = existQuery.list();
			  
			  // Edit Quantity in DB if item already represented
			  if (existingItems.size() > 0){
				  for(StockItem item : existingItems){
					  item.setQuantity(item.getQuantity() + newItem.getQuantity());
					  session.getTransaction().commit();
				  }  
			  }
			  
			  // Add item to DB if not
			  else {
				  try{
					  session.persist(newItem);
					  session.getTransaction().commit();
				  }
				  catch(PersistentObjectException ex){
					  session.merge(newItem);
					  session.getTransaction().commit();
				  }
			  }
			  
			  model.getWarehouseTableModel().addItem(newItem);  //-- KUI SELLE TAGASI LISAD, SIIS TEKIB BUG, et ta hakkab topelt lisama vanu asju.
			  model.getWarehouseTableModel().fireTableDataChanged();
			  
			  // Reset fields only, when entry succeeded
			  // to avoid refilling all fields in case there was an 
			  // error caused by a typo or smth.
			  addToWarehouse.getIdField().setText("");
			  addToWarehouse.getNameField().setText("");
			  addToWarehouse.getDescField().setText("");
			  addToWarehouse.getPriceField().setText("");
			  addToWarehouse.getQuantityField().setText("");
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
	  
	  
  }

}
