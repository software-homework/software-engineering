package businesslogic.logistics;

import java.rmi.RemoteException;
import java.util.ArrayList;

import businesslogicservice.logistics.PresentBLService;
import data.logistics.PresentData;
import dataservice.logistics.PresentDataService;
import po.logistics.PresentPO;
import vo.logistics.CommodityVO;
import vo.logistics.PresentVO;
import vo.logistics.LogisticsLossVO;
import vo.logistics.LogisticsOverflowVO;

public class PresentBL implements PresentBLService,businesslogicservice.finance.GetStockBL,businesslogicservice.manager.GetPresent{
	private PresentDataService pds;
	CommodityBL cb;
	
	public PresentBL(){
		try {
			pds = PresentData.getInstance();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cb = new CommodityBL();
	}
	
	public boolean add(PresentVO pv){
		if(pv == null){
			return false;
		}
		
		for(CommodityVO cv :cb.getCommodity()){
			for(String str : pv.presentList){
				int i1 = str.indexOf(";");
				String name = str.substring(0, i1);
				String str2 = str.substring(i1 + 1);
				int i2 = str2.indexOf(";");
				String model = str2.substring(0, i2);
				int number = Integer.parseInt(str2.substring(i2 + 1));
				
				if(name.equals(cv.commodityname) && model.equals(cv.model)){
					if(cv.number < number){
						return false;
					}
				}
			}
		}
		
		if(pds.add(presentVoToPo(pv))){
			return true;
		}
		return false;
	}
	
	public PresentPO presentVoToPo(PresentVO pv){
		if(pv == null){
			return null;
		}
		PresentPO pp = new PresentPO();
		pp.setId(pv.id);
		pp.setApproval(pv.approval);
		pp.setPresentList(pv.presentList);
		pp.setTime(pv.time);
		return pp;
	}
	
	public PresentVO presentPoToVo(PresentPO pp){
		if(pp == null){
			return null;
		}
		PresentVO pv = new PresentVO();
		pv.id = pp.getId();
		pv.approval = pp.getApproval();
		pv.presentList = pp.getPresentList();
		pv.time = pp.getTime();
		return pv;
	}
	
	public boolean approvalPresent(String id,int approval){
		ArrayList<PresentPO> presentList = new ArrayList<PresentPO>();
		presentList = pds.getPresent();
		PresentVO pv = new PresentVO();
		for(PresentPO pp : presentList){
			if(pp.getId().equals(id)){
				pp.setApproval(approval);
				pds.update(pp);
				pv = presentPoToVo(pp);
				break;
			}
		}
		
		if(pv.approval == 1){
			for(String str : pv.presentList){
				int i = str.indexOf(";");
				String name = str.substring(0, i);
				String str2 = str.substring(i + 1);
				int j = str2.indexOf(";");
				String model = str2.substring(0,j);
				int number = Integer.parseInt(str2.substring(j + 1));
				
			    for(CommodityVO cv : cb.getCommodity()){
			    	if(cv.commodityname.equals(name) && cv.model.equals(model)){
			    		CommodityVO newCv = (CommodityVO)cv.clone();
						newCv.number = cv.number - number;
						if(cb.updateCommodity(cv, newCv, cb.getFather(cv))){
							return true;
						}
						else{
							return false;
						}
			    	}
			    }
			}
		}
		return true;
	} 
	
	public ArrayList<PresentVO> getUncheckedPresent(){
		ArrayList<PresentVO> presentList = new ArrayList<PresentVO>();
		for(PresentPO pp : pds.getPresent()){
			if(pp.getApproval() == 3){
				presentList.add(presentPoToVo(pp));
			}
		}
		return presentList;
	}
	
	public ArrayList<PresentVO> getCheckedPresent(){
		ArrayList<PresentVO> presentList = new ArrayList<PresentVO>();
		for(PresentPO pp : pds.getPresent()){
			if(pp.getApproval() == 1){
				presentList.add(presentPoToVo(pp));
			}
		}
		return presentList;
	}
	
	public ArrayList<PresentVO> getPresent(){
		ArrayList<PresentVO> presentList = new ArrayList<PresentVO>();
		for(PresentPO pp : pds.getPresent()){
			presentList.add(presentPoToVo(pp));
		}
		return presentList;
	}
	
	public void updatePresent(PresentVO pv){
		pds.update(presentVoToPo(pv));	
	}

	@Override
	public ArrayList<LogisticsLossVO> getCheckedStockLoss() {
		return null;
	}

	@Override
	public ArrayList<LogisticsOverflowVO> getCheckedStockOverflow() {
		return null;
	}
}
