package data.finance;

import io.Input;
import io.Output;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DecimalFormat;
import java.util.ArrayList;

import po.finance.AccountPO;
import po.finance.CashExpensesPO;
import po.finance.DocumentPO;
import po.finance.LedgerPO;
import dataservice.finance.AccountDataService;
import exception.ExistPO;
import exception.NotFoundPO;

public class AccountData extends UnicastRemoteObject implements AccountDataService{	

	private static final long serialVersionUID = -8868676826752697887L;

	private String accountPath = "account.txt";
	private String documentPath = "document.txt";
	private String cashExpensesPath = "cashExpenses.txt";
	private String ledgerPath = "ledger.txt";
	private ArrayList<Object> AccountPOlist = new ArrayList<Object>();
	private ArrayList<Object> DocumentPOlist = new ArrayList<Object>();
	private ArrayList<Object> CashExpensesList = new ArrayList<Object>();
	private ArrayList<Object> LedgerList = new ArrayList<Object>();
	
	/**
	 * 
	 * @throws RemoteException
	 */
	public AccountData() throws RemoteException {}
	/*********************************************************************
	 * @throws ExistPO 
	 ********************************************************************/	
	public boolean addAccount(AccountPO accountPO){
		Input input = new Input();
		AccountPOlist = input.input(accountPath);
		if(this.findAccount(accountPO.getId()).size() == 0){					
			AccountPOlist.add(accountPO);

			@SuppressWarnings("unused")
			Output output = new Output(AccountPOlist, accountPath);
			
			return true;
		}
		
		return false;
	}
	
	/*********************************************************************
	 * @throws NotFoundPO 
	 ********************************************************************/		
	public boolean deleteAccount(String id){
		if(this.findAccount(id).size() != 0){
			Input input = new Input();
			AccountPOlist = input.input(accountPath);
			
			for(int i = 0; i < AccountPOlist.size(); i++){
				AccountPO po = (AccountPO)AccountPOlist.get(i);
				if(po.getId().equals(id)){
					AccountPOlist.remove(i);
					break;
				}
			}
			
			@SuppressWarnings("unused")
			Output output = new Output(AccountPOlist, accountPath);
			
			return true;
		}
		
		return false;
	}
	
	/********************************************************************
	 * @throws NotFoundPO 	     
	 * @throws ExistPO       
	 ********************************************************************/		
	public boolean updateAccount(String id_Before, String new_id, String difference) throws NotFoundPO, ExistPO{		
		if(this.findAccount(id_Before).size() == 0)
			throw new NotFoundPO("数据库中不存在原帐户名!");
		if(this.findAccount(new_id).size() != 0 && !(id_Before.equals(new_id)))
			throw new ExistPO("新帐户名在数据库中已存在!");
			
		
		Input input = new Input();
		AccountPOlist = input.input(accountPath);
		for(int i = 0; i < AccountPOlist.size(); i++){			
			AccountPO po = (AccountPO)AccountPOlist.get(i);
			if(po.getId().equals(id_Before)){
				po.setId(new_id);
				
				DecimalFormat df = new DecimalFormat("#.00");
				String new_amount = df.format(Double.parseDouble(po.getAmountOfAccount()) + Double.parseDouble(difference));
				po.setAmountOfAccount(new_amount);
				AccountPOlist.set(i, po);
				break;
			}
		}
		@SuppressWarnings("unused")
		Output output = new Output(AccountPOlist, accountPath);
			
		return true;
	}

	/********************************************************************
	 ********************************************************************/
	public ArrayList<Object> findAccount(String id) {
		Input input = new Input();
		AccountPOlist = input.input(accountPath);
		
		ArrayList<Object> res = new ArrayList<Object>();
		
		for(int i = 0; i < AccountPOlist.size(); i++){
			if(((AccountPO)AccountPOlist.get(i)).getId().equals(id))
				res.add(AccountPOlist.get(i));
		}
		
		return res;
	}

	public ArrayList<Object> fuzzySearchAccount(String id) {
		ArrayList<Object> res = new ArrayList<Object>();
		
		Input input = new Input();
		AccountPOlist = input.input(accountPath);
		
		for(int i = 0; i < AccountPOlist.size(); i++){
			AccountPO temp = (AccountPO)(AccountPOlist.get(i));
			for(int j = 0; j <= temp.getId().length() - id.length(); j++){
				if((temp.getId().substring(j, j + id.length())).equals(id)){
					res.add(temp);
					break;
				}
			}
		}
		
		for(int k = 0; k < res.size(); k++){
			AccountPO temp = (AccountPO)res.get(k);
			if(temp.getId().equals(id)){
				res.set(k, res.get(0));
				res.set(0, temp);
				break;
			}
		}
		return res;
	}
	
	public ArrayList<Object> findAll(String id) {
		Input input = new Input();
		AccountPOlist = input.input(accountPath);
		
		for(int k = 0; k < AccountPOlist.size(); k++){
			AccountPO temp = (AccountPO)AccountPOlist.get(k);
			if(temp.getId().equals(id)){
				AccountPOlist.set(k, AccountPOlist.get(0));
				AccountPOlist.set(0, temp);
				break;
			}
		}
		return AccountPOlist;
	}
	/********************************************************************
	 * SKD FKD XJFYD
	 ********************************************************************/		
	@SuppressWarnings("unused")
	public boolean addDocuments(DocumentPO documentPO) {
		Input input = new Input();
		DocumentPOlist = input.input(documentPath);
		DocumentPOlist.add(documentPO);
		Output output = new Output(DocumentPOlist, documentPath);
		return true;
	}
	@SuppressWarnings("unused")
	public DocumentPO updateDocuments(String id, int approve){
		Input input = new Input();
		DocumentPOlist = input.input(documentPath);
		DocumentPO temp = null;
		for(int i = 0; i < DocumentPOlist.size(); i++){
			temp = ((DocumentPO)DocumentPOlist.get(i));
			if(temp.getId().equals(id)){
				temp.setApprove(approve);
				DocumentPOlist.set(i, temp);
				if(approve == 1)
					this.updateAmount(temp.getTransferList(), temp.getType());
				break;
			}
		}
		Output output = new Output(DocumentPOlist, documentPath);
		return temp;
	}
	
