package businesslogicservice.courier;

import java.util.ArrayList;

import vo.courier.CourierVO;

public interface CourierBLService {
	public String getsID();
	public String getsrID();
	public boolean CheckS(CourierVO svo);
	public ArrayList<CourierVO> ShowS();
	public CourierVO findSales(String id);
}
