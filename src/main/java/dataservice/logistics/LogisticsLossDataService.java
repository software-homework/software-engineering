package dataservice.logistics;

import java.util.ArrayList;

import po.logistics.LogisticsLossPO;

public interface LogisticsLossDataService {
    public boolean add(LogisticsLossPO slp);
	
	public boolean setApproval(LogisticsLossPO slp,int approval);
	
	public ArrayList<LogisticsLossPO> getStockLoss();
	
	public void update(LogisticsLossPO slp);
	
	public void readFromFile();
	
	public void writeToFile();
}
