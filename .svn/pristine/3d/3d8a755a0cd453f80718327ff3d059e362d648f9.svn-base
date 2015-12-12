package vo.finance;

import java.util.ArrayList;

//单据类
public class DocumentVO 
{
	public String type;			//收款单、付款单
	public String id;				//编号
	public String customerName;    //客户名
	public String customerType;    //客户类型(供应商)
	public String operator;	    //操作员
	public ArrayList<String> transferList; 	//转账列表
	public double total;			//总额
	public String time;
	public long idNum;
	public int approve;
	
	//================================================================================================
	
	enum ResultMessage
	{
	}
	
	public DocumentVO(String type, String id, String customerName, String customerType, String operator,
			ArrayList<String> transferList, double total, String time, long idNum, int approve)
	{
		this.type = type;
		this.id = id;		
		this.customerName = customerName;
		this.customerType = customerType;
		this.operator = operator;
		this.transferList = transferList;				
		this.total = total;	
		this.time = time;
		this.idNum = idNum;
		this.approve = approve;
	}
}
