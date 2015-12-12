package businesslogicservice.logistics;

import java.util.ArrayList;

import vo.logistics.LogisticsLossVO;

public interface LogisticsLossBLService {
    public boolean add(LogisticsLossVO slv);
	
	public boolean setApproval(LogisticsLossVO slv,int approval);
	
	public ArrayList<LogisticsLossVO> getStockLoss();
}
