package vo.salesman;

import java.util.ArrayList;

import vo.stockManage.CommodityVO;

public class ImportVO {
	public String id;
	public String customer;
	public int repertory;
	public String user;
	public double total;
	public String remakes;
	public ArrayList<CommodityVO> importList;
	public int approval;
	
	public ImportVO(){
		
	}

}
