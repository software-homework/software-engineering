package po.finance;

import java.io.Serializable;
import java.util.ArrayList;

import vo.finance.DocumentVO;

//收、付款单
@SuppressWarnings("serial")
public class DocumentPO implements Serializable
{	
	private String type;			//收款单、付款单
	private String id;				//编号
	private String customerName;    //客户名
	private String customerType;    //客户类型(供应商)
	private String operator;	    //操作员
	private ArrayList<String> transferList; 	//转账列表
	private double total;			//总额
	private String time;
	private long idNum;				//编号后五位
	private int approve = 3;
	//================================================================================================
	
	enum ResultMessage
	{
	}
	
	public DocumentPO(String type, String id, String customerName, String customerType, String operator,
			ArrayList<String> transferList, double total, String time, long idNum)
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
	}
	
	/*******************************************************************
	 *get set 
	 *******************************************************************/
	public String getType()
	{
		return type;
	}
	
	public void setType(String type) 
	{
		this.type = type;
	}
	
	public String getId() 
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getCustomerName() 
	{
		return customerName;
	}
	
	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}
	
	public String getCustomerType() 
	{
		return customerType;
	}
	
	public void setCustomerType(String customerType) 
	{
		this.customerType = customerType;
	}
	
	public String getOperator() 
	{
		return operator;
	}
	
	public void setOperator(String operator) 
	{
		this.operator = operator;
	}
	
	public ArrayList<String> getTransferList() 
	{
		return transferList;
	}
	
	public void setTransferList(ArrayList<String> transferList) 
	{
		this.transferList = transferList;
	}
	
	public double getTotal() 
	{
		return total;
	}
	
	public void setTotal(double total)
	{
		this.total = total;
	}
	
	public String getTime() 
	{
		return time;
	}
	
	public void setTime(String time)
	{
		this.time = time;
	}
	
	public long getIdNum() 
	{
		return idNum;
	}
	
	public void setIdNum(long idNum) 
	{
		this.idNum = idNum;
	}
	
	public int getApprove() 
	{
		return approve;
	}
	
	public void setApprove(int approve)
	{
		this.approve = approve;
	}

	/********************************************************************
	 * ToVO
	 ********************************************************************/
	public DocumentVO ToVO()
	{
		return new DocumentVO(type, id, customerName, customerType, operator, transferList, total, time, idNum, approve);
	}
}
