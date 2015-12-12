package businesslogicservice.salesman;

import java.util.ArrayList;

import vo.salesman.SalesVO;

public interface SalesBLService {
	public String getsID();
	public String getsrID();
	public boolean CheckS(SalesVO svo);
	public ArrayList<SalesVO> ShowS();
	public SalesVO findSales(String id);
}
