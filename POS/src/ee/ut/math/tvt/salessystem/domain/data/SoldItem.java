package ee.ut.math.tvt.salessystem.domain.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



/**
 * Already bought StockItem. SoldItem duplicates name and price for preserving history. 
 */
@Entity
@Table(name = "SOLDITEM")
public class SoldItem implements Cloneable, DisplayableItem {

	public SoldItem() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "SALE_ID")
	private Long saleId;
	
	@ManyToOne
    @JoinColumn(name = "STOCKITEM_ID", nullable = false)
    private StockItem stockItem;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "QUANTITY")
    private Integer quantity;
    
    @Column(name = "ITEMPRICE")
    private double price;
    
    public SoldItem(StockItem stockItem, int quantity) {
    	if (quantity < 0.0) {
            throw new IllegalArgumentException("Invalid quantity! Has to be positive!");
        }
        this.stockItem = stockItem;
        this.name = stockItem.getName();
        this.price = stockItem.getPrice();
        this.quantity = quantity;        
    }
    
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long id2) {
		this.saleId = id2;
	}


	public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getSum() {
        return price * ((double) quantity);
    }

    public StockItem getStockItem() {
        return stockItem;
    }

    public void setStockItem(StockItem stockItem) {
        this.stockItem = stockItem;
    }
    
}
