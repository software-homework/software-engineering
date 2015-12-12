package businesslogicservice.courier;

import java.util.ArrayList;

import vo.courier.CustomerVO;

public interface CustomerBLService {
	public int getID();
	public boolean AddC(CustomerVO customer);
	public boolean DeleteC(CustomerVO customer);
	public boolean UpdateC(CustomerVO customer);
	public ArrayList<CustomerVO> ShowC();
	public ArrayList<CustomerVO> FinC(CustomerVO customer);
	public boolean updateCustomer(String name,double newProceed,double newPay );

}
