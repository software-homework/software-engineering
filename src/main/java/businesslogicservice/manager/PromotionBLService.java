package businesslogicservice.manager;

import java.util.ArrayList;

import vo.manager.*;

/**
 * 
 * @date 2014年12月1日
 * @author stk
 *
 */

/*
 * 制定促销策略逻辑接口
 */
public interface PromotionBLService {
	public boolean addCustProm(CustPromVO vo);
	public boolean delCustProm(CustPromVO vo);
	public ArrayList<CustPromVO> shoCustProm();
	//-------------------------------------------
	public boolean addBargain(BargainVO vo);
	public boolean delBargain(BargainVO vo);
	public ArrayList<BargainVO> shoBargain();
	//-----------------------------------------------
	public boolean addTotalProm(TotalPromVO vo);
	public boolean delTotalProm(TotalPromVO vo);
	public ArrayList<TotalPromVO> shoTotalProm();
}
