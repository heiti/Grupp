package ee.ut.math.tvt;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class HistoryItemTest {
	
	private static double  cost1 = 15.00;
	private static double  cost2 = 12.00;
	private static double totalSum = 39.00;
	private List<SoldItem> Solditems;
	private List<SoldItem> list1;
	
	StockItem stock_item1;
	StockItem stock_item2;
	SoldItem sold_item1;
	SoldItem sold_item2;
	
	@Before
	public void setUp() {
		stock_item1 = new StockItem(1001L, "Lauaviin", "Kange", cost1);
		stock_item2 = new StockItem(1002L, "Torniviin", "Kange", cost2);
		sold_item1 = new SoldItem(stock_item1, 1);
		sold_item2 = new SoldItem(stock_item2, 2);
		Solditems = new ArrayList<>();
		list1 = new ArrayList<>();
	}
	
	@Test
	public void testAddSoldItem() {
		HistoryItem sold = new HistoryItem(Solditems, 101L);
		Solditems.add(sold_item1);
		Solditems.add(sold_item2);
		sold.setItems(Solditems);
	}
	
	@Test
	public void testGetSumWithNoItems() {
		HistoryItem sold = new HistoryItem();
		assertEquals(sold.getTotalSum(), 0.0, 0.00);	
	}
	
	@Test
	public void testGetSumWithOneItem() {
		list1.add(0, sold_item1);
		HistoryItem sold = new HistoryItem(list1, 1001L);
		assertEquals(sold.getTotalSum(), cost1, 0.0001);
	}
	
	@Test
	public void testGetSumWithMultipleItems() {		
		Solditems.add(sold_item1);
		Solditems.add(sold_item2);		
		HistoryItem sold2 = new HistoryItem(Solditems, 1002L);
		assertEquals(sold2.getTotalSum(), totalSum, 0.001);
	}

}
