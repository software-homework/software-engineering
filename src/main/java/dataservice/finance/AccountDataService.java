package dataservice.finance;

import java.util.ArrayList;

import po.finance.AccountPO;
import po.finance.CashExpensesPO;
import po.finance.DocumentPO;
import po.finance.LedgerPO;
import exception.ExistPO;
import exception.NotFoundPO;

public interface AccountDataService 
{
	public boolean addAccount(AccountPO accountPO);
	public boolean deleteAccount(String id);
	public boolean updateAccount(String id_Before, String new_id, String difference) throws NotFoundPO, ExistPO;
	public ArrayList<Object> findAccount(String id);
	public ArrayList<Object> fuzzySearchAccount(String id);
	public ArrayList<Object> findAll(String id);
	/**
	 */
	public DocumentPO updateDocuments(String id, int approve);
	public ArrayList<Object> filterDocuments(String[] timeRange, String type, String customer, String operator, int approve);
	public boolean addDocuments(DocumentPO documentPO);
	/**
	 */
	public boolean updateCashExpenses(String id, int approve);
	public boolean addCashExpenses(CashExpensesPO cashExpensesPO);
	public ArrayList<Object> filterCashExpenses(String[] timeRange, String operator, int approve);
	
	/**
	 */
	public boolean AddLedger(LedgerPO ledgerPO);
	public ArrayList<Object> showLedger();
}
