package vo.courier;

import java.util.ArrayList;

import vo.logistics.CommodityVO;

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
