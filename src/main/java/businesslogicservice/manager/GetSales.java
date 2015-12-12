package businesslogicservice.manager;

import java.util.*;

import vo.courier.*;

/**
 * 
 * @date 2014年12月6日
 * @time 下午3:32:41
 * @author stk
 *
 */

/*
 * 获得送货信息
 */
public interface GetSales {
	public boolean approvalSales(String id, int approval);
	public void updateSales(CourierVO svo);
	public ArrayList<CourierVO> getUncheckedSales();
	public ArrayList<CourierVO> getCheckedSales();
}
