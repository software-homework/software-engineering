package dataservice.salesman;

import java.util.ArrayList;

import po.salesman.SalesPO;

public interface SalesDataService {
	public boolean insert (SalesPO po);
	public ArrayList<SalesPO> getDataS();
	public void update (SalesPO po);

}
