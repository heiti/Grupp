package ee.ut.math.tvt;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

public class PurchaseInfoTableModelTest {
	
//	private List<T> rows;
	private StockItem stock1;
	private StockItem stock2;
	private SoldItem item1;
	private SoldItem item2;
	private static int quantity = 3;
	
	@Before
	public void setUp() {
//		rows = new ArrayList<T>();
		stock1 = new StockItem(1L, "Saku", "Beer", 5, 10);
		stock2 = new StockItem(2L, "Bock", "Beer", 2, 5);
		item1 = new SoldItem(stock1, quantity);
		item1.setId(1L);
		item2 = new SoldItem(stock2, quantity);
		item2.setId(2L);
	}
	
	@Test
	public void testAddItemWithNewItem() {
		PurchaseInfoTableModel purchase = new PurchaseInfoTableModel();
		purchase.addItem(item1);
		purchase.addItem(item2);
		assertEquals(purchase.getItemById(2), item2);
	}
	
	@Test
	public void testAddItemWithExistingItem() {
		PurchaseInfoTableModel purchase = new PurchaseInfoTableModel();
		purchase.addItem(item1);
		purchase.addItem(item1);
		assertEquals(purchase.getItemById(1).getQuantity(), (2*quantity), 0.001);
	}
}
