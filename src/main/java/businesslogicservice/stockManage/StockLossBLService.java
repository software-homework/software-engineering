package businesslogicservice.stockManage;

import java.util.ArrayList;

import vo.stockManage.StockLossVO;

public interface StockLossBLService {
    public boolean add(StockLossVO slv);
	
	public boolean setApproval(StockLossVO slv,int approval);
	
	public ArrayList<StockLossVO> getStockLoss();
}
