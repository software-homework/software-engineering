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

import dataservice.stockManage.StockLossDataService;
import po.stockManage.StockLossPO;

public class StockLossData extends UnicastRemoteObject implements StockLossDataService{
	private static final long serialVersionUID = -8868676826752697887L;
	private static ArrayList<StockLossPO> stocklossList;
	private static StockLossData sld = null;
	
	private StockLossData()throws RemoteException{
		stocklossList = new ArrayList<StockLossPO>();
		readFromFile();
	}
	
	public ArrayList<StockLossPO> getStockLoss(){
		return stocklossList;
	}
	
	public static StockLossData getInstance()throws RemoteException{
		if(sld == null){
			sld = new StockLossData();
		}
		return sld;
	}
	
	public boolean add(StockLossPO slp){
		if(slp == null){
			return false;
		}
		updateId(slp);
		stocklossList.add(slp);
		writeToFile();
		return true;
	}
	
	public boolean setApproval(StockLossPO slp,int approval){
		if(slp == null){
			return false;
		}
		if(approval < 1 || approval > 3){
			return false;
		}
		
		slp.setApproval(approval);
		return true;
	}
	
	public void update(StockLossPO slp){
		for(StockLossPO s : getStockLoss()){
			if(s.getId().equals(slp.getId())){
				stocklossList.add(stocklossList.indexOf(s),slp);
				stocklossList.remove(s);
				writeToFile();
				break;
			}
		}
	}
	
	public void write(StockLossPO slp,PrintWriter pw){
		pw.println("<StockLossstart>:");
		pw.println("<StockLossid>:" + slp.getId());
		pw.println("<StockLossname>:" + slp.getName());
		pw.println("<StockLossmodel>:" + slp.getModel());
		pw.println("<StockLosslossnumber>:" + slp.getlossNumber());
		pw.println("<StockLossApproval>:" + slp.getApproval());
		pw.println("<StockLossTime>:"+ slp.getTime());
		pw.println("<StockLossend>");
	}
	
	public void readFromFile(){
		try{
			File file=new File("stockLoss.txt");
			stocklossList.clear();
	        if(!file.exists()){
	        	file.createNewFile();
	        	return;
	        }
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
	        String str1 = null;
	        StockLossPO slp = null;
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
	        	if(str2.equals("StockLossstart")){
	        		slp = new StockLossPO();
	        	}
	        	else if(str2.equals("StockLossend")){
	        		stocklossList.add(slp);
	        	}
	        	else if(str2.equals("StockLossid")){
	        		if(slp != null){
	        			slp.setId(new String(str3));
	        		}
	        	}
	        	else if(str2.equals("StockLossname")){
	        		if(slp != null){
	        			slp.setName(new String(str3));
	        		}
	        	}
	        	else if(str2.equals("StockLossmodel")){
	        		slp.setModel(new String(str3));
	        	}
	        	else if(str2.equals("StockLosslossnumber")){
	        		slp.setlossNumber(Integer.parseInt(str3));
	        	}
	        	else if(str2.equals("StockLossApproval")){
	        		if(slp != null){
	        			slp.setApproval(Integer.parseInt(str3));
	        		}
	        	}
	        	else if(str2.equals("StockLossTime")){
	        		slp.setTime(new String(str3));
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
			File file = new File("stockLoss.txt");
	        if(!file.exists()){
	        	file.createNewFile();
	        	stocklossList.clear();
	        	return;
	        }
	        BufferedWriter bw = null;
			
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			PrintWriter pw = new PrintWriter(bw);
			
			for(StockLossPO slp : stocklossList){
				write(slp, pw);
			}
	        pw.close();  
	        
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public void updateId(StockLossPO slp){
		if(getStockLoss().size() == 0){
			slp.setId("BSD-" + slp.getTime() + "-" + "00000");
		}
		else{
			slp.setId("BSD-" + slp.getTime() + "-" + String.format("%05d",getStockLoss().size()));
		}
	}

}
