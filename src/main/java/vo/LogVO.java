package vo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogVO {
	public String date; //日期
	public String time; //时间
	public String user; //操作员
	public String action; //操作
	
	public LogVO(String user,String action){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");
		this.date = df.format(new Date());
		this.time = tf.format(new Date());
		this.user = user;
		this.action = action;
	}
	public LogVO(){
		
	}

}
