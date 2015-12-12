package po.logistics;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class PresentPO implements Serializable{
	String id;
	ArrayList<String> presentList;
	int approval;
	String time;
	
	public PresentPO(){
		
	}
	
	public PresentPO(int approval,String id,ArrayList<String> list,String time){
		this.id = id;
		this.approval = approval;
		this.presentList = list;
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
	
	public ArrayList<String> getPresentList(){
		return presentList;
	}
	
	public void setPresentList(ArrayList<String> list){
		this.presentList = list;
	}
	
	public String getTime(){
		return time;
	}
	
	public void setTime(String time){
		this.time = time;
	}
}

