package businesslogic.stockManage;

import java.rmi.RemoteException;
import java.util.ArrayList;

import businesslogicservice.stockManage.StockLossBLService;
import data.stockManage.StockLossData;
import dataservice.stockManage.StockLossDataService;
import po.stockManage.StockLossPO;
import vo.stockManage.CommodityVO;
import vo.stockManage.MessageVO;
import vo.stockManage.PresentVO;
import vo.stockManage.StockLossVO;
import vo.stockManage.StockOverflowVO;

public class StockLossBL implements StockLossBLService,businesslogicservice.manager.GetStockLoss,businesslogicservice.finance.GetStockBL{
	private StockLossDataService slds;
	MessageBL mb;
	CommodityBL cb;
    
    public StockLossBL(){
    	try {
			slds = StockLossData.getInstance();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	cb = new CommodityBL();
    }
    
	@Override
	public boolean add(StockLossVO slv) {
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
	public boolean setApproval(StockLossVO slv, int approval) {
		// TODO Auto-generated method stub
		if(slv == null){
			return false;
		}
		StockLossPO slp = getStockLossPO(slv.id);
		if(slp == null){
			return false;
		}
		if(slds.setApproval(slp, approval)){
			return true;
		}
		return false;
	}

	public StockLossVO getStockLoss(String id) {
		// TODO Auto-generated method stub
		return stockLossPoToVo(getStockLossPO(id));
	}
	
	public StockLossPO getStockLossPO(String id){
		if(id == null){
			return null;
		}
		for (StockLossPO slp : slds.getStockLoss()){
			if(slp.getId().equals(id)){
				return slp;
			}
		}
		return null;
	}
	
	public StockLossPO stockLossVoToPo(StockLossVO slv){
		if(slv == null){
			return null;
		}
		StockLossPO slp = new StockLossPO();
		slp.setId(slv.id);
		slp.setName(slv.name);
		slp.setModel(slv.model);
		slp.setlossNumber(slv.lossNumber);
		slp.setApproval(slv.approval);
		slp.setTime(slv.time);
		return slp;
	}
	
	public StockLossVO stockLossPoToVo(StockLossPO slp){
		if(slp == null){
			return null;
		}
		StockLossVO slv = new StockLossVO();
		slv.id = slp.getId();
		slv.name = slp.getName();
		slv.model = slp.getModel();
		slv.lossNumber = slp.getlossNumber();
		slv.approval = slp.getApproval();
		slv.time = slp.getTime();
		return slv;
	}
	
	public boolean approvalStockLoss(String id,int approval){
		ArrayList<StockLossPO> stocklosslist = new ArrayList<StockLossPO>();
		stocklosslist = slds.getStockLoss();
		StockLossVO sofv = new StockLossVO();
		for(StockLossPO slp : stocklosslist){
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
	
	public ArrayList<StockLossVO> getCheckedStockLoss(){
		ArrayList<StockLossVO> stocklossList = new ArrayList<StockLossVO>();
		for(StockLossPO slp : slds.getStockLoss()){
			if(slp.getApproval() == 1){
				stocklossList.add(stockLossPoToVo(slp));
			}
		}
		return stocklossList;
	}
	
	public ArrayList<StockLossVO> getUncheckedStockLoss(){
		ArrayList<StockLossVO> stocklossList = new ArrayList<StockLossVO>();
		for(StockLossPO slp : slds.getStockLoss()){
			if(slp.getApproval() == 3){
				stocklossList.add(stockLossPoToVo(slp));
			}
		}
		return stocklossList;
	}
	
	public ArrayList<StockLossVO> getStockLoss(){
		ArrayList<StockLossVO> stocklossList = new ArrayList<StockLossVO>();
		for(StockLossPO slp : slds.getStockLoss()){
			stocklossList.add(stockLossPoToVo(slp));
		}
		return stocklossList;
	}

	@Override
	public ArrayList<StockOverflowVO> getCheckedStockOverflow() {
		return null;
	}

	@Override
	public ArrayList<PresentVO> getCheckedPresent() {
		return null;
	}
}
