package businesslogic.stockManage;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import businesslogic.salesman.ImportBL;
import businesslogic.salesman.SalesBL;
import businesslogicservice.finance.GetCommodityBL;
import businesslogicservice.stockManage.CommodityBLService;
import data.stockManage.StockData;
import dataservice.stockManage.StockDataService;
import po.stockManage.CategoryPO;
import po.stockManage.CommodityPO;
import vo.salesman.ImportVO;
import vo.salesman.SalesVO;
import vo.stockManage.CategoryVO;
import vo.stockManage.CommodityVO;
import vo.stockManage.MessageVO;
import vo.stockManage.StockVO;
import vo.stockManage.StocksnapVO;

public class CommodityBL implements CommodityBLService,businesslogicservice.manager.GetCommodity, businesslogic.salesman.GetCommodity,GetCommodityBL{
	private StockDataService data = null;
	public GetImport gi ;
	public GetSales gs ;
	MessageBL sd;
	StocksnapBL ssb;
	SimpleDateFormat sdf;
	CategoryBL cb;
	
	public CommodityBL(){
		try {
			data = StockData.getInstance();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sdf = new SimpleDateFormat("yyyyMMdd");
		cb = new CategoryBL();
		sd = new MessageBL();
		ssb = new StocksnapBL();
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
    
	public ArrayList<StockVO> showImport(String start,String end) {
		// TODO Auto-generated method stub
		ArrayList<StockVO> stockList = new ArrayList<StockVO>();
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
							StockVO sv = new StockVO(cv.id,cv.commodityname,cv.model,0,0,cv.number,cv.lastPriceIn,0,0,cv.number,cv.lastPriceIn);
							stockList.add(sv);
						}
						else if(type.equals("JHTHD")){
							StockVO sv = new StockVO(cv.id,cv.commodityname,cv.model,cv.number,cv.lastPriceIn,0,0,0,0,-cv.number,-cv.lastPriceIn);
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
	
	public ArrayList<StockVO> showSales(String start, String end) {
		ArrayList<StockVO> stockList = new ArrayList<StockVO>();
		ArrayList<SalesVO> salesList = new ArrayList<SalesVO>();
		gs = new SalesBL();
		salesList =gs.getCheckedSales();
		for(SalesVO sv : salesList){
			int i = sv.id.indexOf("-");
			String type = sv.id.substring(0, i);
			String time = sv.id.substring(i + 1, i + 9);
			try {
				if(sdf.parse(time).compareTo(sdf.parse(start)) >= 0 && sdf.parse(time).compareTo(sdf.parse(end)) < 0){
					for(CommodityVO cv : sv.salesList){
						if(type.equals("XSD")){
							StockVO sv1 = new StockVO(cv.id,cv.commodityname,cv.model,cv.number,cv.lastPriceOut,0,0,cv.number,cv.lastPriceOut,0,0);
							stockList.add(sv1);
						}
						else if(type.equals("XSTHD")){
							StockVO sv1 = new StockVO(cv.id,cv.commodityname,cv.model,0,0,cv.number,cv.lastPriceOut,-cv.number,-cv.lastPriceOut,0,0);
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
	
	public ArrayList<StockVO> merge(String start,String end){
		ArrayList<StockVO> stockList = new ArrayList<StockVO>();
	
	    for(StockVO sv1 : showSales(start,end)){
				stockList.add(sv1);
	    }
			
		for(StockVO sv2 : showImport(start,end)){
				stockList.add(sv2);
		}
		
	    for(StockVO sv : stockList){
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
	
	public ArrayList<StockVO> showImport(){
		ArrayList<StockVO> stockList = new ArrayList<StockVO>();
		ArrayList<ImportVO> importList = new ArrayList<ImportVO>();
		gi = new ImportBL();
		importList=	gi.getCheckedImport();
		for(ImportVO iv : importList){
			int i = iv.id.indexOf("-");
			String type = iv.id.substring(0, i);
			for(CommodityVO cv : iv.importList){
				if(type.equals("JHD")){
					StockVO sv = new StockVO(cv.id,cv.commodityname,cv.model,0,0,cv.number,cv.lastPriceIn,0,0,cv.number,cv.lastPriceIn);
					stockList.add(sv);
				}
				else if(type.equals("JHTHD")){
					StockVO sv = new StockVO(cv.id,cv.commodityname,cv.model,0,0,-cv.number,-cv.lastPriceIn,0,0,-cv.number,-cv.lastPriceIn);
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
	
	private boolean isIn(CommodityVO cv,ArrayList<StockVO> stockList){
		if(cv == null){
			return false;
		}
		if(stockList.size() == 0){
			return false;
		}
		for(StockVO sv : stockList){
			if(cv.commodityname.equals(sv.name) && cv.model.equals(sv.model)){
				return true;
			}
		}
		return false;
	}
	
	public void checkStock(){
		String batch = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
		int count = 1;
		StocksnapVO ssv = null;
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
						for(StockVO sv : showImport()){
							if(cv.commodityname.equals(sv.name) && cv.model.equals(sv.model)){
								ssv = new StocksnapVO(count,cv.commodityname,cv.model,cv2.number,sv.importmoney / sv.importnumber,batch);
								ssv.outTime = time;
								count ++;
								ssb.add(ssv);
							}
						}
					}
				}
			}
			if(!isIn(cv2,showImport())){
				ssv = new StocksnapVO(count,cv2.commodityname,cv2.model,cv2.number,0,batch);
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
