package businesslogic.logistics;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import businesslogic.courier.ImportBL;
import businesslogic.courier.CourierBL;
import businesslogicservice.finance.GetCommodityBL;
import businesslogicservice.logistics.CommodityBLService;
import data.logistics.LogisticsData;
import dataservice.logistics.LogisticsDataService;
import po.logistics.CategoryPO;
import po.logistics.CommodityPO;
import vo.courier.ImportVO;
import vo.courier.CourierVO;
import vo.logistics.CategoryVO;
import vo.logistics.CommodityVO;
import vo.logistics.MessageVO;
import vo.logistics.LogisticsVO;
import vo.logistics.LogisticsSnapVO;

public class CommodityBL implements CommodityBLService,businesslogicservice.manager.GetCommodity, businesslogic.courier.GetCommodity,GetCommodityBL{
	private LogisticsDataService data = null;
	public GetImport gi ;
	public GetSales gs ;
	MessageBL sd;
	LogisticsSnapBL ssb;
	SimpleDateFormat sdf;
	CategoryBL cb;
	
	public CommodityBL(){
		try {
			data = LogisticsData.getInstance();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sdf = new SimpleDateFormat("yyyyMMdd");
		cb = new CategoryBL();
		sd = new MessageBL();
		ssb = new LogisticsSnapBL();
	}

	public boolean addCommodity(CommodityVO commodity,CategoryVO father) {
		// TODO Auto-generated method stub
		CategoryPO cp = new CategoryPO();
		CommodityPO cpp = new CommodityPO();
		cb.categoryVoToPo(father, cp);
		commodityVoToPo(commodity,cpp);
		if(data.add(cpp, cp)){
			return true;
		}
		return false;
	}

	public boolean deleteCommodity(CommodityVO commodity,CategoryVO father) {
		// TODO Auto-generated method stub
		CategoryPO cp = new CategoryPO();
		CommodityPO cpp = new CommodityPO();
		cb.categoryVoToPo(father, cp);
		commodityVoToPo(commodity,cpp);
		if(data.delete(cpp, cp)){
			return true;
		}
		return false;
	}

	public boolean updateCommodity(CommodityVO commodity,CommodityVO newCommodity,CategoryVO father) {
		// TODO Auto-generated method stub
		CategoryPO cp = new CategoryPO();
		CommodityPO cpp = new CommodityPO();
		CommodityPO newCpp = new CommodityPO();
		cb.categoryVoToPo(father, cp);
		commodityVoToPo(commodity,cpp);
		commodityVoToPo(newCommodity,newCpp);
		if(data.update(cpp, newCpp,cp)){
			return true;
		}
		return false;
	}

	public ArrayList<CommodityVO> findCommodity(String key,CategoryPO father){
		ArrayList<CommodityVO> commodityList = new ArrayList<CommodityVO>();
		if(father.getCategoryList().size() > 0){
			for(CategoryPO c : father.getCategoryList()){
				commodityList.addAll(findCommodity(key,c));
			}
		}
		else{
			for(CommodityPO cp : father.getCommodityList()){
				if(cp.getId().indexOf(key) >= 0){
					commodityList.add(commodityPoToVo(cp));
					continue;
				}
				if(cp.getCommodityName().indexOf(key) >= 0){
					commodityList.add(commodityPoToVo(cp));
					continue;
				}
				if(cp.getModel().indexOf(key) >= 0){
					commodityList.add(commodityPoToVo(cp));
					continue;
				}
			}
		}
		return commodityList;
	}

	public ArrayList<CommodityVO> findCommodity(int key,CategoryPO father){
		ArrayList<CommodityVO> commodityList = new ArrayList<CommodityVO>();
		if(father.getCategoryList().size() > 0){
			for(CategoryPO c : father.getCategoryList()){
				commodityList.addAll(findCommodity(key,c));
			}
		}
		else{
			for(CommodityPO cp : father.getCommodityList()){
				if(cp.getNumber() == key){
					commodityList.add(commodityPoToVo(cp));
					continue;
				}
				if(cp.getPriceIn() == key){
					commodityList.add(commodityPoToVo(cp));
					continue;
				}
				if(cp.getPriceOut() == key){
					commodityList.add(commodityPoToVo(cp));
					continue;
				}
				if(cp.getLastPriceIn() == key){
					commodityList.add(commodityPoToVo(cp));
					continue;
				}
				if(cp.getLastPriceOut() == key){
					commodityList.add(commodityPoToVo(cp));
					continue;
				}
			}
		}
		return commodityList;
	}
	
	public ArrayList<CommodityVO> findCommodity(String key) {
		// TODO Auto-generated method stub
		return findCommodity(key,data.getRoot());
	}
	
	public ArrayList<CommodityVO> findCommodity(int key){
		return findCommodity(key,data.getRoot());
	}
	
    public CommodityPO findCommodityPO(String id,CategoryPO father){
    	if(id == null){
    		return null;
    	}
    	if(father.getCategoryList().size() > 0){
    		for(CategoryPO c : father.getCategoryList()){
    			CommodityPO cp = findCommodityPO(id,c);
    		    if(cp != null){
    		    	return cp;
    		    }
    		}
    	}
    	else{
    		for(CommodityPO cc : father.getCommodityList()){
    			if(id.equals(cc.getId())){
    				return cc;
    			}
    		}
    	}
    	return null;
    }
    
    public CommodityPO commodityVoToPo(CommodityVO cv){
    	if(cv == null){
    		return null;
    	}
    	return findCommodityPO(cv.id,data.getRoot());
    }
    
    public void commodityVoToPo(CommodityVO cv,CommodityPO cp){
    	if(cv == null || cp == null){
    		return;
    	}
    	cp.setId(cv.id);
    	cp.setCommodityName(cv.commodityname);
    	cp.setModel(cv.model);
    	cp.setNumber(cv.number);
    	cp.setPriceIn(cv.priceIn);
    	cp.setPriceOut(cv.priceOut);
    	cp.setLastPriceIn(cv.lastPriceIn);
    	cp.setLastPriceOut(cv.lastPriceOut);
    	cp.setDangerNumber(cv.dangerNumber);
    	cp.setIsDelete(cv.isDelete);
    }
    
    public CommodityVO commodityPoToVo(CommodityPO cp){
    	if(cp == null){
    		return null;
    	}
    	CommodityVO cv = new CommodityVO();
    	cv.id = cp.getId();
    	cv.commodityname = cp.getCommodityName();
    	cv.model = cp.getModel();
    	cv.number = cp.getNumber();
    	cv.priceIn = cp.getPriceIn();
    	cv.priceOut = cp.getPriceOut();
    	cv.lastPriceIn = cp.getLastPriceIn();
    	cv.lastPriceOut = cp.getLastPriceOut();
    	cv.dangerNumber = cp.getDangerNumber();
    	cv.isDelete = cp.getIsDelete();
    	return cv;
    }
    
	public ArrayList<LogisticsVO> showImport(String start,String end) {
		// TODO Auto-generated method stub
		ArrayList<LogisticsVO> stockList = new ArrayList<LogisticsVO>();
		ArrayList<ImportVO> importList = new ArrayList<ImportVO>();
		gi = new ImportBL();
		importList=	gi.getCheckedImport();
		for(ImportVO iv : importList){
			int i = iv.id.indexOf("-");
			String type = iv.id.substring(0, i);
			String time = iv.id.substring(i + 1, i + 9);
			try {
				if(sdf.parse(time).compareTo(sdf.parse(start)) >= 0 && sdf.parse(time).compareTo(sdf.parse(end)) < 0){
					for(CommodityVO cv : iv.importList){
						if(type.equals("JHD")){
							LogisticsVO sv = new LogisticsVO(cv.id,cv.commodityname,cv.model,0,0,cv.number,cv.lastPriceIn,0,0,cv.number,cv.lastPriceIn);
							stockList.add(sv);
						}
						else if(type.equals("JHTHD")){
							LogisticsVO sv = new LogisticsVO(cv.id,cv.commodityname,cv.model,cv.number,cv.lastPriceIn,0,0,0,0,-cv.number,-cv.lastPriceIn);
							stockList.add(sv);
						}
					}
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(int i = 0;i < stockList.size();i ++){
			for(int j = i + 1;j < stockList.size();j ++){
				if(stockList.get(i).name.equals(stockList.get(j).name) && stockList.get(i).model.equals(stockList.get(j).model)){
					stockList.get(i).innumber = stockList.get(i).innumber + stockList.get(j).innumber;
					stockList.get(i).inmoney = stockList.get(i).inmoney + stockList.get(j).inmoney;
					stockList.get(i).importnumber = stockList.get(i).importnumber + stockList.get(j).importnumber;
					stockList.get(i).importmoney = stockList.get(i).importmoney + stockList.get(j).importmoney;
					
					stockList.remove(j);
					j --;
				}
			}
		}
		return stockList;
	}
	
	public ArrayList<LogisticsVO> showSales(String start, String end) {
		ArrayList<LogisticsVO> stockList = new ArrayList<LogisticsVO>();
		ArrayList<CourierVO> salesList = new ArrayList<CourierVO>();
		gs = new CourierBL();
		salesList =gs.getCheckedSales();
		for(CourierVO sv : salesList){
			int i = sv.id.indexOf("-");
			String type = sv.id.substring(0, i);
			String time = sv.id.substring(i + 1, i + 9);
			try {
				if(sdf.parse(time).compareTo(sdf.parse(start)) >= 0 && sdf.parse(time).compareTo(sdf.parse(end)) < 0){
					for(CommodityVO cv : sv.salesList){
						if(type.equals("XSD")){
							LogisticsVO sv1 = new LogisticsVO(cv.id,cv.commodityname,cv.model,cv.number,cv.lastPriceOut,0,0,cv.number,cv.lastPriceOut,0,0);
							stockList.add(sv1);
						}
						else if(type.equals("XSTHD")){
							LogisticsVO sv1 = new LogisticsVO(cv.id,cv.commodityname,cv.model,0,0,cv.number,cv.lastPriceOut,-cv.number,-cv.lastPriceOut,0,0);
							stockList.add(sv1);
						}
					}
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(int i = 0;i < stockList.size();i ++){
			for(int j = i + 1;j < stockList.size();j ++){
				if(stockList.get(i).name.equals(stockList.get(j).name) && stockList.get(i).model.equals(stockList.get(j).model)){
					stockList.get(i).outnumber = stockList.get(i).outnumber + stockList.get(j).outnumber;
					stockList.get(i).outmoney = stockList.get(i).outmoney + stockList.get(j).outmoney;
					stockList.get(i).salesnumber = stockList.get(i).salesnumber + stockList.get(j).salesnumber;
					stockList.get(i).salesmoney = stockList.get(i).salesmoney + stockList.get(j).salesmoney;
					
					stockList.remove(j);
					j --;
				}
			}
		}
		return stockList;
	}
	
	public ArrayList<LogisticsVO> merge(String start,String end){
		ArrayList<LogisticsVO> stockList = new ArrayList<LogisticsVO>();
	
	    for(LogisticsVO sv1 : showSales(start,end)){
				stockList.add(sv1);
	    }
			
		for(LogisticsVO sv2 : showImport(start,end)){
				stockList.add(sv2);
		}
		
	    for(LogisticsVO sv : stockList){
	    	for(CommodityVO cv : getCommodity()){
	    		if(sv.name.equals(cv.commodityname) && sv.model.equals(cv.model)){
	    			sv.totalnumber = cv.number;
	    		}
	    	}
	    }
		
		for(int i = 0;i < stockList.size();i ++){
			for(int j = i + 1;j < stockList.size();j ++){
				if(stockList.get(i).name.equals(stockList.get(j).name) && stockList.get(i).model.equals(stockList.get(j).model)){
					stockList.get(i).outnumber = stockList.get(i).outnumber + stockList.get(j).outnumber;
					stockList.get(i).outmoney = stockList.get(i).outmoney + stockList.get(j).outmoney;
					stockList.get(i).innumber = stockList.get(i).innumber + stockList.get(j).innumber;
					stockList.get(i).inmoney = stockList.get(i).inmoney + stockList.get(j).inmoney;
					stockList.get(i).salesnumber = stockList.get(i).salesnumber + stockList.get(j).salesnumber;
					stockList.get(i).salesmoney = stockList.get(i).salesmoney + stockList.get(j).salesmoney;
					stockList.get(i).importnumber = stockList.get(i).importnumber + stockList.get(j).importnumber;
					stockList.get(i).importmoney = stockList.get(i).importmoney + stockList.get(j).importmoney;
					
					stockList.remove(j);
					j --;
				}
			}
		}
		return stockList;
	}
	
	public ArrayList<LogisticsVO> showImport(){
		ArrayList<LogisticsVO> stockList = new ArrayList<LogisticsVO>();
		ArrayList<ImportVO> importList = new ArrayList<ImportVO>();
		gi = new ImportBL();
		importList=	gi.getCheckedImport();
		for(ImportVO iv : importList){
			int i = iv.id.indexOf("-");
			String type = iv.id.substring(0, i);
			for(CommodityVO cv : iv.importList){
				if(type.equals("JHD")){
					LogisticsVO sv = new LogisticsVO(cv.id,cv.commodityname,cv.model,0,0,cv.number,cv.lastPriceIn,0,0,cv.number,cv.lastPriceIn);
					stockList.add(sv);
				}
				else if(type.equals("JHTHD")){
					LogisticsVO sv = new LogisticsVO(cv.id,cv.commodityname,cv.model,0,0,-cv.number,-cv.lastPriceIn,0,0,-cv.number,-cv.lastPriceIn);
					stockList.add(sv);
				}
			}
		}
		for(int i = 0;i < stockList.size();i ++){
			for(int j = i + 1;j < stockList.size();j ++){
				if(stockList.get(i).name.equals(stockList.get(j).name) && stockList.get(i).model.equals(stockList.get(j).model)){
					stockList.get(i).innumber = stockList.get(i).innumber + stockList.get(j).innumber;
					stockList.get(i).inmoney = stockList.get(i).inmoney + stockList.get(j).inmoney;
					stockList.get(i).importnumber = stockList.get(i).importnumber + stockList.get(j).importnumber;
					stockList.get(i).importmoney = stockList.get(i).importmoney + stockList.get(j).importmoney;
					
					stockList.remove(j);
					j --;
				}
			}
		}
		return stockList;
	}
	
	private boolean isIn(CommodityVO cv,ArrayList<LogisticsVO> stockList){
		if(cv == null){
			return false;
		}
		if(stockList.size() == 0){
			return false;
		}
		for(LogisticsVO sv : stockList){
			if(cv.commodityname.equals(sv.name) && cv.model.equals(sv.model)){
				return true;
			}
		}
		return false;
	}
	
	public void checkStock(){
		String batch = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
		int count = 1;
		LogisticsSnapVO ssv = null;
		ArrayList<ImportVO> importList = new ArrayList<ImportVO>();
		gi = new ImportBL();
		importList=	gi.getCheckedImport();
		for(CommodityVO cv2 : getCommodity()){
			for(ImportVO iv : importList){
				int i = iv.id.indexOf("-");
				String str = iv.id.substring(i + 1);
				int j = str.indexOf("-");
				String time = str.substring(0, j);
				for(CommodityVO cv : iv.importList){
					if(cv.commodityname.equals(cv2.commodityname) && cv.model.equals(cv2.model)){
						for(LogisticsVO sv : showImport()){
							if(cv.commodityname.equals(sv.name) && cv.model.equals(sv.model)){
								ssv = new LogisticsSnapVO(count,cv.commodityname,cv.model,cv2.number,sv.importmoney / sv.importnumber,batch);
								ssv.outTime = time;
								count ++;
								ssb.add(ssv);
							}
						}
					}
				}
			}
			if(!isIn(cv2,showImport())){
				ssv = new LogisticsSnapVO(count,cv2.commodityname,cv2.model,cv2.number,0,batch);
				ssv.outTime = "0";
				count ++;
				ssb.add(ssv);
			}
		}
	}

	public boolean sendDanger(CommodityVO commodity,int number) {
		// TODO Auto-generated method stub
		if(commodity == null){
			return false;
		}
		CommodityPO cp = commodityVoToPo(commodity);
		if(cp == null){
			return false;
		}
		cp.setDangerNumber(number);
		return true;
	}

	@Override
	public ArrayList<CommodityVO> getCommodity() {
		// TODO Auto-generated method stub
		ArrayList<CommodityVO> commodityList = new ArrayList<CommodityVO>();
		for(CommodityPO cp : data.showStock()){
			CommodityVO cv = commodityPoToVo(cp);
			commodityList.add(cv);
		}
		return commodityList;
	}

	@Override
	public CommodityVO getCommodity(String id) {
		// TODO Auto-generated method stub
		return commodityPoToVo(findCommodityPO(id,data.getRoot()));
	}
	
	public boolean importChange(CommodityVO cv){
		for(CommodityPO cp : data.showStock()){
			if(cp.getCommodityName().equals(cv.commodityname) && cp.getModel().equals(cv.model)){
				CommodityVO newCv = (CommodityVO)commodityPoToVo(cp).clone();
				newCv.number = cp.getNumber() + cv.number;
				newCv.lastPriceIn = cv.priceIn;
				newCv.isDelete = false;
				if(newCv.number < 0 || newCv.lastPriceIn < 0){
					return false;
				}
				else{
					if(updateCommodity(cv, newCv, getFather(cv))){
						if(newCv.number < newCv.dangerNumber){
							sd.add(new MessageVO("库存报警",cv.commodityname,cv.model,newCv.number,"空",false));
						}
						return true;
					}
					else{
						return false;
					}
				}
			}
		}
		return false;
	}
	
	public boolean salesChange(CommodityVO cv){
		for(CommodityPO cp : data.showStock()){
			if(cp.getCommodityName().equals(cv.commodityname) && cp.getModel().equals(cv.model)){
				CommodityVO newCv = (CommodityVO)commodityPoToVo(cp).clone();
				newCv.number = cp.getNumber() + cv.number;
				newCv.lastPriceOut = cv.priceOut;
				newCv.isDelete = false;
				if(newCv.number < 0 || newCv.lastPriceOut < 0){
					return false;
				}
				else{
					if(updateCommodity(cv, newCv, getFather(cv))){
						if(newCv.number < newCv.dangerNumber){
							sd.add(new MessageVO("库存报警",cv.commodityname,cv.model,newCv.number,"空",false));
						}
						return true;
					}
					else{
						return false;
					}
				}
			}
		}
		return false;
	}
	
	public CategoryVO getFather(CommodityVO commodity){
		CommodityPO cp = new CommodityPO();
		commodityVoToPo(commodity, cp);
		return cb.categoryPoToVo(data.getFather(cp));
	}
}
