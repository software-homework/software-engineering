package po.stockManage;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MessagePO implements Serializable{
	String type;
	String name;
	String model;
	int number;
	String customer;
	boolean isRead;
	String time;
	
	public MessagePO(){
		
	}
	
	public String getCustomer(){
		return customer;
	}
	
	public void setCustomer(String customer){
		this.customer = customer;
	}
	
	public String getType(){
		return type;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public int getNumber(){
		return number;
	}
	
	public void setNumber(int number){
		this.number = number;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getModel(){
		return model;
	}
	
	public void setModel(String model){
		this.model = model;
	}
	
	public boolean getIsRead(){
		return isRead;
	}
	
	public void setIsRead(boolean isRead){
		this.isRead = isRead;
	}
	
	public String getTime(){
		return time;
	}
	
	public void setTime(String time){
		this.time = time;
	}
}
