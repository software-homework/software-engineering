package dataservice.logistics;

import java.util.ArrayList;

import po.logistics.PresentPO;

public interface PresentDataService {
	public boolean add(PresentPO pp);
	
	public ArrayList<PresentPO> getPresent();
	
	public void update(PresentPO pp);
	
	
}
