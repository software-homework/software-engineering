package businesslogicservice.logistics;

import java.util.ArrayList;

import vo.logistics.LogisticsSnapVO;

public interface LogisticsSnapBLService {
	public boolean add(LogisticsSnapVO ssv);
	
	public ArrayList<LogisticsSnapVO> showStocksnap();
}
