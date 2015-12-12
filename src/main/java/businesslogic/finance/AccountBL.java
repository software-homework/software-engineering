package businesslogic.finance;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import businesslogic.courier.CustomerBL;
import businesslogic.courier.ImportBL;
import businesslogic.courier.CourierBL;
import businesslogic.logistics.PresentBL;
import businesslogic.logistics.LogisticsLossBL;
import businesslogic.logistics.LogisticsOverflowBL;
import businesslogicservice.finance.AccountBLService;
import businesslogicservice.finance.GetCustomerBL;
import businesslogicservice.finance.GetImportBL;
import businesslogicservice.finance.GetSalesBL;
import businesslogicservice.finance.GetStockBL;
import data.finance.AccountData;
import dataservice.finance.AccountDataService;
import exception.ExistPO;
import exception.NotFoundPO;
import po.finance.AccountPO;
import po.finance.CashExpensesPO;
import po.finance.DocumentPO;
import po.finance.LedgerPO;
import vo.courier.CustomerVO;
import vo.courier.ImportVO;
import vo.courier.CourierVO;
import vo.finance.AccountVO;
import vo.finance.CashExpensesVO;
import vo.finance.DocumentVO;
import vo.finance.LedgerVO;
import vo.logistics.CommodityVO;
import vo.logistics.PresentVO;
import vo.logistics.LogisticsLossVO;
import vo.logistics.LogisticsOverflowVO;

public class AccountBL implements AccountBLService, businesslogicservice.manager.GetDocument
{
	//调用accountDataService接口方法
	AccountDataService accountDataService = null;
	
