package businesslogic.logistics;

import java.util.ArrayList;

import businesslogicservice.logistics.LogisticsOverflowBLService;
import data.logistics.LogisticsOverflowData;
import dataservice.logistics.LogisticsOverflowDataService;
import po.logistics.LogisticsOverflowPO;
import vo.logistics.CommodityVO;
import vo.logistics.PresentVO;
import vo.logistics.LogisticsLossVO;
import vo.logistics.LogisticsOverflowVO;

public class LogisticsOverflowBL implements LogisticsOverflowBLService,businesslogicservice.manager.GetStockOverflow,businesslogicservice.finance.GetStockBL{
    private LogisticsOverflowDataService sofds;
    CommodityBL cb;
    
    public LogisticsOverflowBL(){
    	try {
			sofds = LogisticsOverflowData.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	cb = new CommodityBL();
    }
    
	@Override
	public boolean add(LogisticsOverflowVO sofv) {
		// TODO Auto-generated method stub
		if(sofv == null){
			return false;
		}
		if(sofds.add(stockOverflowVoToPo(sofv))){
//			updateId(stockOverflowVoToPo(sofv));
			return true;
		}
		return false;
	}

	@Override
	public boolean setApproval(LogisticsOverflowVO sofv, int approval) {
		// TODO Auto-generated method stub
		if(sofv == null){
			return false;
		}
		LogisticsOverflowPO sofp = getStockOverflowPO(sofv.id);
		if(sofp == null){
			return false;
		}
		if(sofds.setApproval(sofp, approval)){
			return true;
		}
		return false;
	}
	
	public LogisticsOverflowVO getStockOverflow(String id){
		return stockOverflowPoToVo(getStockOverflowPO(id));
	}
	
	public LogisticsOverflowPO getStockOverflowPO(String id){
		if(id == null){
			return null;
		}
		for (LogisticsOverflowPO sofp : sofds.getStockOverflow()){
			if(sofp.getId().equals(id)){
				return sofp;
			}
		}
		return null;
	}
	
	public LogisticsOverflowPO stockOverflowVoToPo(LogisticsOverflowVO sofv){
		if(sofv == null){
			return null;
		}
		LogisticsOverflowPO sofp = new LogisticsOverflowPO();
		sofp.setId(sofv.id);
		sofp.setName(sofv.name);
		sofp.setModel(sofv.model);
		sofp.setOverNumber(sofv.overNumber);
		sofp.setApproval(sofv.approval);
		sofp.setTime(sofv.time);
		return sofp;
	}
	
	public LogisticsOverflowVO stockOverflowPoToVo(LogisticsOverflowPO sofp){
		if(sofp == null){
			return null;
		}
		LogisticsOverflowVO sofv = new LogisticsOverflowVO();
		sofv.id = sofp.getId();
		sofv.name = sofp.getName();
		sofv.model = sofp.getModel();
		sofv.overNumber = sofp.getOverNumber();
		sofv.approval = sofp.getApproval();
		sofv.time = sofp.getTime();
		return sofv;
	}
	
	public boolean approvalStockOverflow(String id,int approval){
		ArrayList<LogisticsOverflowPO> stockOverflowlist = new ArrayList<LogisticsOverflowPO>();
		stockOverflowlist = sofds.getStockOverflow();
		LogisticsOverflowVO sofv = new LogisticsOverflowVO();
		for(LogisticsOverflowPO sofp : stockOverflowlist){
			if(sofp.getId().equals(id)){
				sofp.setApproval(approval);
				sofds.update(sofp);
				sofv = stockOverflowPoToVo(sofp);
				break;
			}
		}
		
		if(sofv.approval == 1){
			for(CommodityVO cv : cb.getCommodity()){
				if(sofv.name.equals(cv.commodityname) && sofv.model.equals(cv.model)){
					CommodityVO newCv = (CommodityVO)cv.clone();
					newCv.number = cv.number + sofv.overNumber;
					if(cb.updateCommodity(cv, newCv, cb.getFather(cv))){
						return true;
					}
					else{
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public ArrayList<LogisticsOverflowVO> getCheckedStockOverflow(){
		ArrayList<LogisticsOverflowVO> stockoverflowList = new ArrayList<LogisticsOverflowVO>();
		for(LogisticsOverflowPO sofp : sofds.getStockOverflow()){
			if(sofp.getApproval() == 1){
				stockoverflowList.add(stockOverflowPoToVo(sofp));
			}
		}
		return stockoverflowList;
	}
	
	public ArrayList<LogisticsOverflowVO> getUncheckedStockOverflow(){
		ArrayList<LogisticsOverflowVO> stockoverflowList = new ArrayList<LogisticsOverflowVO>();
		for(LogisticsOverflowPO sofp : sofds.getStockOverflow()){
			if(sofp.getApproval() == 3){
				stockoverflowList.add(stockOverflowPoToVo(sofp));
			}
		}
		return stockoverflowList;
	}
	
	public ArrayList<LogisticsOverflowVO> getStockOverflow(){
		ArrayList<LogisticsOverflowVO> list = new ArrayList<LogisticsOverflowVO>();
		for(LogisticsOverflowPO sofp : sofds.getStockOverflow()){
			list.add(stockOverflowPoToVo(sofp));
		}
		return list;
	}

	@Override
	public ArrayList<LogisticsLossVO> getCheckedStockLoss() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<PresentVO> getCheckedPresent() {
		// TODO Auto-generated method stub
		return null;
	}
}
