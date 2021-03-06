package vo.logistics;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MessageVO {
	public String type;
	public String name;
	public String model;
	public String customer;
	public int number;
	public boolean isRead;
	public String time;
	
	public MessageVO(){
		
	}
	
	public MessageVO(String type,String name,String model,int number,String customer,boolean isRead){
		this.type = type;
		this.name = name;
		this.model = model;
		this.number = number;
		this.customer = customer;
		this.isRead = isRead;
		this.time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
	}
}
