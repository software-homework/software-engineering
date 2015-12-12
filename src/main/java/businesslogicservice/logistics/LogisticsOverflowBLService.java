package businesslogicservice.logistics;

import java.util.ArrayList;

import vo.logistics.LogisticsOverflowVO;

public interface LogisticsOverflowBLService {
	public boolean add(LogisticsOverflowVO sofv);
	
	public boolean setApproval(LogisticsOverflowVO sofv,int approval);
	
	public ArrayList<LogisticsOverflowVO> getStockOverflow();
}
