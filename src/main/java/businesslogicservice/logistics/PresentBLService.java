package businesslogicservice.logistics;

import java.util.ArrayList;

import vo.logistics.PresentVO;

public interface PresentBLService {
	public boolean add(PresentVO pv);
	
	public ArrayList<PresentVO> getPresent();
}
