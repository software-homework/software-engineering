package businesslogicservice.stockManage;

import java.util.ArrayList;
import vo.stockManage.MessageVO;

public interface MessageBLService {
	public boolean add(MessageVO sdv);
	
	public void update(MessageVO message);
	
	public ArrayList<MessageVO> showUnRead();
}
