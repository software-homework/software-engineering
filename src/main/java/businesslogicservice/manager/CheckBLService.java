package businesslogicservice.manager;

/**
 * 
 * @date 2014年12月8日
 * @time 下午7:44:55
 * @author stk
 *
 */

/*
 * 查看表格逻辑接口
 */
public interface CheckBLService {
	public Object[][] showCondition(String[] str);
	//-------------------------------------------------
	public boolean output(String path, Object[][] data, String[] name);
}
