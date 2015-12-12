package po.logistics;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LogisticsOverflowPO implements Serializable{
	String id;
	int approval;
	String name;
	String model;
	int overNumber;
	String time;
	
	public LogisticsOverflowPO(){
		
	}
	
	public LogisticsOverflowPO(String id,int state,String name,String model,int overnumber,String time){
		this.id = id;
		this.approval = state;
		this.name = name;
		this.model = model;
		this.overNumber = overnumber;
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
	
	public int getOverNumber(){
		return overNumber;
	}
	
	public void setOverNumber(int overNumber){
		this.overNumber = overNumber;
	}
	
	public String getTime(){
		return time;
	}
	
	public void setTime(String time){
		this.time = time;
	}
}
