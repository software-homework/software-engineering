package businesslogicservice;

import java.util.ArrayList;

import vo.LogVO;

public interface LogBLService {
	public void addLog(LogVO logvo); //添加日志
	public ArrayList<LogVO> showLog(); //显示日志

}
