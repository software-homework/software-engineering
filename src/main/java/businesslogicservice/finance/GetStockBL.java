package businesslogicservice.finance;

import java.util.ArrayList;

import vo.logistics.PresentVO;
import vo.logistics.LogisticsLossVO;
import vo.logistics.LogisticsOverflowVO;

public interface GetStockBL {
	public ArrayList<LogisticsLossVO> getCheckedStockLoss();
	public ArrayList<LogisticsOverflowVO> getCheckedStockOverflow();
	public ArrayList<PresentVO> getCheckedPresent();
}
