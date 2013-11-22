package ee.ut.math.tvt;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class HistoryItemTest {
	
	private List<SoldItem> Solditems;
	private static double  cost1 = 15.00;
	private static double  cost2 = 12.00;
	private static double totalSum = 39.00;
	private static List<SoldItem> list1;
	
	
	
	@Before
	public void setUp() {
	
	}
	
	@Test
	public void testAddSoldItem() {
		StockItem stock_item1 = new StockItem(1001L, "Lauaviin", "Kange", cost1);
		SoldItem sold_item1 = new SoldItem(stock_item1, 1);
		HistoryItem sold = new HistoryItem(Solditems, 101L);
		Solditems.add(sold_item1);
		sold.setItems(Solditems);
	}
	
	@Test
	public void testGetSumWithNoItems() {
		HistoryItem sold = new HistoryItem();
		assertEquals(sold.getTotalSum(), 0.0, 0.00);
		
	}
	
	@Test
	public void testGetSumWithOneItem() {
		StockItem stock_item1 = new StockItem(1001L, "Lauaviin", "Kange", cost1);
		SoldItem sold_item1 = new SoldItem(stock_item1, 1);
		list1.add(0, sold_item1);
		System.out.println(list1.get(0));
		HistoryItem sold = new HistoryItem(list1, 1001L);
		assertEquals(sold.getTotalSum(), 15.00, 0.0001);
	}
	
	@Test
	public void testGetSumWithMultipleItems() {
		StockItem stock_item1 = new StockItem(1001L, "Lauaviin", "Kange", cost1);
		StockItem stock_item2 = new StockItem(1002L, "Torniviin", "Kange", cost2);
		SoldItem sold_item1 = new SoldItem(stock_item1, 1);
		SoldItem sold_item2 = new SoldItem(stock_item2, 2);
		Solditems.add(sold_item1);
		Solditems.add(sold_item2);
		
		HistoryItem sold2 = new HistoryItem(Solditems, 1002L);
		assertEquals(sold2.getTotalSum(), totalSum, 0.001);
		
	}

}
