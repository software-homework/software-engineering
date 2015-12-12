package businesslogicservice.finance;

import java.util.ArrayList;

import vo.logistics.LogisticsVO;

public interface GetCommodityBL {
	public ArrayList<LogisticsVO> merge(String start,String end);
}
