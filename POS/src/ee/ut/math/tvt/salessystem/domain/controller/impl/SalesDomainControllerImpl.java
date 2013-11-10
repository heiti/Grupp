package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {
	
	// we're not really using any of those methods, see PurchaseTab and PurchaseItemPanel instead
	public void submitCurrentPurchase(List<SoldItem> goods) throws VerificationFailedException {

	}
	public void cancelCurrentPurchase() throws VerificationFailedException {
		
	}
	public void startNewPurchase() throws VerificationFailedException {
		
	}
	
	
	// Added warehouse as argument, so that loadWarehouseState can be called from other classes using model.get...
	public List<StockItem> loadWarehouseState(StockTableModel warehouse) {
		
		Session session = HibernateUtil.currentSession();
		
		Query fromDB = session.createQuery("from StockItem");
		@SuppressWarnings("unchecked")
		List<StockItem> wareHouseItems = fromDB.list();
		
		return wareHouseItems;
	}

	@Override
	public List<StockItem> loadWarehouseState() {
		List<StockItem> dataset = new ArrayList<StockItem>();
		
		StockItem chips = new StockItem(1l, "Lays chips", "Potato chips", 11.0, 5);
		StockItem chupaChups = new StockItem(2l, "Chupa-chups", "Sweets", 8.0, 8);
	    StockItem frankfurters = new StockItem(3l, "Frankfurters", "Beer sausages", 15.0, 12);
	    StockItem beer = new StockItem(4l, "Free Beer", "Student's delight", 0.0, 100);

		dataset.add(chips);
		dataset.add(chupaChups);
		dataset.add(frankfurters);
		dataset.add(beer);
		
		return dataset;
	}
	
	public void endSession() {
	    HibernateUtil.closeSession();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<HistoryItem> loadHistoryState() {
		Session session = HibernateUtil.currentSession();
		Query historyQuery = session.createQuery("from HistoryItem");
		
		return historyQuery.list();
		
	}
}
