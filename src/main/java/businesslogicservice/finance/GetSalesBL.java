package businesslogicservice.finance;

import java.util.ArrayList;

import vo.courier.CourierVO;

public interface GetSalesBL 
{
	public ArrayList<CourierVO> getCheckedSales();
	
	public String getsID();
	public String getsrID();
	public boolean CheckS(CourierVO svo);
	public boolean approvalSales(String id,int approval);
}
