package businesslogicservice.finance;

import java.util.ArrayList;

import vo.stockManage.PresentVO;
import vo.stockManage.StockLossVO;
import vo.stockManage.StockOverflowVO;

public interface GetStockBL {
	public ArrayList<StockLossVO> getCheckedStockLoss();
	public ArrayList<StockOverflowVO> getCheckedStockOverflow();
	public ArrayList<PresentVO> getCheckedPresent();
}
