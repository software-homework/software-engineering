package vo.stockManage;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StockOverflowVO {
	public String id;
	public int approval;
	public String name;
	public String model;
	public int overNumber;
	public String time;
	
	public StockOverflowVO(){
		
	}
	
	public StockOverflowVO(int state,String name,String model,int overnumber){
		this.approval = state;
		this.name = name;
		this.model = model;
		this.overNumber = overnumber;
		this.time = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
	}
	
}
