package po.stockManage;

import java.io.Serializable;

@SuppressWarnings("serial")
public class StockLossPO implements Serializable{
	String id;
	int approval;
	String name;
	String model;
	int lossNumber;
	String time;
	
	public StockLossPO(){
		
	}
	
	public StockLossPO(String id,int state,String name,String model,int lossNumber,String time){
		this.id = id;
		this.approval = state;
		this.name = name;
		this.model = model;
		this.lossNumber = lossNumber;
		this.time = time;
	}
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public int getApproval(){
		return approval;
	}
	
	public void setApproval(int state){
		this.approval = state;
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
	
	public int getlossNumber(){
		return lossNumber;
	}
	
	public void setlossNumber(int lossNumber){
		this.lossNumber = lossNumber;
	}
	
	public String getTime(){
		return time;
	}
	
	public void setTime(String time){
		this.time = time;
	}
}
