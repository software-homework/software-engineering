package businesslogicservice.manager;

import java.util.*;

import vo.salesman.*;

/**
 * 
 * @date 2014年12月6日
 * @time 下午2:54:08
 * @author stk
 *
 */

/*
 * 获得进货单信息接口
 */
public interface GetImport {
	public boolean approvalImport(String id, int approval);
	public void updateImport(ImportVO ivo);
	public ArrayList<ImportVO> getUncheckedImport();
	public ArrayList<ImportVO> getCheckedImport();
}