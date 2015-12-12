package dataservice.logistics;

import java.util.ArrayList;

import po.logistics.LogisticsSnapPO;

public interface LogisticsSnapDataService {
	public boolean add(LogisticsSnapPO ssp);
	
	public void remove();
	
	public ArrayList<LogisticsSnapPO> getStocksnap();
	
	public LogisticsSnapPO getLast();
}
