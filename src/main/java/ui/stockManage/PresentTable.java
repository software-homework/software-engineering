package ui.stockManage;

import java.util.ArrayList;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import vo.stockManage.PresentVO;

public class PresentTable implements TableModel{
	public String column[] = new String[]{"单据编号","赠送商品及数量","制定时间","审批状态"};
	public CheckPresent cp;
	public ArrayList<PresentVO> list;
	
	public PresentTable(CheckPresent cp){
		this.cp = cp;
		this.list = cp.list;
	}
	
	public String getItem(PresentVO pv, int nIndex){
		String szRet = "";
		switch (nIndex){
		case 0:
			szRet = pv.id;
			break;
		case 1:
			for(String  s : pv.presentList){
				szRet = szRet + s + "  ";
			}
			break;
		case 2:
			szRet = pv.time;
			break;
		case 3:
			if(pv.approval == 1){
				szRet = "审批通过";
			}
			else if(pv.approval == 3){
				szRet = "未审批";
			}
			else if(pv.approval == 2){
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
		int l = cp.getRowCount();
		if (cp.list == null){
			return l;
		}
		return Math.max(cp.list.size(), l);
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
		if(cp.list == null){
			return "";
		}
		if(cp.list.size() == 0){
			return "";
		}
		if(rowIndex >= cp.list.size()){
			return "";
		}
		return getItem(cp.list.get(rowIndex),columnIndex);
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
