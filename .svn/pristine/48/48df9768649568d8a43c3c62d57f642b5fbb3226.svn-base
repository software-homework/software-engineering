package vo.stockManage;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StockLossVO {
	public String id;
	public int approval;
	public String name;
	public String model;
	public int lossNumber;
	public String time;
	
	public StockLossVO(){
		
	}
	
	public StockLossVO(int state,String name,String model,int lossNumber){
		this.approval = state;
		this.name = name;
		this.model = model;
		this.lossNumber = lossNumber;
		this.time = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
	}
}
