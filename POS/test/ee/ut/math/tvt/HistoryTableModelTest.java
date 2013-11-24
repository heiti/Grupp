package ee.ut.math.tvt;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.HistoryTableModel;

public class HistoryTableModelTest {
	
	private HistoryItem item1;
	private List<HistoryItem> data;
	private List<SoldItem> Solditems;
	private SoldItem sitem1;
	
	@Before
	public void setUp() {
		Solditems = new ArrayList<>();
		sitem1 = new SoldItem(new StockItem(1L, "Saku", "Beer", 10, 5), 2);
		Solditems.add(sitem1);
		item1 = new HistoryItem(Solditems, 1L);
		data = new ArrayList<>();
		data.add(item1);
	}
	
	@Test
	public void getHistoryItemByIDWhenItemExists() {
		HistoryTableModel item = new HistoryTableModel();
		item.setData(data);
		assertEquals(item.getHistoryItemByID(1L), item1);
	}
	
	@Test
	(expected = NoSuchElementException.class)
	public void getHistoryItemByIDWhenThrowsException() {
		HistoryTableModel item = new HistoryTableModel();
		item.setData(data);
		item.getHistoryItemByID(2L);
	}
	
	@Test
	public void testGetColumnValue() {
		HistoryTableModel item = new HistoryTableModel();
		item.setData(data);
		assertEquals(item.getColumnValue(item.getHistoryItemByID(1L), 0), item1.getId());
		assertEquals(item.getColumnValue(item.getHistoryItemByID(1L), 1), item1.getDate());
		assertEquals(item.getColumnValue(item.getHistoryItemByID(1L), 2), item1.getTime());
		assertEquals(item.getColumnValue(item.getHistoryItemByID(1L), 3), item1.getTotalSum());
	}
}
