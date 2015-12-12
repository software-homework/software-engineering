package dataservice.courier;

import java.util.ArrayList;

import po.courier.ImportPO;

public interface ImportDataService {
	public boolean insert (ImportPO po);
	public ArrayList<ImportPO> getDataI();
	public void update (ImportPO po);
}
