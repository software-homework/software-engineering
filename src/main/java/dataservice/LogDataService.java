package dataservice;

import java.util.ArrayList;

import po.LogPO;

public interface LogDataService {
	public void addLog(LogPO logpo);
	public ArrayList<LogPO> getAllLog();

}
