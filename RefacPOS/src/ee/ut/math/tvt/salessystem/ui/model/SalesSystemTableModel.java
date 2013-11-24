package ee.ut.math.tvt.salessystem.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.table.AbstractTableModel;

import ee.ut.math.tvt.salessystem.domain.data.DisplayableItem;
import ee.ut.math.tvt.salessystem.domain.data.Sale;

/**
 * Generic table model implementation suitable for extending.
 */
public abstract class SalesSystemTableModel<T extends DisplayableItem> extends
        AbstractTableModel {

    private static final long serialVersionUID = 1L;

    protected final String[] headers;

    public SalesSystemTableModel(final String[] headers) {
        this.headers = headers;
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
        return getRows().size();
    }

    public Object getValueAt(final int rowIndex, final int columnIndex) {
        return getColumnValue(getTableRows().get(rowIndex), columnIndex);
    }

    // search for item with the specified id
    public T getItemById(final long id) {
        for (final T item : getTableRows()) {
            if (item.getId() == id)
                return item;
        }
        throw new NoSuchElementException();
    }

    abstract public List<T> getTableRows();

    public void clear() {
    	getTableRows().clear();
        fireTableDataChanged();
    }

    public void populateWithData(final List<T> data) {	
        getTableRows().clear();
        getTableRows().addAll(data);
    }
    
    public void addRow(T row) {
    	getTableRows().add(row);
        fireTableDataChanged();
    }
    
    public T getRow(int index) {
        return getTableRows().get(index);
    }
    
    public List<T> getRows() {
        return getTableRows();
    }
    
}
