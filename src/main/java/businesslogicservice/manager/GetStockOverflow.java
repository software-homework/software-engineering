package businesslogicservice.manager;

import java.util.ArrayList;

import vo.stockManage.StockOverflowVO;

/**
 * 
 * @date 2014年12月6日
 * @time 下午4:47:43
 * @author stk
 *
 */

/*
 * 获得报溢单信息接口
 */
public interface GetStockOverflow {
	public boolean approvalStockOverflow(String id, int approval);
	public ArrayList<StockOverflowVO> getUncheckedStockOverflow();
	public ArrayList<StockOverflowVO> getCheckedStockOverflow();
}
