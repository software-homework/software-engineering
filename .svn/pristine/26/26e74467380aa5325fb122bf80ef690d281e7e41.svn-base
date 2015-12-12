package businesslogicservice.manager;

import java.util.ArrayList;

import vo.finance.CashExpensesVO;
import vo.finance.DocumentVO;
import vo.salesman.ImportVO;
import vo.salesman.SalesVO;
import vo.stockManage.PresentVO;
import vo.stockManage.StockLossVO;
import vo.stockManage.StockOverflowVO;

/**
 * 
 * @date 2014年12月7日
 * @time 下午2:49:19
 * @author stk
 *
 */

/*
 * 获取财务类单据信息
 */
public interface GetDocument {
	public boolean updateDocuments(String id, int approval);
	public ArrayList<DocumentVO> getUncheckedDocuments();
	public String[][] showDetail(String time1, String time2, String notDecided, String customer, String operator);
	public String[][] showHistory(String time1, String time2, String billtype, String customer, String operator);
	public boolean updateCashExpenses(String id, int approval);
	public ArrayList<CashExpensesVO> getUncheckedCashExpenses();
	public ImportVO findOneImport(String id);
	public SalesVO findOneSales(String id);
	public DocumentVO findOneDocument(String id);
	public StockLossVO findOneStockLoss(String id);
	public StockOverflowVO findOneStockOverflow(String id);
	public PresentVO findOnePresent(String id);
	public CashExpensesVO findOneCashExpenses(String id);
}
