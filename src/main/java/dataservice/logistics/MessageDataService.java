package dataservice.logistics;

import java.util.ArrayList;

import po.logistics.MessagePO;

public interface MessageDataService {
	public boolean add(MessagePO sdp);
	
	public void update(MessagePO mp);
	
	public ArrayList<MessagePO> getSendDanger();
}
