package ui.logistics;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import businesslogic.logistics.CategoryBL;
import businesslogicservice.logistics.CategoryBLService;
import vo.logistics.CategoryVO;

public class LogisticsTree implements TreeModel {
	public CategoryBLService cs;
//	public CategoryVO root;
	
	public LogisticsTree(){
		cs = new CategoryBL();
//		update();
	}
	
/*	public void update()
	{
		root = cs.findCategory(null);
	}*/

	public Object getRoot(){
		return cs.getRoot();
	}
	
	public Object getChild(Object parent,int index){
		CategoryVO cv = (CategoryVO) parent;
		if(cv == null){
			return null;
		}
		return cv.categoryList.get(index);
	}
	
	public int getChildCount(Object parent){
		CategoryVO cv = (CategoryVO) parent;
		if(cv == null){
			return 0;
		}
		return cv.categoryList.size();
	}
	
	public boolean isLeaf(Object node){
/*		CategoryVO cv = (CategoryVO) node;
		if(cv == null){
			return true;
		}
		if(cv.categoryList.size() == 0){
			return true;
		}
		else{
			return false;
		}*/
		if(getChildCount(node) == 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void valueForPathChanged(TreePath path, Object newValue) {
		// TODO Auto-generated method stub
		
	}

	public int getIndexOfChild(Object parent, Object child) {
		CategoryVO cv1 = (CategoryVO)parent;
		CategoryVO cv2 = (CategoryVO)child;
		for(CategoryVO c : cv1.categoryList){
			if(c == cv2){
				return c.hashCode();
			}
		}
		return 0;
	}

	public void addTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub
		
	}

	public void removeTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub
		
	}

}
