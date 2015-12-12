package dataservice.courier;

import java.util.ArrayList;

import po.courier.CustomerPO;

public interface CustomerDataService {
	public CustomerPO find(String name);
	public boolean insert(CustomerPO po);
	public boolean delete(CustomerPO po);
	public boolean update(CustomerPO po);
	public ArrayList<CustomerPO> getData();
}