	public AccountBL(){
		try {
			accountDataService = new AccountData();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/********************************************************************
	 ********************************************************************/
	public boolean addAccount(String id, String amountOfAccount) {		
		AccountPO accountPO = new AccountPO(id, amountOfAccount);
		return accountDataService.addAccount(accountPO);
	}
	
	public boolean deleteAccount(String id){
		return accountDataService.deleteAccount(id);
	}

	public boolean updateAccount(String id_Before, String new_id, String difference) throws NotFoundPO, ExistPO{		
		return accountDataService.updateAccount(id_Before, new_id, difference);
	}

	/********************************************************************
	 ********************************************************************/
	public ArrayList<AccountVO> findAccount(String id) {
		ArrayList<Object> list = accountDataService.findAccount(id);
		
		ArrayList<AccountVO> res = new ArrayList<AccountVO>();
		for(int i = 0; i < list.size(); i++){
			AccountPO temp = (AccountPO)list.get(i);
			res.add(temp.toVO());
		}
		
		return res;
	}
	
	public ArrayList<AccountVO> fuzzySearchAccount(String id) {
		ArrayList<Object> list = accountDataService.fuzzySearchAccount(id);
		
		ArrayList<AccountVO> res = new ArrayList<AccountVO>();
		for(int i = 0; i < list.size(); i++){
			AccountPO temp = (AccountPO)list.get(i);
			res.add(temp.toVO());
		}
		
		return res;
	}
	
	public ArrayList<AccountVO> findAll(String id){
		ArrayList<Object> list = accountDataService.findAll(id);
		
		ArrayList<AccountVO> res = new ArrayList<AccountVO>();
		for(int i = 0; i < list.size(); i++){
			AccountPO temp = (AccountPO)list.get(i);
			res.add(temp.toVO());
		}
		return res;
	}
	/********************************************************************
	 * SKD FKD XJFYD
	 ********************************************************************/
	public boolean updateDocuments(String id, int approve) {
		GetCustomerBL getCustomerBL = new CustomerBL();
		DocumentPO documentPO = accountDataService.updateDocuments(id, approve);
		
		if(approve == 1){
			if(documentPO.getType().equals("SKD")){
				getCustomerBL.updateCustomer(documentPO.getCustomerName(), documentPO.getTotal() * (-1), 0.00);
			}
			else if(documentPO.getType().equals("FKD")){
				getCustomerBL.updateCustomer(documentPO.getCustomerName(), 0.00, documentPO.getTotal() * (-1));
			}
		}
		return true;
	}
	
	public ArrayList<DocumentVO> getUncheckedDocuments() {
		ArrayList<DocumentVO> res = new ArrayList<DocumentVO>();
		ArrayList<Object> temp = accountDataService.filterDocuments(null, null, null, null, 3);
		for(Object i : temp){
			res.add(((DocumentPO)i).ToVO());
		}
		return res;
	}
	
	public boolean addDocuments(String type, String id, String customerName, String customerType, String operator,
			ArrayList<String> transferList, double total, String time, long idNum){
		//total
		DecimalFormat df = new DecimalFormat("#.00");
        total = Double.parseDouble(df.format(total));
		return accountDataService.addDocuments(new DocumentPO(type, id, customerName, customerType, 
				operator, transferList, total, time, idNum));
	}
	
	public ArrayList<DocumentVO> filterDocuments(String[] timeRange, String type,
			String customer, String operator, int approve){
		ArrayList<Object> temp = accountDataService.filterDocuments(timeRange, type, customer, operator, approve);
		ArrayList<DocumentVO> res = new ArrayList<DocumentVO>();
		for(Object i : temp){
			res.add(((DocumentPO)i).ToVO());
		}
		return res;
	}
	
	public boolean updateCashExpenses(String id, int approve){
		return accountDataService.updateCashExpenses(id, approve);
	}
	public ArrayList<CashExpensesVO> getUncheckedCashExpenses(){
		ArrayList<Object> temp = accountDataService.filterCashExpenses(null, null, 3);
		ArrayList<CashExpensesVO> res = new ArrayList<CashExpensesVO>();
		for(Object i : temp){
			res.add(((CashExpensesPO)i).ToVO());
		}
		return res;
	}
	
	public boolean addCashExpenses(String id, String operator, String account, 
			ArrayList<String> list, double total, String time, long idNum) {
		//total
		DecimalFormat df = new DecimalFormat("#.00");
		total = Double.parseDouble(df.format(total));
		return accountDataService.addCashExpenses(new CashExpensesPO(id, operator, account, list, total, time, idNum));
	}
	
	public ArrayList<CashExpensesVO> filterCashExpenses(String[] timeRange, String operator) {
		ArrayList<Object> temp = accountDataService.filterCashExpenses(timeRange, operator, 1);
		ArrayList<CashExpensesVO> res = new ArrayList<CashExpensesVO>();
		for(Object i : temp){
			res.add(((CashExpensesPO)i).ToVO());
		}
		return res;
	}

	public String createID(String type){
		String id = new String();
		long idNum;
		String time;
		ArrayList<Object> temp = new ArrayList<Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		time = sdf.format(new Date());
		switch(type){
			case "FKD":
			case "SKD":
				temp = accountDataService.filterDocuments(new String[]{time, time}, type, null, null, 0);
				if(temp.size() == 0){
					id = type + "-" + time + "-" + "10000";
					idNum = 10000;
				}
				else{
					idNum = ((DocumentPO)(temp.get(temp.size() - 1))).getIdNum() + 1;
					id = type + "-" + time + "-" + idNum;
				}
				break;
			case "XJFYD":
				temp = accountDataService.filterCashExpenses(null, null, 0);
				if(temp.size() == 0){
					id = type + "-" + time + "-" + "10000";
					idNum = 10000;
				}
				else{
					idNum = ((CashExpensesPO)(temp.get(temp.size() - 1))).getIdNum() + 1;
					id = type + "-" + time + "-" + idNum;
				}
				break;
		}
		
		return id;
	}
	
	/********************************************************************
	 ********************************************************************/
	public String[][] showDetail(String time1, String time2, String notDecided,
			String customer, String operator) {
		GetSalesBL getSalesBL = new CourierBL();
		ArrayList<CourierVO> list = getSalesBL.getCheckedSales();
		ArrayList<String> temp = new ArrayList<String>();
		//除去退货单
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).id.charAt(3) != '-'){
				list.remove(i);
				i--;
			}
		}
		//筛选时间     客户    业务员
		for(int i = 0; i < list.size(); i++){
			if(!(list.get(i).user.equals(operator)) || !(list.get(i).customer.equals(customer)) ||
					!((list.get(i).id.substring(4, 12).compareTo(time1) >= 0) && (list.get(i).id.substring(4, 12).compareTo(time2) <= 0))){
				list.remove(i);
				i--;
			}
		}
		//选择符合条件的商品并添加到res
		NumberFormat ddf=NumberFormat.getNumberInstance() ; 
		ddf.setMaximumFractionDigits(2); 
		for(int i = 0; i < list.size(); i++){
			ArrayList<CommodityVO> salesList = list.get(i).salesList;
			for(int j = 0; j < salesList.size(); j++){
				if(salesList.get(j).commodityname.equals(notDecided)){
					String s = list.get(i).id.substring(4, 8) + "/" + list.get(i).id.substring(8, 10) + "/" + list.get(i).id.substring(10, 12) + ";"
							+ salesList.get(j).commodityname + ";" + salesList.get(j).model + ";" + 
							salesList.get(j).number + ";";
					
					double price = salesList.get(j).lastPriceOut;
					double total = salesList.get(j).number * salesList.get(j).priceOut;
					String s1 = ddf.format(price);
					String s2 = ddf.format(total);
					s = s + s1 + ";" + s2;
					temp.add(s);
				}
			}
		}
		
