package businesslogicservice.stockManage;

import java.util.ArrayList;
import vo.stockManage.*;

public interface CommodityBLService {
	
	public boolean addCommodity(CommodityVO commodity,CategoryVO father);
	
	public boolean deleteCommodity(CommodityVO commodity,CategoryVO father);
	
	public boolean updateCommodity(CommodityVO commodity,CommodityVO newCommodity,CategoryVO father);
	
	public ArrayList<CommodityVO> findCommodity(String key);
	
	public ArrayList<CommodityVO> findCommodity(int key);
	 
	public CommodityVO getCommodity(String id);
	
	public ArrayList<CommodityVO> getCommodity();
	
	public CategoryVO getFather(CommodityVO commodity);
	
	public ArrayList<StockVO> merge(String start,String end);
	
//	public CategoryVO findCategory(CommodityVO commodity);
	
	public void checkStock();
	
	public boolean sendDanger(CommodityVO commodity,int number);
	
}
