package businesslogicservice.manager;

import java.util.ArrayList;

import vo.courier.ImportVO;
import vo.courier.CourierVO;
import vo.finance.CashExpensesVO;
import vo.finance.DocumentVO;
import vo.logistics.PresentVO;
import vo.logistics.LogisticsLossVO;
import vo.logistics.LogisticsOverflowVO;

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
	public CourierVO findOneSales(String id);
	public DocumentVO findOneDocument(String id);
	public LogisticsLossVO findOneStockLoss(String id);
	public LogisticsOverflowVO findOneStockOverflow(String id);
	public PresentVO findOnePresent(String id);
	public CashExpensesVO findOneCashExpenses(String id);
}
