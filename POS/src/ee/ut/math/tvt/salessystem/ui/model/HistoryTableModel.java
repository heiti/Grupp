package ee.ut.math.tvt.salessystem.ui.model;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.NoSuchElementException;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;


//Table Model for History Tab
public class HistoryTableModel extends SalesSystemTableModel<HistoryItem> {
	
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(StockTableModel.class);
	
	private List<HistoryItem> data;



	public HistoryTableModel() {
		super(new String[] {"Id", "Date", "Time", "Total Price"});
		
	}
	public List<HistoryItem> getData() {
		return data;
	}

	public void setData(List<HistoryItem> data) {
		this.data = data;
	}
	@Override
	protected Object getColumnValue(HistoryItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getDate();
		case 2:
			return item.getTime();
		case 3:
			return item.getTotalSum();
		}
		throw new IllegalArgumentException("Column index out of range");
	}
	
	public HistoryItem getHistoryItemByID(Long ID){
		for (HistoryItem item : data){
			if(ID == item.getId()){
				return item;
			}
		}
		throw new NoSuchElementException();
		
	}
	
	
	

}
