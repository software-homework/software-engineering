package businesslogicservice.manager;

import java.util.*;

import vo.salesman.*;

/**
 * 
 * @date 2014年12月6日
 * @time 下午3:32:41
 * @author stk
 *
 */

/*
 * 获得销售单信息
 */
public interface GetSales {
	public boolean approvalSales(String id, int approval);
	public void updateSales(SalesVO svo);
	public ArrayList<SalesVO> getUncheckedSales();
	public ArrayList<SalesVO> getCheckedSales();
}
