package businesslogic.logistics;

import java.rmi.RemoteException;
import java.util.ArrayList;

import businesslogicservice.logistics.LogisticsLossBLService;
import data.logistics.LogisticsLossData;
import dataservice.logistics.LogisticsLossDataService;
import po.logistics.LogisticsLossPO;
import vo.logistics.CommodityVO;
import vo.logistics.MessageVO;
import vo.logistics.PresentVO;
import vo.logistics.LogisticsLossVO;
import vo.logistics.LogisticsOverflowVO;

public class LogisticsLossBL implements LogisticsLossBLService,businesslogicservice.manager.GetStockLoss,businesslogicservice.finance.GetStockBL{
	private LogisticsLossDataService slds;
	MessageBL mb;
	CommodityBL cb;
    
    public LogisticsLossBL(){
    	try {
			slds = LogisticsLossData.getInstance();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	cb = new CommodityBL();
    }
    
	@Override
	public boolean add(LogisticsLossVO slv) {
		// TODO Auto-generated method stub
		if(slv == null){
			return false;
		}
		
		for(CommodityVO cv : cb.getCommodity()){
			if(cv.commodityname.equals(slv.name) && cv.model.equals(slv.model)){
				if(cv.number - slv.lossNumber < 0){
					return false;
				}
			}
		}
		
		if(slds.add(stockLossVoToPo(slv))){
			return true;
		}
		return false;
	}

	@Override
	public boolean setApproval(LogisticsLossVO slv, int approval) {
		// TODO Auto-generated method stub
		if(slv == null){
			return false;
		}
		LogisticsLossPO slp = getStockLossPO(slv.id);
		if(slp == null){
			return false;
		}
		if(slds.setApproval(slp, approval)){
			return true;
		}
		return false;
	}

	public LogisticsLossVO getStockLoss(String id) {
		// TODO Auto-generated method stub
		return stockLossPoToVo(getStockLossPO(id));
	}
	
	public LogisticsLossPO getStockLossPO(String id){
		if(id == null){
			return null;
		}
		for (LogisticsLossPO slp : slds.getStockLoss()){
			if(slp.getId().equals(id)){
				return slp;
			}
		}
		return null;
	}
	
	public LogisticsLossPO stockLossVoToPo(LogisticsLossVO slv){
		if(slv == null){
			return null;
		}
		LogisticsLossPO slp = new LogisticsLossPO();
		slp.setId(slv.id);
		slp.setName(slv.name);
		slp.setModel(slv.model);
		slp.setlossNumber(slv.lossNumber);
		slp.setApproval(slv.approval);
		slp.setTime(slv.time);
		return slp;
	}
	
	public LogisticsLossVO stockLossPoToVo(LogisticsLossPO slp){
		if(slp == null){
			return null;
		}
		LogisticsLossVO slv = new LogisticsLossVO();
		slv.id = slp.getId();
		slv.name = slp.getName();
		slv.model = slp.getModel();
		slv.lossNumber = slp.getlossNumber();
		slv.approval = slp.getApproval();
		slv.time = slp.getTime();
		return slv;
	}
	
	public boolean approvalStockLoss(String id,int approval){
		ArrayList<LogisticsLossPO> stocklosslist = new ArrayList<LogisticsLossPO>();
		stocklosslist = slds.getStockLoss();
		LogisticsLossVO sofv = new LogisticsLossVO();
		for(LogisticsLossPO slp : stocklosslist){
			if(slp.getId().equals(id)){
				slp.setApproval(approval);
				slds.update(slp);
				sofv = stockLossPoToVo(slp);
				break;
			}
		}
		
		if(sofv.approval == 1){
			for(CommodityVO cv : cb.getCommodity()){
				if(sofv.name.equals(cv.commodityname) && sofv.model.equals(cv.model)){
					CommodityVO newCv = (CommodityVO)cv.clone();
					newCv.number = cv.number - sofv.lossNumber;
					if(cb.updateCommodity(cv, newCv, cb.getFather(cv))){
						if(newCv.number < newCv.dangerNumber){
							mb.add(new MessageVO("库存报警",cv.commodityname,cv.model,newCv.number,"空",false));
						}
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
	
	public ArrayList<LogisticsLossVO> getCheckedStockLoss(){
		ArrayList<LogisticsLossVO> stocklossList = new ArrayList<LogisticsLossVO>();
		for(LogisticsLossPO slp : slds.getStockLoss()){
			if(slp.getApproval() == 1){
				stocklossList.add(stockLossPoToVo(slp));
			}
		}
		return stocklossList;
	}
	
	public ArrayList<LogisticsLossVO> getUncheckedStockLoss(){
		ArrayList<LogisticsLossVO> stocklossList = new ArrayList<LogisticsLossVO>();
		for(LogisticsLossPO slp : slds.getStockLoss()){
			if(slp.getApproval() == 3){
				stocklossList.add(stockLossPoToVo(slp));
			}
		}
		return stocklossList;
	}
	
	public ArrayList<LogisticsLossVO> getStockLoss(){
		ArrayList<LogisticsLossVO> stocklossList = new ArrayList<LogisticsLossVO>();
		for(LogisticsLossPO slp : slds.getStockLoss()){
			stocklossList.add(stockLossPoToVo(slp));
		}
		return stocklossList;
	}

	@Override
	public ArrayList<LogisticsOverflowVO> getCheckedStockOverflow() {
		return null;
	}

	@Override
	public ArrayList<PresentVO> getCheckedPresent() {
		return null;
	}
}
