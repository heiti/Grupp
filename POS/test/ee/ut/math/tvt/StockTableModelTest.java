package ee.ut.math.tvt;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;

public class StockTableModelTest {
	private StockItem item1;
	private StockItem item1_1;
	private StockItem item2;
	private List <SoldItem> order = new ArrayList<>();
	
	
	@Before
	public void setUp() {
		item1 = new StockItem(1L, "Saku", "0.5 pudel", 3,	10);
		item2 = new StockItem(2L, "Ale Le coq", "0.5 pudel", 3.5,	10);
	}
	
	@Test
	/*
	 * In our program the unique name test is implemented in class Stocktab,
	 * method addNewItem() 
	 */
	(expected = IllegalArgumentException.class)
	public void testValidateNameUniqueness() {
		StockTableModel stock = new StockTableModel();
		item1_1 = new StockItem(1L, "Bock", "0.5 purk", 2, 100);
		stock.addItem(item1);
		stock.addItem(item1_1);
		
	}
	
	@Test
	/*
	 * In our program quantity control is implemented in class PurchaseItemPanel,
	 * method addItemEventHandler().
	 */
	(expected = IllegalArgumentException.class)
	public void testHasEnoughInStock() {
		StockTableModel stock = new StockTableModel();
		stock.addItem(item1);
		System.out.println(stock);
		SoldItem sold = new SoldItem(item1, 100);
		order.add(sold);
		stock.editContents(order);
		
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
		stock.addItem(item2);
		
		stock.getItemById(8);		
	}
	
}

