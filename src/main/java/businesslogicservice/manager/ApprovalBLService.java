package businesslogicservice.manager;

import vo.finance.CashExpensesVO;
import vo.finance.DocumentVO;
import vo.salesman.ImportVO;
import vo.salesman.SalesVO;
import vo.stockManage.PresentVO;
import vo.stockManage.StockLossVO;
import vo.stockManage.StockOverflowVO;

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
	public SalesVO finSales(String id);
	public ImportVO finImport(String id);
	public DocumentVO finDocument(String id);
	public StockLossVO finStockLoss(String id);
	public StockOverflowVO finStockOverflow(String id);
	public PresentVO finPresent(String id);
	public CashExpensesVO finCashExpenses(String id);
	//------------------------------------------------
	public boolean modifySales(SalesVO vo);
	public boolean modifyImport(ImportVO vo);
	public boolean modifyPresent(PresentVO vo);
	//---------------------------------------------
	public int[] approve(String[] id, int approval);
}
