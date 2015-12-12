package vo.logistics;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PresentVO {
	public String id;
	public ArrayList<String> presentList;
	public int approval;
	public String time;
	
	public PresentVO(){
		
	}
	
	public PresentVO(int approval,ArrayList<String> list){
		this.approval = approval;
		this.presentList = list;
		this.time = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
	}
}
