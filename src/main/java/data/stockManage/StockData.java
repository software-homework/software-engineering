package data.stockManage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Stack;

import po.stockManage.CategoryPO;
import po.stockManage.CommodityPO;
import dataservice.stockManage.StockDataService;

public class StockData extends UnicastRemoteObject implements StockDataService {
	private static final long serialVersionUID = -8868676826752697887L;
	private static CategoryPO root;
	private static StockData sd;

	private StockData()throws RemoteException{
		root = new CategoryPO();
		root.setId("-1");
		root.setName("×Ü·ÖÀà");
		readFromFile();
		updateId();
	}
	
	public static StockData getInstance()throws RemoteException{
		if(sd == null){
			sd = new StockData();
		}
		return sd;
	}
	
	public CategoryPO getRoot(){
		return root;
	}
	
	@Override
	public boolean add(CommodityPO commodity, CategoryPO father) {
		// TODO Auto-generated method stub
		CategoryPO cp = findCategory(father.getId());
		if(cp == null){
			return false;
		}
		if(cp.getCategoryList().size() > 0){
			return false;
		}
		
		if(commodity == null){
			return false;
		}
		for(CommodityPO c : showStock(root)){
			if(c.getCommodityName().equals(commodity.getCommodityName()) && c.getModel().equals(commodity.getModel())){
				return false;
			}
		}
		ArrayList<CommodityPO> list = new ArrayList<CommodityPO>();
		list = cp.getCommodityList();
		list.add(commodity);
		cp.setCommodityList(list);
		updateId();
		writeToFile();
		return true;
	}

	@Override
	public boolean delete(CommodityPO commodity, CategoryPO father) {
		// TODO Auto-generated method stub
		CategoryPO cp = findCategory(father.getId());
		if(cp == null){
			return false;
		}
		CommodityPO cpp = commodityVoToPo(commodity);
		if(cpp == null){
			return false;
		}
		
		if(cpp.getIsDelete() == false){
			return false;
		}
		
		if(cpp.getNumber() != 0){
			return false;
		}
		ArrayList<CommodityPO> list = new ArrayList<CommodityPO>();
		list = cp.getCommodityList();
		list.remove(cpp);
		cp.setCommodityList(list);
		updateId();
		writeToFile();
		return true;
	}

