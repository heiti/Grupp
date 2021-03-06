package ee.ut.math.tvt.salessystem.ui.model;

import java.io.Console;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

/**
 * Stock item table model.
 */
public class StockTableModel extends SalesSystemTableModel<StockItem> {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(StockTableModel.class);

	public StockTableModel() {
		super(new String[] {"Id", "Name", "Price", "Quantity"});
	}

	@Override
	protected Object getColumnValue(StockItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getName();
		case 2:
			return item.getPrice();
		case 3:
			return item.getQuantity();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	/**
	 * Add new stock item to table. If there already is a stock item with
	 * same id, then existing item's quantity will be increased.
	 * @param stockItem
	 */
	public void addItem(final StockItem stockItem) {
		try {
			StockItem item = getItemById(stockItem.getId());
			 if (stockItem.getName() != item.getName()) {
		          throw new IllegalArgumentException("Invalid id or name! Item with this id but with different name already exists!");		          
			 }
			//item.setQuantity(item.getQuantity() + stockItem.getQuantity());
			log.debug("Found existing item " + stockItem.getName()
					+ " increased quantity by " + stockItem.getQuantity());
		}
		
		catch (NoSuchElementException e) {
			rows.add(stockItem);
			log.debug("Added " + stockItem.getName()
					+ " quantity of " + stockItem.getQuantity());
		}
		//fireTableDataChanged(); -- KUI SELLE TAGASI LISAD TEKIB BUG
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final StockItem stockItem : rows) {
			buffer.append(stockItem.getId() + "\t");
			buffer.append(stockItem.getName() + "\t");
			buffer.append(stockItem.getPrice() + "\t");
			buffer.append(stockItem.getQuantity() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}
	// Method that updates warehouse state when purchase is accepted
	public void editContents(List<SoldItem> acceptedProducts) {
		
		for(SoldItem item : acceptedProducts){
			for(StockItem sItem : getTableRows()){
				if(item.getName().equals(sItem.getName())){
					if((sItem.getQuantity()-item.getQuantity()) >= 0){
						sItem.setQuantity(sItem.getQuantity()-item.getQuantity());
					}
					else {
						throw new IllegalArgumentException();
					}
					
				}
			}
		}
		//fireTableDataChanged();
		
	}

}
