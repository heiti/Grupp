package ee.ut.math.tvt;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class StockItemTest {
	private StockItem item1;
	private static int items = 5;
	private static double price = 3;
	
	@Before
	public void setUp() {
		item1 = new StockItem(1L, "Saku Originaal", "Õlu", price, items);
	}
	
	@Test
	public void testClone() {
		assertEquals(item1.clone().getClass(), item1.getClass());			
	}
	
	@Test
	public void testGetColumn() {
		assertEquals(item1.getColumn(0), 1L);
		assertEquals(item1.getColumn(1), "Saku Originaal");
		assertEquals(item1.getColumn(2), price);
		assertEquals(item1.getColumn(3), items);		
	}
	
	@Test
	(expected = RuntimeException.class)
	public void testGetColumnWhenInvalid() {
		assertEquals(item1.getColumn(4), items);
	}
	
	@Test
	(expected = IllegalArgumentException.class)
	public void testStockItemWithNegativePrice() {
		item1 = new StockItem(1L, "Saku Originaal", "Õlu", -price, items);
	}
	
	@Test
	(expected = IllegalArgumentException.class)
	public void testStockItemWithNegativeQuantity() {
		item1 = new StockItem(1L, "Saku Originaal", "Õlu", price, -items);
	}
}
