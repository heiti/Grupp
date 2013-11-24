package ee.ut.math.tvt.salessystem.ui.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import ee.ut.math.tvt.salessystem.domain.data.DisplayableItem;
import ee.ut.math.tvt.salessystem.domain.data.Sale;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

/**
 * Generic table model implementation suitable for extending.
 */
public abstract class SalesSystemTableModel<T extends DisplayableItem> extends
        AbstractTableModel {

    private static final long serialVersionUID = 1L;

    //protected List<T> rows; 
    protected Sale currentSale;
    protected final String[] headers;

    public SalesSystemTableModel(final String[] headers) {
        this.headers = headers;
        //rows = currentSale.getSoldItems(); // sale.getRows() ?? 
    }

    /**
     * @param item
     *            item describing selected row
     * @param columnIndex
     *            selected column index
     * @return value displayed in column with specified index
     */
    protected abstract Object getColumnValue(T item, int columnIndex);

    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public String getColumnName(final int columnIndex) {
        return headers[columnIndex];
    }

    public int getRowCount() {
        return currentSale.getSoldItems().size();
    }

    public Object getValueAt(final int rowIndex, final int columnIndex) {
        return getColumnValue(((List<T>) currentSale.getSoldItems()).get(rowIndex), columnIndex);
    }

    // search for item with the specified id
    public StockItem getItemById(final long id) {
        for (final T item : this.getRows()) {
            if (item.getId() == id)
                return (StockItem) item;
        }
        throw new NoSuchElementException();
    }

    abstract public Set<T> getTableRows();

    public void clear() {
    	currentSale.getSoldItems().clear();
        fireTableDataChanged();
    }

    public void populateWithData(final List<T> data) {
    	currentSale.getSoldItems().clear();
    	currentSale.getSoldItems().addAll((Collection<? extends SoldItem>) data);
    }
    
    public void addRow(T row) {
    	currentSale.getSoldItems().add((SoldItem) row);
        fireTableDataChanged();
    }
    
    public T getRow(int index) {
        return ((List<T>) currentSale.getSoldItems()).get(index);
    }
    
    abstract public List<T> getRows();
    
    
}
