package po.courier;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CustomerPO implements Serializable{
	private int ID;
	private String type;
	private String level;
	private String name;
	private String phone;
	private String email;
	private String address;
	private int postcode;
	private double limit;
	private double proceed;
	private double pay;
	private String user;
	
	public CustomerPO(int ID,String type,String level,String name,String phone,String email,String address,int postcode,double limit,double proceed,double pay,String user){
		setID(ID);
		setType(type);
		setLevel(level);
		setName(name);
		setPhone(phone);
		setEmail(email);
		setAddress(address);
		setPostcode(postcode);
		setLimit(limit);
		setProceed(proceed);
		setPay(pay);
		setUser(user);
		
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPostcode() {
		return postcode;
	}

	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}

	public double getLimit() {
		return limit;
	}

	public void setLimit(double limit) {
		this.limit = limit;
	}

	public double getProceed() {
		return proceed;
	}

	public void setProceed(double proceed) {
		this.proceed = proceed;
	}

	public double getPay() {
		return pay;
	}

	public void setPay(double pay) {
		this.pay = pay;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
