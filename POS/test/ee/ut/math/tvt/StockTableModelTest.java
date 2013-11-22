package ee.ut.math.tvt;

import static org.junit.Assert.assertEquals;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;

public class StockTableModelTest {
	private StockItem item1;
	private StockItem item2;
	
	
	@Before
	public void setUp() {
		item1 = new StockItem(1L, "Saku", "0.5 pudel", 3,	10);
		item2 = new StockItem(2L, "Ale Le coq", "0.5 pudel", 3.5,	10);
	}
	
	@Test
	public void testValidateNameUniqueness() {
		StockTableModel stock = new StockTableModel();
		stock.addItem(item1);
		stock.addItem(item1);
		assertEquals(stock.getItemById(1).getQuantity(), 20);
		
	}
	
	@Test
	public void testHasEnoughInStock() {
		
	}
	
	@Test
	public void testGetItemByIdWhenItemExists() {
		StockTableModel stock = new StockTableModel();
		stock.addItem(item1);
		stock.addItem(item2);
		
		assertEquals(stock.getItemById(1), item1);
		
	}
	
	@Test
	(expected=NoSuchElementException.class)
	public void testGetItemByIdWhenThrowsException() {
		StockTableModel stock = new StockTableModel();
		stock.addItem(item1);
		stock.getItemById(8);		
	}
	
}

