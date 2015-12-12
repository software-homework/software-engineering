package vo.courier;

import java.util.ArrayList;

import vo.logistics.CommodityVO;

public class CourierVO {
	public String id;
	public String customer;
	public int repertory;
	public String user;
	public double pretotal;
	public double discount;
	public double Voucher;
	public double posttotal;
	public String remakes;
	public ArrayList<CommodityVO> salesList;
	public int approval;
	
	public CourierVO(){
		
	}
}
