package ee.ut.math.tvt;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class SoldItemTest {
	private StockItem stock_item1;
	private SoldItem sold_item1;
	private static double cost1= 15.00;
	

	@Before
	public void setUp() {
		stock_item1 = new StockItem(1L, "Lauaviin", "Kange alkohol", cost1);		
	}

	@Test
	public void testGetSum() {
		sold_item1 = new SoldItem(stock_item1, 3);
		assertEquals(sold_item1.getSum(), (3 * cost1), 0.0001);
	}

	@Test
	public void testGetSumWithZeroQuantity() {
		sold_item1 = new SoldItem(stock_item1, 0);
		assertEquals(sold_item1.getSum(), 0, 0.0001);	
	}
	
	@Test
	(expected = IllegalArgumentException.class)
	public void testSoldItemWithNegativeQuantity() {
		sold_item1 = new SoldItem(stock_item1, -3);
	}	
}
