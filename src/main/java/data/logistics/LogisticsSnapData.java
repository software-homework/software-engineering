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

import dataservice.logistics.LogisticsSnapDataService;
import po.logistics.LogisticsSnapPO;

public class LogisticsSnapData extends UnicastRemoteObject implements LogisticsSnapDataService{
	private static final long serialVersionUID = -8868676826752697887L;
	private static ArrayList<LogisticsSnapPO> stocksnapList;
	private static LogisticsSnapData ssd = null;
	
	private LogisticsSnapData()throws RemoteException{
		stocksnapList = new ArrayList<LogisticsSnapPO>();
		readFromFile();
	}
	
	public static LogisticsSnapData getInstance()throws RemoteException{
		if(ssd == null){
			ssd = new LogisticsSnapData();
		}
		return ssd;
	}
	
	public boolean add(LogisticsSnapPO ssp){
		if(ssp == null){
			return false;
		}
		stocksnapList.add(ssp);
		writeToFile();
		return true;
	}
	
	public void remove(){
		stocksnapList = new ArrayList<LogisticsSnapPO>();
	}
	
	public ArrayList<LogisticsSnapPO> getStocksnap(){
		return stocksnapList;
	}
	
	public LogisticsSnapPO getLast(){
		return stocksnapList.get(stocksnapList.size() - 1);
	}
	
	public void write(LogisticsSnapPO ssp,PrintWriter pw){
		pw.println("<stocksnapstart>:");
		pw.println("<stocksnapname>:" + ssp.getName());
		pw.println("<stocksnapmodel>:" + ssp.getModel());
		pw.println("<stocksnapnumber>:" + ssp.getNumber());
		pw.println("<stocksnapaverage>:" + ssp.getAverage());
		pw.println("<stocksnapbatch>:" + ssp.getBatch());
		pw.println("<stocksnapbatchnumber>:" + ssp.getBatchNumber());
		pw.println("<stocksnapouttime>:" + ssp.getTime());
		pw.println("<stocksnaprow>:" + ssp.getRow());
		pw.println("<stocksnapend>");
	}
	
	public void readFromFile(){
		try{
			File file=new File("stocksnap.txt");
			stocksnapList.clear();
	        if(!file.exists()){
	        	file.createNewFile();
	        	return;
	        }
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
	        String str1 = null;
	        LogisticsSnapPO ssp = null;
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
	        	if(str2.equals("stocksnapstart")){
	        		ssp = new LogisticsSnapPO();
	        	}
	        	else if(str2.equals("stocksnapend")){
	        		stocksnapList.add(ssp);
	        	}
	        	else if(str2.equals("stocksnapname")){
	        		if(ssp != null){
	        			ssp.setName(new String(str3));
	        		}
	        	}
	        	else if(str2.equals("stocksnapmodel")){
	        		if(ssp != null){
	        			ssp.setModel(new String(str3));
	        		}
	        	}
	        	else if(str2.equals("stocksnapnumber")){
	        		ssp.setNumber(Integer.parseInt(new String(str3)));
	        	}
	        	else if(str2.equals("stocksnapaverage")){
	        		ssp.setAverage(Double.parseDouble(new String(str3)));
	        	}
	        	else if(str2.equals("stocksnapbatch")){
	        		ssp.setBatch(new String(str3));
	        	}
	        	else if(str2.equals("stocksnapbatchnumber")){
	        		ssp.setBatchNumber(new String(str3));
	        	}
	        	else if(str2.equals("stocksnapouttime")){
	        		ssp.setTime(new String(str3));
	        	}
	        	else if(str2.equals("stocksnaprow")){
	        		ssp.setRow(Integer.parseInt(new String(str3)));
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
			File file = new File("stocksnap.txt");
	        if(!file.exists()){
	        	file.createNewFile();
	        	stocksnapList.clear();
	        	return;
	        }
	        BufferedWriter bw = null;
			
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			PrintWriter pw = new PrintWriter(bw);
			
			for(LogisticsSnapPO ssp : stocksnapList){
				write(ssp, pw);
			}
	        pw.close();  
	        
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}
