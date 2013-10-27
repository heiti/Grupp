package ee.ut.math.tvt.salessystem.ui.model;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;

/**
 * Main model. Holds all the other models.
 */
public class SalesSystemModel {
    
    private static final Logger log = Logger.getLogger(SalesSystemModel.class);

    // Warehouse model
    private StockTableModel warehouseTableModel;
    
    // History model
    private HistoryTableModel historyTableModel;
    
    // Current shopping cart model
    private PurchaseInfoTableModel currentPurchaseTableModel;
    
    //History shopping cart model
    private HistoryPurchaseTableModel historyPurchaseTableModel;

    private final SalesDomainController domainController;

    /**
     * Construct application model.
     * @param domainController Sales domain controller.
     */
    public SalesSystemModel(SalesDomainController domainController) {
        this.domainController = domainController;
        
        warehouseTableModel = new StockTableModel();
        historyTableModel = new HistoryTableModel();
        currentPurchaseTableModel = new PurchaseInfoTableModel();
        historyPurchaseTableModel = new HistoryPurchaseTableModel();

        // populate stock model with data from the warehouse
        warehouseTableModel.populateWithData(domainController.loadWarehouseState(warehouseTableModel));

    }

    public HistoryPurchaseTableModel getHistoryPurchaseTableModel() {
		return historyPurchaseTableModel;
	}

	public HistoryTableModel getHistoryTableModel() {
		return historyTableModel;
	}

	public StockTableModel getWarehouseTableModel() {
        return warehouseTableModel;
    }

    public PurchaseInfoTableModel getCurrentPurchaseTableModel() {
        return currentPurchaseTableModel;
    }
    
    public SalesDomainController getSalesDomainController() {
        return domainController;
    }
    
}
