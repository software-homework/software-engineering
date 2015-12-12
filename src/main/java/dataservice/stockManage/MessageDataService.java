package dataservice.stockManage;

import java.util.ArrayList;
import po.stockManage.MessagePO;

public interface MessageDataService {
	public boolean add(MessagePO sdp);
	
	public void update(MessagePO mp);
	
	public ArrayList<MessagePO> getSendDanger();
}
