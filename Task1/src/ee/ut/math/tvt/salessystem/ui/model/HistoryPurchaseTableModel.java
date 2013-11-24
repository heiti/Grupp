package ee.ut.math.tvt.salessystem.ui.model;

import java.util.List;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;

public class HistoryPurchaseTableModel extends SalesSystemTableModel<SoldItem> {
	
	// Same as the currentPurchaseTable Model but for displaying history purchases.
	
	private static final long serialVersionUID = 1L;

	public HistoryPurchaseTableModel() {
		super(new String[] {"Name", "Price", "Quantity", "Total Price"});
		
	}

	@Override
	public void populateWithData(List<SoldItem> data) {
		rows.clear();
		rows.addAll(data);
	}

	@Override
	protected Object getColumnValue(SoldItem item, int columnIndex) {
		switch(columnIndex){
		case 0: return item.getName();
		case 1: return item.getPrice();
		case 2: return item.getQuantity();
		case 3: return item.getSum();		
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	public void add(List<SoldItem> items) {
		rows.clear();
		for(SoldItem item : items){
			rows.add(item);
		}
		
	}
	

}
