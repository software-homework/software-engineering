package po.salesman;

import java.io.Serializable;
import java.util.ArrayList;

import po.stockManage.CommodityPO;

@SuppressWarnings("serial")
public class ImportPO implements Serializable{
	private String ID;
	private String customer;
	private int repertory;
	private String user;
	private double total;
	private String remakes;
	private ArrayList<CommodityPO> importList;
	private int approval;
	
	public ImportPO(String ID,String customer,int repertory,String user,ArrayList<CommodityPO> importList,double total,String remakes,int approval){
		setID(ID);
		setCustomer(customer);
		setRepertory(repertory);
		setUser(user);
		setImportList(importList);
		setTotal(total);
		setRemakes(remakes);
		setApproval(approval);
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public int getRepertory() {
		return repertory;
	}

	public void setRepertory(int repertory) {
		this.repertory = repertory;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getRemakes() {
		return remakes;
	}

	public void setRemakes(String remakes) {
		this.remakes = remakes;
	}

	public ArrayList<CommodityPO> getImportList() {
		return importList;
	}

	public void setImportList(ArrayList<CommodityPO> importList) {
		this.importList = importList;
	}

	public int getApproval() {
		return approval;
	}

	public void setApproval(int approval) {
		this.approval = approval;
	}


}
