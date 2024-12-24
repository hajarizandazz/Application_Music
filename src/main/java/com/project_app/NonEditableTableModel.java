
package main.java.com.project_app;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class NonEditableTableModel extends DefaultTableModel {
    public NonEditableTableModel(Vector<String> columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // Toutes les cellules sont non modifiables
    }
    public String[] getRowData(int row) {
        int columnCount = getColumnCount();
        String[] rowData = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            rowData[i] = getValueAt(row, i).toString();
        }
        return rowData;
    }
}
