package businesslogicservice.manager;

import java.util.ArrayList;

import vo.stockManage.PresentVO;

/**
 * 
 * @date 2014年12月9日
 * @time 下午9:20:57
 * @author stk
 *
 */

/*
 * 获得赠送单信息接口
 */
public interface GetPresent {
	public boolean approvalPresent(String id, int approval);
	public void updatePresent(PresentVO vo);
	public ArrayList<PresentVO> getUncheckedPresent();
	public ArrayList<PresentVO> getCheckedPresent();
	public boolean add(PresentVO pv);
}
