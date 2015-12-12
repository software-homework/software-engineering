package businesslogicservice.stockManage;

import vo.stockManage.CategoryVO;

public interface CategoryBLService {
	public CategoryVO getRoot();
	
	public boolean addCategory(CategoryVO category,CategoryVO father);
	
	public boolean deleteCategory(CategoryVO category,CategoryVO father);
	
	public boolean updateCategory(CategoryVO cp,CategoryVO cv,CategoryVO father);
	
	public CategoryVO getFather(CategoryVO category);
}
