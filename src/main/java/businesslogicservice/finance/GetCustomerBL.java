package businesslogicservice.finance;

import java.util.ArrayList;

import vo.courier.CustomerVO;

public interface GetCustomerBL{
	public boolean updateCustomer(String name,double newProceed,double newPay);
	public ArrayList<CustomerVO> ShowC();
}
