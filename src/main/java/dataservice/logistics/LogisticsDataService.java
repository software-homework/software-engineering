package dataservice.logistics;

import java.util.ArrayList;

import po.logistics.CategoryPO;
import po.logistics.CommodityPO;

public interface LogisticsDataService {
    public CategoryPO getRoot();
	
    public boolean add(CategoryPO category,CategoryPO father);
	
	public boolean delete(CategoryPO category,CategoryPO father);
	
	public boolean update(CategoryPO category,CategoryPO newCategory,CategoryPO father);
	
    public boolean add(CommodityPO commodity,CategoryPO father);
	
	public boolean delete(CommodityPO commodity,CategoryPO father);
	
	public boolean update(CommodityPO commodity,CommodityPO newCommodity,CategoryPO father);
	
	public ArrayList<CommodityPO> showStock();
	
	public CategoryPO getFather(CategoryPO category);
	
	public CategoryPO getFather(CommodityPO commodity);
	
}
