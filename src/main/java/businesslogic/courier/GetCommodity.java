package businesslogic.courier;

import java.util.ArrayList;

import po.logistics.CommodityPO;
import vo.logistics.CommodityVO;

public interface GetCommodity {
	public void commodityVoToPo(CommodityVO cv,CommodityPO cp);
	public CommodityVO commodityPoToVo(CommodityPO cp);
	public ArrayList<CommodityVO> getCommodity();
	public boolean importChange(CommodityVO cv);
	public boolean salesChange(CommodityVO cv);
	
}
