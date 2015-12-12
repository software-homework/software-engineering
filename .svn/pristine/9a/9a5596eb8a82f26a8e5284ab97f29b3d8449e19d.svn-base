package po.finance;

import java.io.Serializable;
import java.util.ArrayList;

import vo.finance.CashExpensesVO;

@SuppressWarnings("serial")
public class CashExpensesPO implements Serializable
{
	private String id;			//编号
	private String operator; 	//操作员
	private String account; 	//银行账户
	private ArrayList<String> list;
	private double total;		//总额
	private String time;
	private long idNum;			//编号后五位	
	private int approve = 3;
	public CashExpensesPO(String id, String operator, String account, 
			ArrayList<String> list, double total, String time, long idNum){
		this.id = id;
		this.operator = operator;
		this.account = account;
		this.list = list;
		this.total = total;
		this.time = time;
		this.idNum = idNum;
	}

	/*******************************************************************
	 * get set
	 *******************************************************************/
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}


	public String getAccount() {
		return account;
	}


	public void setAccount(String account) {
		this.account = account;
	}


	public ArrayList<String> getList() {
		return list;
	}


	public void setList(ArrayList<String> list) {
		this.list = list;
	}


	public double getTotal() {
		return total;
	}


	public void setTotal(double total) {
		this.total = total;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public long getIdNum() {
		return idNum;
	}


	public void setIdNum(long idNum) {
		this.idNum = idNum;
	}
	
	public int getApprove() {
		return approve;
	}

	public void setApprove(int approve) {
		this.approve = approve;
	}

	/****************************************************************
	 * ToVO
	 ****************************************************************/

	public CashExpensesVO ToVO(){
		return new CashExpensesVO(id, operator, account, list, total, time, idNum, approve);
	}
}
