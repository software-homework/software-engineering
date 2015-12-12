package ui.stockManage;

import java.util.ArrayList;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import vo.stockManage.StockLossVO;

public class StockLossTable implements TableModel{
	public String column[] = new String[]{"单据编号","商品名称","商品型号","报损数量","制定时间","审批状态"};
	public CheckStockLoss csl;
	public ArrayList<StockLossVO> list;
	
	public StockLossTable(CheckStockLoss csl){
		this.csl = csl;
		this.list = csl.list;
	}
	
	public String getItem(StockLossVO sofv, int nIndex){
		String szRet = "";
		switch (nIndex){
		case 0:
			szRet = sofv.id;
			break;
		case 1:
			szRet = sofv.name;
			break;
		case 2:
			szRet = sofv.model;
			break;
		case 3:
			szRet = "" + sofv.lossNumber;
			break;
		case 4:
			szRet = sofv.time;
			break;
		case 5:
			if(sofv.approval == 1){
				szRet = "审批通过";
			}
			else if(sofv.approval == 3){
				szRet = "未审批";
			}
			else if(sofv.approval == 2){
				szRet = "审批不通过";
			}
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
		int l = csl.getRowCount();
		if (csl.list == null){
			return l;
		}
		return Math.max(csl.list.size(), l);
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
		if(csl.list == null){
			return "";
		}
		if(csl.list.size() == 0){
			return "";
		}
		if(rowIndex >= csl.list.size()){
			return "";
		}
		return getItem(csl.list.get(rowIndex),columnIndex);
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
