package data.logistics;

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

import dataservice.logistics.LogisticsOverflowDataService;
import po.logistics.LogisticsOverflowPO;

public class LogisticsOverflowData extends UnicastRemoteObject implements LogisticsOverflowDataService{
	private static final long serialVersionUID = -8868676826752697887L;
	private static ArrayList<LogisticsOverflowPO> stockoverflowList;
	private static LogisticsOverflowData sofd = null;
	
	private LogisticsOverflowData()throws RemoteException{
		stockoverflowList = new ArrayList<LogisticsOverflowPO>();
		readFromFile();
	}
	
	public ArrayList<LogisticsOverflowPO> getStockOverflow(){
		return stockoverflowList;
	}
	
	public static LogisticsOverflowData getInstance()throws RemoteException{
		if(sofd == null){
			sofd = new LogisticsOverflowData();
		}
		return sofd;
	}
	
	public boolean add(LogisticsOverflowPO sofp){
		if(sofp == null){
			return false;
		}
		updateId(sofp);
		stockoverflowList.add(sofp);
		writeToFile();
		return true;
	}
	
	public boolean setApproval(LogisticsOverflowPO sofp,int approval){
		if(sofp == null){
			return false;
		}
		if(approval < 1 || approval > 3){
			return false;
		}
		
		sofp.setApproval(approval);
		return true;
	}
	
	public void update(LogisticsOverflowPO sofp){
		for(LogisticsOverflowPO sp : getStockOverflow()){
			if(sp.getId().equals(sofp.getId())){
				stockoverflowList.add(stockoverflowList.indexOf(sp),sofp);
				stockoverflowList.remove(sp);
				writeToFile();
				break;
			}
		}
	}
	
	public void write(LogisticsOverflowPO sofp,PrintWriter pw){
		pw.println("<StockOverflowstart>:");
		pw.println("<StockOverflowid>:"+ sofp.getId());
		pw.println("<StockOverflowname>:" + sofp.getName());
		pw.println("<StockOverflowmodel>:" + sofp.getModel());
		pw.println("<StockOverflowovernumber>:" + sofp.getOverNumber());
		pw.println("<StockOverflowApproval>:" + sofp.getApproval());
		pw.println("<StockOverflowTime>:" + sofp.getTime());
		pw.println("<StockOverflowend>");
	}
	
	public void readFromFile(){
		try{
			File file=new File("stockOverflow.txt");
			stockoverflowList.clear();
	        if(!file.exists()){
	        	file.createNewFile();
	        	return;
	        }
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
	        String str1 = null;
	        LogisticsOverflowPO sofp = null;
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
	        	if(str2.equals("StockOverflowstart")){
	        		sofp = new LogisticsOverflowPO();
	        	}
	        	else if(str2.equals("StockOverflowend")){
	        		stockoverflowList.add(sofp);
	        	}
	        	else if(str2.equals("StockOverflowid")){
	        		if(sofp != null){
	        			sofp.setId(new String(str3));
	        		}
	        	}
	        	else if(str2.equals("StockOverflowname")){
	        		if(sofp != null){
	        			sofp.setName(new String(str3));
	        		}
	        	}
	        	else if(str2.equals("StockOverflowmodel")){
	        		sofp.setModel(new String(str3));
	        	}
	        	else if(str2.equals("StockOverflowovernumber")){
	        		sofp.setOverNumber(Integer.parseInt(str3));
	        	}
	        	else if(str2.equals("StockOverflowApproval")){
	        		if(sofp != null){
	        			sofp.setApproval(Integer.parseInt(str3));
	        		}
	        	}
	        	else if(str2.equals("StockOverflowTime")){
	        		sofp.setTime(new String(str3));
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
			File file = new File("stockOverflow.txt");
	        if(!file.exists()){
	        	file.createNewFile();
	        	stockoverflowList.clear();
	        	return;
	        }
	        BufferedWriter bw = null;
			
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			PrintWriter pw = new PrintWriter(bw);
			
			for(LogisticsOverflowPO sofp : stockoverflowList){
				write(sofp, pw);
			}
	        pw.close();  
	        
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public void updateId(LogisticsOverflowPO sofp){
		if(getStockOverflow().size() == 0){
			sofp.setId("BYD-" + sofp.getTime() + "-" + "00000");
		}
		else{
			sofp.setId("BYD-" + sofp.getTime() + "-" + String.format("%05d",getStockOverflow().size()));
		}
	}
	
}
