package businesslogic.logistics;

import java.rmi.RemoteException;

import businesslogicservice.logistics.CategoryBLService;
import data.logistics.LogisticsData;
import dataservice.logistics.LogisticsDataService;
import po.logistics.CategoryPO;
import po.logistics.CommodityPO;
import vo.logistics.CategoryVO;

public class CategoryBL implements CategoryBLService{
	private LogisticsDataService data = null;
	
	public CategoryBL(){
		try {
			data = LogisticsData.getInstance();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public CategoryVO getRoot(){
		return categoryPoToVo(data.getRoot());
	}
	
	public boolean addCategory(CategoryVO category, CategoryVO father) {
		CategoryPO cp = new CategoryPO();
		CategoryPO cpp = new CategoryPO();
		categoryVoToPo(category,cp);
		categoryVoToPo(father,cpp);
		if(data.add(cp, cpp)){
			return true;
		}
		return false;
	}
	
	public boolean deleteCategory(CategoryVO category, CategoryVO father) {
		CategoryPO cp = new CategoryPO();
		CategoryPO cpp = new CategoryPO();
		categoryVoToPo(category,cp);
		categoryVoToPo(father,cpp);
		if(data.delete(cp, cpp)){
			return true;
		}
		return false;
	}

	public boolean updateCategory(CategoryVO category,CategoryVO newCategory,CategoryVO father) {
		CategoryPO cp = new CategoryPO();
		CategoryPO newCp = new CategoryPO();
		CategoryPO cpp = new CategoryPO();
		categoryVoToPo(category,cp);
		categoryVoToPo(newCategory,newCp);
		categoryVoToPo(father,cpp);
		if(data.update(cp, newCp, cpp)){
			return true;
		}
		return false;
	}
	
	public void categoryVoToPo(CategoryVO cv,CategoryPO cp){
		cp.setId(cv.id);
		cp.setName(cv.categoryname);
	}
	
	public CategoryVO categoryPoToVo(CategoryPO cp){
		if(cp == null){
			return null;
		}
		CategoryVO cv=new CategoryVO();
		cv.id = cp.getId();
		cv.categoryname = cp.getName();
		for(CategoryPO c:cp.getCategoryList()){
			cv.categoryList.add(categoryPoToVo(c));
		}
		for(CommodityPO com:cp.getCommodityList()){
			cv.commodityList.add(new CommodityBL().commodityPoToVo(com));
		}
		return cv;
	}
	
	public CategoryVO getFather(CategoryVO category){
		CategoryPO cp = new CategoryPO();
		categoryVoToPo(category,cp);
		return categoryPoToVo(data.getFather(cp));
	}

}
