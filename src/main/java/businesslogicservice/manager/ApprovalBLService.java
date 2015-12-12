package businesslogicservice.manager;

import vo.courier.ImportVO;
import vo.courier.CourierVO;
import vo.finance.CashExpensesVO;
import vo.finance.DocumentVO;
import vo.logistics.PresentVO;
import vo.logistics.LogisticsLossVO;
import vo.logistics.LogisticsOverflowVO;

/**
 * 
 * @date 2014年12月11日
 * @time 下午5:10:04
 * @author stk
 *
 */

/*
 * 审批单据逻辑接口
 */
public interface ApprovalBLService {
	public String[] show();
	//---------------------------------------------
	public CourierVO finSales(String id);
	public ImportVO finImport(String id);
	public DocumentVO finDocument(String id);
	public LogisticsLossVO finStockLoss(String id);
	public LogisticsOverflowVO finStockOverflow(String id);
	public PresentVO finPresent(String id);
	public CashExpensesVO finCashExpenses(String id);
	//------------------------------------------------
	public boolean modifySales(CourierVO vo);
	public boolean modifyImport(ImportVO vo);
	public boolean modifyPresent(PresentVO vo);
	//---------------------------------------------
	public int[] approve(String[] id, int approval);
}