		String[][] res = new String[temp.size()][6];
		for(int i = 0; i < temp.size(); i++){
			res[i] = temp.get(i).split(";");
		}

		return res;
	}

	/********************************************************************
	 ********************************************************************/
	public String[][] showHistory(String time1, String time2, String billtype, String customer, String operator) {
		GetSalesBL getSalesBL = new CourierBL();
		GetImportBL getImportBL = new ImportBL();
		GetStockBL getStockLoss = new LogisticsLossBL();
		GetStockBL getStockOverFlow = new LogisticsOverflowBL();
		GetStockBL getStockPresent = new PresentBL();
		
		ArrayList<LogisticsLossVO> stockLoss = new ArrayList<LogisticsLossVO>();
		ArrayList<LogisticsOverflowVO> stockOverFlow = new ArrayList<LogisticsOverflowVO>();
		ArrayList<PresentVO> stockPresent = new ArrayList<PresentVO>();
		ArrayList<CourierVO> sales = new ArrayList<CourierVO>();
		ArrayList<ImportVO> imports = new ArrayList<ImportVO>();
		ArrayList<DocumentVO> documents = this.filterDocuments(new String[]{time1, time2}, null, customer, operator, 1);
		ArrayList<CashExpensesVO> cashExpenses = this.filterCashExpenses(new String[]{time1,time2}, operator);
		String[][] id = null;
		if(billtype.equals("销售类单据")){
			sales = getSalesBL.getCheckedSales();
			for(int i = 0; i < sales.size(); i++){
				if(!(sales.get(i).user.equals(operator)) || !(sales.get(i).customer.equals(customer)) ||
						!((sales.get(i).id.split("-")[1].compareTo(time1) >= 0) && (sales.get(i).id.split("-")[1].compareTo(time2) <= 0))){
					sales.remove(i);
					i--;
				}
			}
			id = new String[sales.size()][1];
			for(int i = 0; i < sales.size(); i++){
				id[i][0] = sales.get(i).id;
			}
		}
		else if(billtype.equals("进货类单据")){
			imports = getImportBL.getCheckedImport();
			for(int i = 0; i < imports.size(); i++){
				if(!(imports.get(i).user.equals(operator)) || !(imports.get(i).customer.equals(customer)) ||
						!((imports.get(i).id.split("-")[1].compareTo(time1) >= 0) && (imports.get(i).id.split("-")[1].compareTo(time2) <= 0))){
					imports.remove(i);
					i--;
				}
			}
			id = new String[imports.size()][1];
			for(int i = 0; i < imports.size(); i++){
				id[i][0] = imports.get(i).id;
			}
		}
		else if(billtype.equals("财务类单据")){
			id = new String[documents.size() + cashExpenses.size()][1];
			for(int i = 0; i < id.length; i++){
				if(i < documents.size())
					id[i][0] = documents.get(i).id;
				else
					id[i][0] = cashExpenses.get(i - documents.size()).id;
			}
		}
		else if(billtype.equals("库存类单据")){
			stockLoss = getStockLoss.getCheckedStockLoss();
			stockOverFlow = getStockOverFlow.getCheckedStockOverflow();
			stockPresent = getStockPresent.getCheckedPresent();
			for(int i = 0; i < stockLoss.size(); i++){
				if(!((stockLoss.get(i).time.compareTo(time1) >= 0) && (stockLoss.get(i).time.compareTo(time2) <= 0))){
					stockLoss.remove(i);
					i--;
				}
			}
			for(int i = 0; i < stockOverFlow.size(); i++){
				if(!((stockOverFlow.get(i).time.compareTo(time1) >= 0) && (stockOverFlow.get(i).time.compareTo(time2) <= 0))){
					stockOverFlow.remove(i);
					i--;
				}
			}
			for(int i = 0; i < stockPresent.size(); i++){
				if(!((stockPresent.get(i).time.compareTo(time1) >= 0) && (stockPresent.get(i).time.compareTo(time2) <= 0))){
					stockOverFlow.remove(i);
					i--;
				}
			}
			id = new String[stockLoss.size() + stockOverFlow.size() + stockPresent.size()][1];
			for(int i = 0; i < id.length; i++){
				if(i < stockLoss.size())
					id[i][0] = stockLoss.get(i).id;
				else if(i < (stockLoss.size() + stockOverFlow.size()))
					id[i][0] = stockOverFlow.get(i - stockLoss.size()).id;
				else
					id[i][0] = stockPresent.get(i - stockLoss.size() - stockOverFlow.size()).id;
			}
		}
		return id;
	}

	/********************************************************************
	 ********************************************************************/
	public boolean output(String[][] res) {
		return true;
	}
	/********************************************************************
	 ********************************************************************/
	public boolean red(ImportVO importVO, CourierVO salesVO) {
		GetImportBL getImportBL = new ImportBL();
		GetSalesBL getSalesBL = new CourierBL();
		if(importVO != null){
			if(importVO.id.split("-")[0].equals("JHD"))
				importVO.id = getImportBL.getiID();
			else
				importVO.id = getImportBL.getirID();
			
			getImportBL.CheckI(importVO);
			getImportBL.approvalImport(importVO.id, 1);
			getImportBL.approvalImport(importVO.id, 2);
		}
		else if(salesVO != null){
			if(salesVO.id.split("-")[0].equals("XSD"))
				salesVO.id = getSalesBL.getsID();
			else
				salesVO.id = getSalesBL.getsrID();
			
			getSalesBL.CheckS(salesVO);
			getSalesBL.approvalSales(salesVO.id, 1);
			getSalesBL.approvalSales(salesVO.id, 2);
		}
		return true;
	}

	/********************************************************************
	 ********************************************************************/
	public boolean copy(ImportVO importVO, CourierVO salesVO) {
		GetImportBL getImportBL = new ImportBL();
		GetSalesBL getSalesBL = new CourierBL();
		if(importVO != null){
			if(importVO.id.split("-")[0].equals("JHD"))
				importVO.id = getImportBL.getiID();
			else
				importVO.id = getImportBL.getirID();
			
			getImportBL.CheckI(importVO);
			getImportBL.approvalImport(importVO.id, 3);
		}
		else if(salesVO != null){
			if(salesVO.id.split("-")[0].equals("XSD"))
				salesVO.id = getSalesBL.getsID();
			else
				salesVO.id = getSalesBL.getsrID();
			
			getSalesBL.CheckS(salesVO);
			getSalesBL.approvalSales(salesVO.id, 3);
		}
		return true;
	}
	
	/********************************************************************
	 ********************************************************************/
	public boolean AddLedger(String time, String type){
		if(this.findOneLedger(time) == null){
			GetCustomerBL getCustomerBL = new CustomerBL();
			
			ArrayList<CustomerVO> customerVOs = getCustomerBL.ShowC();
			ArrayList<AccountVO> accountVOs = this.findAll("");
			
			LedgerVO ledgerVO = new LedgerVO(customerVOs, accountVOs, time, type);
			
			return accountDataService.AddLedger(ledgerVO.ToPO());
		}
		return false;
	}
	
	public ArrayList<LedgerVO> showLedger(){
		ArrayList<Object> ledger = accountDataService.showLedger();
		ArrayList<LedgerVO> res = new ArrayList<LedgerVO>();
		for(Object i : ledger){
			res.add(((LedgerPO)i).ToVO());
		}
		return res;
	}
	
	public LedgerVO findOneLedger(String time){
		ArrayList<LedgerVO> ledger = this.showLedger();
		for(LedgerVO i : ledger){
			if(i.time.equals(time))
				return i;
		}
		return null;
	}
	/********************************************************************/
	public DocumentVO findOneDocument(String id){
		ArrayList<DocumentVO> list = this.filterDocuments(null, null, null, null, 1);
		DocumentVO res = null;
		for(int i = 0; i < list.size(); i++){
			res = list.get(i);
			if(res.id.equals(id))
				break;
		}
		return res;
	}

	public CashExpensesVO findOneCashExpenses(String id) {
		ArrayList<CashExpensesVO> list = this.filterCashExpenses(null, null);
		CashExpensesVO res = null;
		for(int i = 0; i < list.size(); i++){
			res = list.get(i);
			if(res.id.equals(id))
				break;
		}
		return res;
	}
	
	public ImportVO findOneImport(String id){
		GetImportBL getImportBL = new ImportBL();
		ArrayList<ImportVO> list = new ArrayList<ImportVO>(getImportBL.getCheckedImport());
		ImportVO res = null;
		for(int i = 0; i < list.size(); i++){
			res = list.get(i);
			if(res.id.equals(id)){
				break;
			}
		}
		return res;
	}
	
	public CourierVO findOneSales(String id){
		GetSalesBL getSalesBL = new CourierBL();
		ArrayList<CourierVO> list = new ArrayList<CourierVO>(getSalesBL.getCheckedSales());
		CourierVO res = null;
		for(int i = 0; i < list.size(); i++){
			res = list.get(i);
			if(res.id.equals(id)){
				break;
			}
		}
		return res;
	}

	public LogisticsOverflowVO findOneStockOverflow(String id) {
		GetStockBL getStockOverFlow = new LogisticsOverflowBL();
		ArrayList<LogisticsOverflowVO> list = new ArrayList<LogisticsOverflowVO>(getStockOverFlow.getCheckedStockOverflow());
		LogisticsOverflowVO res = null;
		for(int i = 0; i < list.size(); i++){
			res = list.get(i);
			if(res.id.equals(id)){
				break;
			}
		}
		return res;	
	}

	public LogisticsLossVO findOneStockLoss(String id) {
		GetStockBL getStockLoss = new LogisticsLossBL();
		ArrayList<LogisticsLossVO> list = new ArrayList<LogisticsLossVO>(getStockLoss.getCheckedStockLoss());
		LogisticsLossVO res = null;
		for(int i = 0; i < list.size(); i++){
			res = list.get(i);
			if(res.id.equals(id)){
				break;
			}
		}
		return res;	
	}
	public PresentVO findOnePresent(String id){
		GetStockBL getPresent = new PresentBL();
		ArrayList<PresentVO> list = new ArrayList<PresentVO>(getPresent.getCheckedPresent());
		PresentVO res = null;
		for(int i = 0; i < list.size(); i++){
			res = list.get(i);
			if(res.id.equals(id)){
				break;
			}
		}
		return res;	
	}
}