	public void updateAmount(ArrayList<String> list, String type){
		if(type.equals("SKD")){
			for(String i : list){
				try {
					this.updateAccount(i.split(";")[0], i.split(";")[0], i.split(";")[1]);
				} catch (NotFoundPO | ExistPO e) {
					e.printStackTrace();
				}
			}
		}
		else if(type.equals("FKD")){
			for(String i : list){
				try {
					this.updateAccount(i.split(";")[0], i.split(";")[0], "-" + i.split(";")[1]);
				} catch (NotFoundPO | ExistPO e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public ArrayList<Object> filterDocuments(String[] timeRange, String type,
			String customer, String operator, int approve) {
		Input input = new Input();
		DocumentPOlist = input.input(documentPath);
		if(timeRange != null){
			for(int i = 0; i < DocumentPOlist.size(); i++){
				if(!(((DocumentPO)DocumentPOlist.get(i)).getTime().compareTo(timeRange[0]) >= 0 &&
						((DocumentPO)DocumentPOlist.get(i)).getTime().compareTo(timeRange[1]) <= 0)){
					DocumentPOlist.remove(i);
					i--;
				}
			}
		}
		if(type != null){
			for(int i = 0; i < DocumentPOlist.size(); i++){
				if(!(((DocumentPO)DocumentPOlist.get(i)).getType().equals(type))){
					DocumentPOlist.remove(i);
					i--;
				}
			}
		}
		if(customer != null){
			for(int i = 0; i < DocumentPOlist.size(); i++){
				if(!(((DocumentPO)DocumentPOlist.get(i)).getCustomerName().equals(customer))){
					DocumentPOlist.remove(i);
					i--;
				}
			}
		}	
		if(operator != null){
			for(int i = 0; i < DocumentPOlist.size(); i++){
				if(!(((DocumentPO)DocumentPOlist.get(i)).getOperator().equals(operator))){
					DocumentPOlist.remove(i);
					i--;
				}
			}
		}	
		if(approve != 0){
			for(int i = 0; i < DocumentPOlist.size(); i++){
				if(((DocumentPO)DocumentPOlist.get(i)).getApprove() != approve){
					DocumentPOlist.remove(i);
					i--;
				}
			}
		}	
		return DocumentPOlist;
	}
	
	@SuppressWarnings("unused")
	public boolean updateCashExpenses(String id, int approve){
		Input input = new Input();
		CashExpensesList = input.input(cashExpensesPath);
		for(int i = 0; i < CashExpensesList.size(); i++){
			CashExpensesPO temp = (CashExpensesPO)CashExpensesList.get(i);
			if(temp.getId().equals(id)){
				temp.setApprove(approve);
				CashExpensesList.set(i, temp);
				break;
			}
		}
		Output output = new Output(CashExpensesList, cashExpensesPath);
		return true;
	}
	
	@SuppressWarnings("unused")
	public boolean addCashExpenses(CashExpensesPO cashExpensesPO) {
		Input input = new Input();
		CashExpensesList = input.input(cashExpensesPath);
		CashExpensesList.add(cashExpensesPO);
		//�����˻����
		try {
			this.updateAccount(cashExpensesPO.getAccount(), cashExpensesPO.getAccount(), "-" + cashExpensesPO.getTotal());
		} catch (NotFoundPO | ExistPO e) {
			e.printStackTrace();
		}
		Output output = new Output(CashExpensesList, cashExpensesPath);
		return true;
	}
	
	public ArrayList<Object> filterCashExpenses(String[] timeRange, String operator, int approve) {
		Input input = new Input();
		CashExpensesList = input.input(cashExpensesPath);
		if(timeRange != null){
			for(int i = 0; i < CashExpensesList.size(); i++){
				if(!(((CashExpensesPO)CashExpensesList.get(i)).getTime().compareTo(timeRange[0]) >= 0 &&
						((CashExpensesPO)CashExpensesList.get(i)).getTime().compareTo(timeRange[1]) <= 0)){
					CashExpensesList.remove(i);
					i--;
				}
			}
		}
		if(operator != null){
			for(int i = 0; i < CashExpensesList.size(); i++){
				if(!(((CashExpensesPO)CashExpensesList.get(i)).getOperator().equals(operator))){
					CashExpensesList.remove(i);
					i--;
				}
			}
		}
		if(approve != 0){
			for(int i = 0; i < CashExpensesList.size(); i++){
				if(((CashExpensesPO)CashExpensesList.get(i)).getApprove() != approve){
					CashExpensesList.remove(i);
					i--;
				}
			}
		}	
		return CashExpensesList;
	}
	/********************************************************************
	 ********************************************************************/		

	@SuppressWarnings("unused")
	public boolean AddLedger(LedgerPO ledgerPO) {
		Input input = new Input();
		LedgerList = input.input(ledgerPath);
		LedgerList.add(ledgerPO);
		Output output = new Output(LedgerList, ledgerPath);
		
		return true;
	}

	public ArrayList<Object> showLedger() {		
		Input input = new Input();
		LedgerList = input.input(ledgerPath);
		
		return LedgerList;
	}	
}