package ui.logistics;

import java.util.ArrayList;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import vo.logistics.LogisticsVO;

public class ShowLogisticsTable implements TableModel{
	public String column[] = new String[]{"快递编号","快递名称","型号", "出库数量", "出库金额", "入库数量", "入库金额", "销售数量","销售金额","快递接数量","快递接金额","库存数量"};
	public ShowLogistics ss;
	public ArrayList<LogisticsVO> stockList;
	
	public ShowLogisticsTable(ShowLogistics ss){
		this.ss = ss;
		stockList = ss.stockList;
	}
	
	public String getItem(LogisticsVO stock, int nIndex){
		String szRet = "";
		switch (nIndex){
		case 0:
			szRet = stock.id;
			break;
		case 1:
			szRet = stock.name;
			break;
		case 2:
			szRet = stock.model;
			break;
		case 3:
			szRet = "" + stock.outnumber;
			break;
		case 4:
			szRet = "" + stock.outmoney;
			break;
		case 5:
			szRet = "" + stock.innumber;
			break;
		case 6:
			szRet = "" + stock.inmoney;
			break;
	    case 7:
	    	szRet = "" + stock.salesnumber;
		    break;
	    case 8:
			szRet = "" + stock.salesmoney;
			break;
		case 9:
			szRet = "" + stock.importnumber;
			break;
	    case 10:
	    	szRet = "" + stock.importmoney;
		    break;
	    case 11:
	    	szRet = "" + stock.totalnumber;
		    break;    
	    }
		
		if(szRet == null){
			return "";
		}
		return szRet;
	}
	 
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		int l = ss.getRowCount();
		if (ss.stockList == null){
			return l;
		}
		return Math.max(ss.stockList.size(), l);
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return column.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		// TODO Auto-generated method stub
		return column[columnIndex];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		if(ss.stockList == null){
			return "";
		}
		if(ss.stockList.size() == 0){
			return "";
		}
		if(rowIndex >= ss.stockList.size()){
			return "";
		}
		return getItem(ss.stockList.get(rowIndex),columnIndex);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
	}
	
}
