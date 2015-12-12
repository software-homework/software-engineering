package businesslogicservice.manager;

import java.util.ArrayList;

import vo.logistics.LogisticsLossVO;

/**
 * 
 * @date 2014年12月6日
 * @time 下午4:40:33
 * @author stk
 *
 */

/*
 * 获得报损单信息接口
 */
public interface GetStockLoss {
	public boolean approvalStockLoss(String id, int approval);
	public ArrayList<LogisticsLossVO> getUncheckedStockLoss();
	public ArrayList<LogisticsLossVO> getCheckedStockLoss();
}
