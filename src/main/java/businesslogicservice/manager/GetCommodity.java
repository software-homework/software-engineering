package businesslogicservice.manager;

import java.util.ArrayList;

import vo.logistics.CommodityVO;

/**
 * 
 * @date 2014年11月25日
 * @author stk
 *
 */

/*
 * 获得商品信息接口
 */
public interface GetCommodity {
	public ArrayList<CommodityVO> getCommodity();
}
