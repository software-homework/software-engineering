package businesslogic.stockManage;

import java.rmi.RemoteException;
import java.util.ArrayList;

import businesslogic.salesman.SetMessage;
import businesslogicservice.stockManage.MessageBLService;
import data.stockManage.MessageData;
import dataservice.stockManage.MessageDataService;
import po.stockManage.MessagePO;
import vo.stockManage.MessageVO;

public class MessageBL implements MessageBLService,SetMessage{
    private MessageDataService sdds;
    
    public MessageBL(){
    	try {
			sdds = MessageData.getInstance();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Override
	public boolean add(MessageVO sdv) {
		// TODO Auto-generated method stub
		if(sdv == null){
			return false;
		}
		
		if(sdds.add(senddangerVoToPo(sdv))){
			return true;
		}
		return false;
	}

	@Override
	public void update(MessageVO mv) {
		// TODO Auto-generated method stub
		
		sdds.update(senddangerVoToPo(mv));
	}
	
	public MessagePO senddangerVoToPo(MessageVO sdv){
		if(sdv == null){
			return null;
		}
		MessagePO sdp = new MessagePO();
		sdp.setType(sdv.type);
		sdp.setName(sdv.name);
		sdp.setModel(sdv.model);
		sdp.setNumber(sdv.number);
		sdp.setCustomer(sdv.customer);
		sdp.setIsRead(sdv.isRead);
		sdp.setTime(sdv.time);
		return sdp;
	}
	
	public MessageVO senddangerPoToVo(MessagePO sdp){
		if(sdp == null){
			return null;
		}
		MessageVO sdv = new MessageVO();
		sdv.type = sdp.getType();
		sdv.name = sdp.getName();
		sdv.model = sdp.getModel();
		sdv.number = sdp.getNumber();
		sdv.customer = sdp.getCustomer();
		sdv.isRead = sdp.getIsRead();
		sdv.time = sdp.getTime();
		return sdv;
	}
	
	public ArrayList<MessageVO> showUnRead(){
		ArrayList<MessageVO> list = new ArrayList<MessageVO>();
		for(MessagePO sdp : sdds.getSendDanger()){
			if(sdp.getIsRead() == false){
				list.add(senddangerPoToVo(sdp));
			}
		}
		return list;
	}
}
