package dataservice.courier;

import java.util.ArrayList;

import po.courier.CourierPO;

public interface CourierDataService {
	public boolean insert (CourierPO po);
	public ArrayList<CourierPO> getDataS();
	public void update (CourierPO po);

}
