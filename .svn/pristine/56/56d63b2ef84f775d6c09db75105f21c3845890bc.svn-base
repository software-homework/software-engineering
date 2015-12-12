package po.stockManage;

import java.io.Serializable;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class CategoryPO implements Serializable{
	String id;
	String name;
	ArrayList<CommodityPO> commodityList = new ArrayList<CommodityPO>();
	ArrayList<CategoryPO> categoryList = new ArrayList<CategoryPO>();
	
	public CategoryPO(){
		
	}
	
	public CategoryPO(String categoryname){
		this.name=categoryname;
	}
	
	public void setId(String id){
		this.id=id;
	}
	
	public String getId(){
		return id;
	}
	
	public void setName(String categoryname){
		this.name=categoryname;
	}
	
	public String getName(){
		return name;
	}
	
	public ArrayList<CommodityPO> getCommodityList(){
		return commodityList;
	}
	
	public void setCommodityList(ArrayList<CommodityPO> commodityList){
		this.commodityList = commodityList;
	}
	
	public ArrayList<CategoryPO> getCategoryList(){
		return categoryList;
	}
	
	public void setCategoryList(ArrayList<CategoryPO> categoryList){
		this.categoryList = categoryList;
	}
	
	public void setCategoryListEmpty(){
		this.categoryList.clear();
	}
	
	public void setCommodityListEmpty(){
		this.commodityList.clear();
	}
}
