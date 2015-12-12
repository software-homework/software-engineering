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

import po.stockManage.PresentPO;
import dataservice.stockManage.PresentDataService;

public class PresentData extends UnicastRemoteObject implements PresentDataService {
	private static final long serialVersionUID = -8868676826752697887L;
	private static ArrayList<PresentPO> presentList;
	private static PresentData pd = null;
	
	private PresentData()throws RemoteException{
		presentList = new ArrayList<PresentPO>();
		readFromFile();
	}
	
	public static PresentData getInstance()throws RemoteException{
		if(pd == null){
			pd = new PresentData();
		}
		return pd;
	}
	
	public boolean add(PresentPO pp){
		if(pp == null){
			return false;
		}
		updateId(pp);
		presentList.add(pp);
		writeToFile();
		return true;
	}
	
	public void update(PresentPO pp){
		for(PresentPO p : getPresent()){
			if(p.getId().equals(pp.getId())){
				presentList.add(presentList.indexOf(p),pp);
				presentList.remove(p);
				writeToFile();
				break;
			}
		}
	}
	
	public ArrayList<PresentPO> getPresent(){
		return presentList;
	}
	
	public void write(PresentPO pp,PrintWriter pw){
		pw.println("<presentstart>:");
		pw.println("<presentid>:" + pp.getId());
		pw.println("<presentlist>:");
		for(int i = 0;i < pp.getPresentList().size();i ++){
			int index1 = pp.getPresentList().get(i).indexOf(";");
			String str = pp.getPresentList().get(i).substring(index1 + 1);
			int index2 = str.indexOf(";");
			pw.println("<commoditystart>:");
			pw.println("<commodityname>:" + pp.getPresentList().get(i).substring(0, index1));
			pw.println("<commoditymodel>:" + str.substring(0, index2));
			pw.println("<commoditynumber>:"+ str.substring(index2 + 1));
			pw.println("<commodityend>");
		}
		pw.println("<presentapproval>:" + pp.getApproval());
		pw.println("<presenttime>:" + pp.getTime());
		pw.println("<presentend>");
	}
	
	public void readFromFile(){
		try{
			File file=new File("present.txt");
			presentList.clear();
	        if(!file.exists()){
	        	file.createNewFile();
	        	return;
	        }
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
	        String str1 = null;
	        PresentPO pp = null;
	        String present = null;
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
	        	if(str2.equals("presentstart")){
	        		pp = new PresentPO();
	        	}
	        	else if(str2.equals("presentend")){
	        		presentList.add(pp);
	        	}
	        	else if(str2.equals("presentid")){
	        		pp.setId(new String(str3));
	        	}
	        	else if(str2.equals("presentlist")){
	        		if(pp != null){
	        			pp.setPresentList(new ArrayList<String>());
	        		}
	        	}
	        	else if(str2.equals("commoditystart")){
	        		if(pp.getPresentList() != null){
	        			present = new String();
	        		}
	        	}
	        	else if(str2.equals("commodityname")){
	        		present = new String(str3);
	        	}
	        	else if(str2.equals("commoditymodel")){
	        		present = present + ";" + new String(str3);
	        	}
	        	else if(str2.equals("commoditynumber")){
	        		present = present + ";" + new String(str3);
	        	}
	        	else if(str2.equals("commodityend")){
	        		pp.getPresentList().add(present);
	        		present = null;
	        	}
	        	else if(str2.equals("presentapproval")){
	        		pp.setApproval(Integer.parseInt(str3));
	        	}
	        	else if(str2.equals("presenttime")){
	        		pp.setTime(new String(str3));
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
			File file = new File("present.txt");
	        if(!file.exists()){
	        	file.createNewFile();
	        	presentList.clear();
	        	return;
	        }
	        BufferedWriter bw = null;
			
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			PrintWriter pw = new PrintWriter(bw);
			
			for(PresentPO pp : presentList){
				write(pp, pw);
			}
	        pw.close();  
	        
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public void updateId(PresentPO pp){
		if(getPresent().size() == 0){
			pp.setId("ZSD-" + pp.getTime() + "-00000");
		}
		else{
			pp.setId("ZSD-" + pp.getTime() + "-" + String.format("%05d",getPresent().size()));
		}
	}
}
