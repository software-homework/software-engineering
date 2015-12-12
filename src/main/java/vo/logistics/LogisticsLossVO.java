package vo.logistics;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogisticsLossVO {
	public String id;
	public int approval;
	public String name;
	public String model;
	public int lossNumber;
	public String time;
	
	public LogisticsLossVO(){
		
	}
	
	public LogisticsLossVO(int state,String name,String model,int lossNumber){
		this.approval = state;
		this.name = name;
		this.model = model;
		this.lossNumber = lossNumber;
		this.time = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
	}
}
