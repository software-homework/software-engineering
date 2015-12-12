package businesslogicservice.finance;

import java.util.ArrayList;

import vo.finance.AccountVO;
import vo.finance.CashExpensesVO;
import vo.finance.DocumentVO;
import vo.finance.LedgerVO;
import vo.salesman.ImportVO;
import vo.salesman.SalesVO;
import vo.stockManage.PresentVO;
import vo.stockManage.StockLossVO;
import vo.stockManage.StockOverflowVO;
import exception.ExistPO;
import exception.NotFoundPO;

public interface AccountBLService
{
	public boolean addAccount(String id, String amountOfAccount);	
	public boolean deleteAccount(String id);
	public boolean updateAccount(String id_Before, String new_id, String difference) throws NotFoundPO, ExistPO;
	public ArrayList<AccountVO> findAccount(String id);
	public ArrayList<AccountVO> fuzzySearchAccount(String id);
	public ArrayList<AccountVO> findAll(String id);
	/**
	 *SKD FKD 
	 */
	public boolean updateDocuments(String id, int approve);
	public ArrayList<DocumentVO> getUncheckedDocuments();
	
	public boolean addDocuments(String type, String id, String customerName, String customerType, String operator,
			ArrayList<String> transferList, double total, String time, long idNum);
	public ArrayList<DocumentVO> filterDocuments(String[] timeRange, String type, String customer, String operator, int approve);
	/**
	 *XJFYD 
	 */
	public boolean updateCashExpenses(String id, int approve);
	public ArrayList<CashExpensesVO> getUncheckedCashExpenses();
	public boolean addCashExpenses(String id, String operator, String account, 
			ArrayList<String> list, double total, String time, long idNum);
	public ArrayList<CashExpensesVO> filterCashExpenses(String[] timeRange, String operator);

	public String createID(String type);
	/**
	 * 表单 导出 红冲
	 */
	public String[][] showDetail(String time1, String time2, String notDecided,
		 String customer, String operator);
	public String[][] showHistory(String time1, String time2, String billtype, 
			String customer, String operator);
	public boolean output(String[][] res);
	public boolean red(ImportVO importVO, SalesVO salesVO);
	public boolean copy(ImportVO importVO, SalesVO salesVO);
	/**
	 * 
	 */
	public boolean AddLedger(String time, String type);
	public ArrayList<LedgerVO> showLedger();
	public LedgerVO findOneLedger(String time);
	/**find One bill*/
	public DocumentVO findOneDocument(String id);
	public CashExpensesVO findOneCashExpenses(String id);
	public ImportVO findOneImport(String id);
	public SalesVO findOneSales(String id);
	public StockOverflowVO findOneStockOverflow(String id);
	public StockLossVO findOneStockLoss(String id);
	public PresentVO findOnePresent(String id);
}
