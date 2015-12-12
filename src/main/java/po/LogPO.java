package po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LogPO implements Serializable{
	private String date;   //日期
	private String time;   //时间
	private String user;   //操作员
	private String action;   //操作
	
	public LogPO(String date,String time,String user,String action){
		setDate(date);
		setTime(time);
		setUser(user);
		setAction(action);
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
