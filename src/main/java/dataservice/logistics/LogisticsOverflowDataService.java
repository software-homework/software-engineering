package dataservice.logistics;

import java.util.ArrayList;

import po.logistics.LogisticsOverflowPO;

public interface LogisticsOverflowDataService {
	public boolean add(LogisticsOverflowPO sofp);
	
	public boolean setApproval(LogisticsOverflowPO sofp,int approval);
	
	public ArrayList<LogisticsOverflowPO> getStockOverflow();
	
	public void update(LogisticsOverflowPO sofp);
	
	public void readFromFile();
	
	public void writeToFile();
}