	@Override
	public boolean update(CommodityPO commodity, CommodityPO newCommodity,CategoryPO father) {
		if(newCommodity == null){
			return false;
		}
		
/*		for(CommodityPO c : showStock(root)){
			if(c.getCommodityName().equals(newCommodity.getCommodityName()) && c.getModel().equals(newCommodity.getModel())){
				return false;
			}
		}*/
		
		CategoryPO cp = findCategory(father.getId());
		if(cp == null){
			return false;
		}
		CommodityPO cpp = commodityVoToPo(commodity);
		if(cpp == null){
			return false;
		}
		
		ArrayList<CommodityPO> list = new ArrayList<CommodityPO>();
		list = cp.getCommodityList();
		list.add(list.indexOf(cpp),newCommodity);
		list.remove(cpp);
		cp.setCommodityList(list);
		updateId();
		writeToFile();
		return true;
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
    
    public CommodityPO commodityVoToPo(CommodityPO cp){
    	if(cp == null){
    		return null;
    	}
    	return findCommodityPO(cp.getId(),root);
    }
    
    public CategoryPO getFather(CommodityPO commodity, CategoryPO category){
		for (CommodityPO cp : category.getCommodityList()){
			if (cp == commodity){
				return category;
			}
		}
		for (CategoryPO cp : category.getCategoryList()){
			CategoryPO cpp = getFather(commodity, cp);
			if (cpp != null){
				return cpp;
			}
		}
		return null;
	}
	
	public CategoryPO getFather(CommodityPO commodity){
		return getFather(commodityVoToPo(commodity), root);
	}


	public ArrayList<CommodityPO> showStock(CategoryPO father) {
        ArrayList<CommodityPO> commodityList = new ArrayList<CommodityPO>();
        if(father.getCategoryList().size() > 0){
			for(CategoryPO c : father.getCategoryList()){
				commodityList.addAll(showStock(c));
			}
		}
        else{
        	for(CommodityPO cp : father.getCommodityList()){
        		commodityList.add(cp);
        	}
        }
        return commodityList;
	}
	
	public ArrayList<CommodityPO> showStock() {
        return showStock(root);
	}
	
	@Override
	public boolean add(CategoryPO category, CategoryPO father) {
		CategoryPO cp = findCategory(father.getId());
		if(cp == null){
			return false;
		}
		for(CategoryPO c:cp.getCategoryList()){
			if(c.getName().equals(category.getName())){
				return false;
			}
		}
		ArrayList<CategoryPO> list = new ArrayList<CategoryPO>();
		list = cp.getCategoryList();
		list.add(category);
		cp.setCategoryList(list);
		updateId();
		writeToFile();
		return true;
	}

	@Override
	public boolean delete(CategoryPO category, CategoryPO father) {
		CategoryPO cp = findCategory(father.getId());
	    if(cp == null){
	    	return false;
	    }
	    
	    for(CategoryPO c:cp.getCategoryList()){
			if(c.getName().equals(category.getName())){
				ArrayList<CategoryPO> list = new ArrayList<CategoryPO>();
				list = cp.getCategoryList();
				list.remove(c);
				cp.setCategoryList(list);
				updateId();
				writeToFile();
				return true;
			}
		}
	    return false;
	}

	@Override
	public boolean update(CategoryPO category, CategoryPO newCategory,
			CategoryPO father) {
		// TODO Auto-generated method stub
		CategoryPO cp1 = findCategory(father.getId());
		if(cp1 == null){
			return false;
		}
		CategoryPO cp2=findCategory(category.getId());
		if(cp2 == null){
			return false;
		}
		for(CategoryPO c:cp1.getCategoryList()){
			if(c.getName().equals(newCategory.getName())){
				return false;
			}
		}
		for (CategoryPO c : cp2.getCategoryList()){
			ArrayList<CategoryPO> list = new ArrayList<CategoryPO>();
			list = newCategory.getCategoryList();
			list.add(c);
			newCategory.setCategoryList(list);
		}
		for (CommodityPO c : cp2.getCommodityList()){
			ArrayList<CommodityPO> list = new ArrayList<CommodityPO>();
			list = newCategory.getCommodityList();
			list.add(c);
			newCategory.setCommodityList(list);
		}
		ArrayList<CategoryPO> list = new ArrayList<CategoryPO>();
		list = cp1.getCategoryList();
		list.add(list.indexOf(cp2),newCategory);
		list.remove(cp2);
		cp1.setCategoryList(list);
		updateId();
		writeToFile();
		return true;
	}
	
	public CategoryPO findCategory(String id){
		if(id.equals("-1")){
			return root;
		}
		return findCategoryPO(id,root);
	}
	
	public CategoryPO findCategoryPO(String id,CategoryPO category){
		if(category == null){
			return null;
		}
		if(category.getCategoryList().size() != 0){
			for(CategoryPO cp:category.getCategoryList()){
				if(cp.getId().equals(id)){
					return cp;
				}
				else{
					CategoryPO cpp = findCategoryPO(id,cp);
					if(cpp != null){
						return cpp;
					}
				}
			}
		}
		return null;
	}
	
	public CategoryPO getFather(CategoryPO category){
		return getFather(findCategory(category.getId()),root);
	}

	public CategoryPO getFather(CategoryPO category,CategoryPO father){
		for(CategoryPO cp : father.getCategoryList()){
			if(cp == category){
				return father;
			}
		}
		for(CategoryPO cp : father.getCategoryList()){
			CategoryPO cpp = getFather(category,cp);
			if(cpp != null){
				return cpp;
			}
		}
		return null;
	}

	public void write(CategoryPO category,PrintWriter pw){
		pw.println("<categorystart>:");
		pw.println("<categoryname>:" + category.getName());
		if(category.getCategoryList().size() > 0 || category.getCommodityList().size() > 0){
			if(category.getCategoryList().size() == 0){
				for(CommodityPO cp : category.getCommodityList()){
					pw.println("<commoditystart>:");
					pw.println("<commodityname>:" + cp.getCommodityName());
					pw.println("<model>:" + cp.getModel());
					pw.println("<number>:" + cp.getNumber());
					pw.println("<priceIn>:" + cp.getPriceIn());
					pw.println("<priceOut>:" + cp.getPriceOut());
					pw.println("<lastPriceIn>:" + cp.getLastPriceIn());
					pw.println("<lastPriceOut>:" + cp.getLastPriceOut());
					pw.println("<dangerNumber>:" + cp.getDangerNumber());
					pw.println("<commodityend>");
				}
			}
			else{
				pw.println("<in>:");
				for(CategoryPO cp : category.getCategoryList()){
					write(cp,pw);
				}
				pw.println("<out>");
			}
		}
		pw.println("<categoryend>");
	}
	
	public void readFromFile(){
		try{
			File file=new File("stock.txt");
			root.setCategoryListEmpty();
			root.setCommodityListEmpty();
	        if(!file.exists()){
	        	file.createNewFile();
	        	return;
	        }
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			CategoryPO father = root;
			Stack<CategoryPO> categorystack = new Stack<CategoryPO>();
			CategoryPO category = null;
			CommodityPO commodity = null;
	        String str1 = null;
	        str1 = br.readLine();
	        while(str1 != null){
	        	int i1 = str1.indexOf("<");
	        	int i2 = str1.indexOf(">");
	        	if(i1 < 0 || i2 < 0){
	        		break;
	        	}
	        	String str2 = str1.substring(i1 + 1, i2);
	        	String str3 = null;
	        	if(str1.length() > i2 + 2){
	        		str3 = str1.substring(i2 + 2);
	        	}
	        	if(str2.equals("categorystart")){
	        		category = new CategoryPO();
	        	}
	        	else if(str2.equals("categoryend")){
	        		ArrayList<CategoryPO> list = new ArrayList<CategoryPO>();
	        		list = father.getCategoryList();
	        		list.add(category);
	        		father.setCategoryList(list);
	        	}
	        	else if(str2.equals("commoditystart")){
	        		commodity = new CommodityPO();
	        	}
	        	else if(str2.equals("commodityend")){
	        		ArrayList<CommodityPO> list = new ArrayList<CommodityPO>();
	        		list = category.getCommodityList();
	        		list.add(commodity);
	        		category.setCommodityList(list);
	        	}
	        	else if(str2.equals("in")){
	        		categorystack.push(father);
	        		father = category;
	        		category = null;
	        	}
	        	else if (str2.equals("out"))
	        	{
	        		category = father;
	        		father = categorystack.pop();
	        	}
	        	else if(str2.equals("categoryname")){
	        		if(category != null){
	        			category.setName(new String(str3));
	        		}
	        	}
	        	else if(str2.equals("commodityname")){
	        		if(commodity != null){
	        			commodity.setCommodityName(new String(str3));
	        		}
	        	}
	        	else if (str2.equals("model")){
	        		commodity.setModel(new String(str3));
	        	}
	        	else if (str2.equals("number")){
	        		commodity.setNumber(Integer.parseInt(str3));
	        	}
	        	else if (str2.equals("priceIn")){
	        		commodity.setPriceIn(Double.parseDouble(str3));
	        	}
	        	else if (str2.equals("priceOut")){
	        		commodity.setPriceOut(Double.parseDouble(str3));
	        	}
	        	else if (str2.equals("lastPriceIn")){
	        		commodity.setLastPriceIn(Double.parseDouble(str3));
	        	}
	        	else if (str2.equals("lastPriceOut")){
	        		commodity.setLastPriceOut(Double.parseDouble(str3));
	        	}
	        	else if (str2.equals("dangerNumber")){
	        		commodity.setDangerNumber(Integer.parseInt(str3));
	        	}
	        	str1 = br.readLine();
	        }
	        br.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public void writeToFile(){
		try{
			File file = new File("stock.txt");
	        if(!file.exists()){
	        	file.createNewFile();
	        	root.setCategoryListEmpty();
				root.setCommodityListEmpty();
	        	return;
	        }
	        BufferedWriter bw = null;
			
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			PrintWriter pw = new PrintWriter(bw);
			
			
			for(CategoryPO cp : root.getCategoryList()){
				write(cp, pw);
			}
	        pw.close();  
	        
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public void updateId(CategoryPO category, CategoryPO father){
		if (father.getId().equals("-1")){
			category.setId("" + father.getCategoryList().indexOf(category));
		}
		else{
			category.setId(father.getId() + "-" + father.getCategoryList().indexOf(category));
		}
		for (CategoryPO child : category.getCategoryList()){
			updateId(child, category);
		}
		for (CommodityPO c : category.getCommodityList()){
			c.setId(category.getId() + "-" + category.getCommodityList().indexOf(c));
		}
	}

	public void updateId(){
		for (CategoryPO category : root.getCategoryList()){
			updateId(category, root);
		}
	}
}
