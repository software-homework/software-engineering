package data.stockManage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import po.stockManage.MessagePO;
import dataservice.stockManage.MessageDataService;

public class MessageData extends UnicastRemoteObject implements MessageDataService{
	private static final long serialVersionUID = -8868676826752697887L;
    private static ArrayList<MessagePO> messageList;
    private static MessageData sdd = null;
    
    private MessageData()throws RemoteException{
    	messageList = new ArrayList<MessagePO>();
    	readFromFile();
    }
    
    public static MessageData getInstance()throws RemoteException{
    	if(sdd == null){
    		sdd = new MessageData();
    	}
    	return sdd;
    }

	public boolean add(MessagePO sdp) {
		// TODO Auto-generated method stub
		if(sdp == null){
			return false;
		}
		messageList.add(sdp);
		writeToFile();
		return true;
	}

	@Override
	public void update(MessagePO mp) {
		// TODO Auto-generated method stub
		for(MessagePO sdp : messageList){
			if(sdp.getType().equals(mp.getType()) && sdp.getTime().equals(mp.getTime()) && sdp.getCustomer().equals(mp.getCustomer()) && sdp.getName().equals(mp.getName()) && sdp.getModel().equals(mp.getModel()) && sdp.getNumber() == mp.getNumber()){
/*				messageList.add(messageList.indexOf(sdp),mp);
				messageList.remove(sdp);*/
				sdp.setIsRead(true);
				writeToFile();
				break;
			}
		}
	}
	
	public ArrayList<MessagePO> getSendDanger(){
		return messageList;
	}
	
	public void write(MessagePO sdp,PrintWriter pw){
		pw.println("<messagestart>:");
		pw.println("<messagetype>:" + sdp.getType());
		pw.println("<commodityname>:" + sdp.getName());
		pw.println("<commoditymodel>:" + sdp.getModel());
		pw.println("<commoditynumber>:" + sdp.getNumber());
		pw.println("<messagecustomer>:" + sdp.getCustomer());
		pw.println("<commodityisread>:" + sdp.getIsRead());
		pw.println("<messagetime>:" + sdp.getTime());
		pw.println("<messageend>");
	}
	
	public void readFromFile(){
		try{
			File file=new File("message.txt");
			messageList.clear();
	        if(!file.exists()){
	        	file.createNewFile();
	        	return;
	        }
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
	        String str1 = null;
	        MessagePO sdp = null;
	        str1 = br.readLine();
	        while(str1 != null){
	        	int i1 = str1.indexOf("<");
	        	int i2 = str1.indexOf(">");
	        	if(i1 < 0 || i2 < 0){
	        		break;
	        	}
	        	String str2 = str1.substring(i1 + 1, i2);
	        	String str3 = null;
	        	if(str1.length() > i2 + 2){
	        		str3 = str1.substring(i2 + 2);
	        	}
	        	if(str2.equals("messagestart")){
	        		sdp = new MessagePO();
	        	}
	        	else if(str2.equals("messageend")){
	        		messageList.add(sdp);
	        	}
	        	else if(str2.equals("messagetype")){
	        		if(sdp != null){
	        			sdp.setType(new String(str3));
	        		}
	        	}
	        	else if(str2.equals("commodityname")){
	        		if(sdp != null){
	        			sdp.setName(new String(str3));
	        		}
	        	}
	        	else if(str2.equals("commoditymodel")){
	        		sdp.setModel(new String(str3));
	        	}
	        	else if(str2.equals("commoditynumber")){
	        		sdp.setNumber(Integer.parseInt(new String(str3)));
	        	}
	        	else if(str2.equals("messagecustomer")){
	        		sdp.setCustomer(new String(str3));
	        	}
	        	else if(str2.equals("commodityisread")){
	        		if(str3.equals("true")){
	        			sdp.setIsRead(true);
	        		}
	        		else{
	        			sdp.setIsRead(false);
	        		}
	        	}
	        	else if(str2.equals("messagetime")){
	        		sdp.setTime(new String(str3));
	        	}
	        	str1 = br.readLine();
	        }
	        br.close();
		}
		catch (IOException e){
	    	e.printStackTrace();
	    }
	}
	
	public void writeToFile(){
		try{
			File file = new File("message.txt");
	        if(!file.exists()){
	        	file.createNewFile();
	        	messageList.clear();
	        	return;
	        }
	        BufferedWriter bw = null;
			
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			PrintWriter pw = new PrintWriter(bw);
			
			for(MessagePO sdp : messageList){
				write(sdp, pw);
			}
	        pw.close();  
	        
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}
