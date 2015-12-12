package ui;

import javax.swing.table.AbstractTableModel;

/**
 * 
 * @date 2014年11月26日
 * @author stk
 *
 */

/*
 * 自定义TableModel
 */
@SuppressWarnings("serial")
public class TableModel extends AbstractTableModel{
	private Object[][] data;
	private String[] name;
	//---------------------------------------------------------------
	public TableModel(Object[][] data, String[] name) {
		this.data = data;
		this.name = name;
	}
	//---------------------------------------------------------------
	public Object[][] getData() {
		return data;
	}
	
	public boolean isCellEditable(int row, int column) {
		return (column == 0);
	}
	
	public String getColumnName(int column) {  
        return name[column];  
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int column) {
		return (getValueAt(0, column).getClass());
	}
	
	public int getColumnCount() {
		return name.length;
	}
	
	public int getRowCount() {
		return data.length;
	}
	
	public Object getValueAt(int row, int column) {
		return data[row][column];
	}
	
	public void setValueAt(Object value, int row, int column) {  
        data[row][column] = value;  
    }
}
