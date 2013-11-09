package ee.ut.math.tvt.salessystem.domain.data;

import java.util.List;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "HISTORYITEM")
public class HistoryItem implements DisplayableItem, Cloneable{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "DATE")
	private String Date;
	
	@Column(name = "TIME")
	private String Time;
	
	@OneToMany
	@JoinTable(name = "HISTORYITEMS_TO_SOLDITEMS",
	joinColumns = @JoinColumn(name ="HISTORYITEM_ID", referencedColumnName = "ID"),
	inverseJoinColumns = @JoinColumn(name ="SOLDITEM_ID", referencedColumnName = "ID")
	)
	List<SoldItem> items;
	
	@Column(name = "TOTAL_SUM")	
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
