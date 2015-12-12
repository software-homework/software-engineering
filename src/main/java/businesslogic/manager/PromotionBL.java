package businesslogic.manager;

import java.rmi.RemoteException;
import java.util.ArrayList;

import businesslogicservice.manager.PromotionBLService;
import data.manager.PromotionData;
import dataservice.manager.PromotionDataService;
import po.manager.BargainPO;
import po.manager.CustPromPO;
import po.manager.TotalPromPO;
import vo.manager.BargainVO;
import vo.manager.CustPromVO;
import vo.manager.TotalPromVO;

/**
 * 
 * @date 2014年11月30日
 * @author stk
 *
 */

/*
 * 制定促销策略逻辑
 */
public class PromotionBL implements PromotionBLService{
	PromotionDataService promotionData=null;
	ArrayList<CustPromPO> custPromList;
	ArrayList<BargainPO> bargainList;
	ArrayList<TotalPromPO> totalPromList;
	//---------------------------------------------------------
	public PromotionBL() {
		try {
			promotionData = PromotionData.getInstance();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	//---------------------------------------------------------
	public boolean addCustProm(CustPromVO vo) {
		return promotionData.addCustProm(this.custPromVtoP(vo));
	}
	
	public boolean delCustProm(CustPromVO vo) {
		return promotionData.delCustProm(this.custPromVtoP(vo));
	}
	
	public ArrayList<CustPromVO> shoCustProm() {
		ArrayList<CustPromVO> voList = new ArrayList<CustPromVO>();
		for(CustPromPO i : promotionData.shoCustProm()) {
			voList.add(new CustPromVO(i.getStartTime(), i.getEndTime(), i.getType(), i.getGoodsName(), i.getDiscount(), i.getVoucher()));
		}
		return voList;
	}
	//--------------------------------------------------------
	public boolean addBargain(BargainVO vo) {
		return promotionData.addBargain(this.bargainVtoP(vo));
	}
	
	public boolean delBargain(BargainVO vo) {
		return promotionData.delBargain(this.bargainVtoP(vo));
	}
	
	public ArrayList<BargainVO> shoBargain() {
		ArrayList<BargainVO> voList = new ArrayList<BargainVO>();
		for(BargainPO i : promotionData.shoBargain()) {
			voList.add(new BargainVO(i.getStartTime(), i.getEndTime(), i.getGoodsName(), i.getPromotion()));
		}
		return voList;
	}
	//-------------------------------------------------------
	public boolean addTotalProm(TotalPromVO vo) {
		return promotionData.addTotalProm(this.totalPromVtoP(vo));
	}
	
	public boolean delTotalProm(TotalPromVO vo) {
		return promotionData.delTotalProm(this.totalPromVtoP(vo));
	}
	
	public ArrayList<TotalPromVO> shoTotalProm() {
		ArrayList<TotalPromVO> voList = new ArrayList<TotalPromVO>();;
		for(TotalPromPO i : promotionData.shoTotalProm()) {
			voList.add(new TotalPromVO(i.getStartTime(), i.getEndTime(), i.getTotal(), i.getGoodsName(), i.getVoucher()));
		}
		return voList;
	}
	//-----------------------------------------------------------
	//将CustPromVO转成CustPromPO
	private CustPromPO custPromVtoP(CustPromVO vo) {
		return new CustPromPO(vo.startTime, vo.endTime, vo.type, vo.goodsName, vo.discount, vo.voucher);
	}
	
	//将BargainVO转成BargainPO
	private BargainPO bargainVtoP(BargainVO vo) {
		return new BargainPO(vo.startTime, vo.endTime, vo.goodsName, vo.promotion);
	}
	
	//将TotalPromVO转成TotalPromPO
	private TotalPromPO totalPromVtoP(TotalPromVO vo) {
		return new TotalPromPO(vo.startTime, vo.endTime, vo.total, vo.goodsName, vo.voucher);
	}
}
