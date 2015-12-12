package dataservice.stockManage;

import java.util.ArrayList;
import po.stockManage.CategoryPO;
import po.stockManage.CommodityPO;

public interface StockDataService {
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
