package businesslogicservice.logistics;

import java.util.ArrayList;

import vo.logistics.MessageVO;

public interface MessageBLService {
	public boolean add(MessageVO sdv);
	
	public void update(MessageVO message);
	
	public ArrayList<MessageVO> showUnRead();
}
