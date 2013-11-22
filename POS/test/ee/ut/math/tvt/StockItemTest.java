package ee.ut.math.tvt;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class StockItemTest {
	private StockItem item1;
	private static Object items = 5;
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testClone() {
		StockItem item2 = new StockItem(1L, "Saku Originaal", "Ílu", 3, 5);
		assertEquals(item2.clone().getClass(), item2.getClass());	
		
	}
	
	@Test
	public void testGetColumn() {
		item1 = new StockItem(1L, "Saku Originaal", "Ílu", 3, 5);
		assertEquals(item1.getColumn(3), items);
		
	}
}
