package ui.logistics;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import vo.logistics.CategoryVO;
import vo.logistics.CommodityVO;

public class CommodityTable implements TableModel{
	public String column[] = new String[]{"商品编号","商品名称", "型号", "数量", "默认进价", "默认售价", "最近一次进价", "最近一次售价"};
	public LogisticsMain sm;
	public CategoryVO cv;
	
	public CommodityTable(LogisticsMain sm){
		this.sm = sm;
		cv = sm.cv;
	}
	
	public String getItem(CommodityVO commodity, int nIndex){
		String szRet = "";
		switch (nIndex)
		{
		case 0:
			szRet = commodity.id;
			break;
		case 1:
			szRet = commodity.commodityname;
			break;
		case 2:
			szRet = commodity.model;
			break;
		case 3:
			szRet = "" + commodity.number;
			break;
		case 4:
			szRet = "" + commodity.priceIn;
			break;
		case 5:
			szRet = "" + commodity.priceOut;
			break;
		case 6:
			szRet = "" + commodity.lastPriceIn;
			break;
	    case 7:
	    	szRet = "" + commodity.lastPriceOut;
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
		int l = sm.getRowCount();
		if (cv == null){
			return l;
		}
		return Math.max(cv.commodityList.size(), l);
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
		if(cv == null){
			return "";
		}
		if(cv.commodityList.size() == 0){
			return "";
		}
		if(rowIndex >= cv.commodityList.size()){
			return "";
		}
		return getItem(cv.commodityList.get(rowIndex),columnIndex);
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
