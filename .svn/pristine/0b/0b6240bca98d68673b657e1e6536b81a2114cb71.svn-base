package po.salesman;

import java.io.Serializable;
import java.util.ArrayList;

import po.stockManage.CommodityPO;

@SuppressWarnings("serial")
public class SalesPO implements Serializable{
	private String ID;
	private String customer;
	private int repertory;
	private String user;
	private double pretotal;
	private double discount;
	private double Voucher;
	private double posttotal;
	private String remakes;
	private ArrayList<CommodityPO> SalesList;
	private int approval;
	
	public SalesPO(String ID,String customer,int repertory,String user,ArrayList<CommodityPO> SalesList,double pretotal,double discount,double voucher,double posttotal,String remakes,int approval){
		setID(ID);
		setCustomer(customer);
		setRepertory(repertory);
		setUser(user);
		setSalesList(SalesList);
		setPretotal(pretotal);
		setDiscount(discount);
		setVoucher(voucher);
		setPosttotal(posttotal);
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

	public String getRemakes() {
		return remakes;
	}

	public void setRemakes(String remakes) {
		this.remakes = remakes;
	}

	public double getPretotal() {
		return pretotal;
	}

	public void setPretotal(double pretotal) {
		this.pretotal = pretotal;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getVoucher() {
		return Voucher;
	}

	public void setVoucher(double voucher) {
		Voucher = voucher;
	}

	public double getPosttotal() {
		return posttotal;
	}

	public void setPosttotal(double posttotal) {
		this.posttotal = posttotal;
	}

	public ArrayList<CommodityPO> getSalesList() {
		return SalesList;
	}

	public void setSalesList(ArrayList<CommodityPO> salesList) {
		SalesList = salesList;
	}

	public int getApproval() {
		return approval;
	}

	public void setApproval(int approval) {
		this.approval = approval;
	}


}
