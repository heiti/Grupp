package ee.ut.math.tvt.salessystem.domain.data;

import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryItem extends StockItem implements Cloneable, DisplayableItem {
	
	private Long id;
	
	private String Date;
	
	private String Time;
	
	List<SoldItem> items;
	
	private double Sum;
	
	public HistoryItem(List<SoldItem> Solditems, Long id){
		this.id = id;
		DateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat timeformat = new SimpleDateFormat("h:mm a");
		Date timeofpurchase = new Date();
		Date = dateformat.format(timeofpurchase);
		Time = timeformat.format(timeofpurchase);
		items = Solditems;
		Sum = this.getSum(Solditems);
		
	}
	
	private double getSum(List<SoldItem> Solditems){
		double sum = 0;
		
		for(SoldItem item : Solditems){
			sum = sum + item.getSum();
			
		}
		
		return sum;
			
	}

	public double getTotalSum() {
		return Sum;
	}

	public void setTotalSum(double totalSum) {
		this.Sum = totalSum;
	}

	public List<SoldItem> getItems() {
		return items;
	}


	public void setItems(List<SoldItem> items) {
		this.items = items;
	}


	public String getDate() {
		return Date;
	}



	public String getTime() {
		return Time;
	}



	public void setDate(String date) {
		this.Date = date;
	}



	public void setTime(String time) {
		this.Time = time;
	}
	
	
	@Override
	public Long getId() {
		return id;
	}

}
