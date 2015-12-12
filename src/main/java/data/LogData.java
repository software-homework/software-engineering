package data;

import io.Input;
import io.Output;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import po.LogPO;
import dataservice.LogDataService;

public class LogData extends UnicastRemoteObject implements LogDataService{
	private static final long serialVersionUID = -8868676826752697887L;
	private static LogData logdata;
	private static ArrayList<LogPO> loglist;
	private static ArrayList<Object> templist;
	String path;
	
	private LogData()throws RemoteException{
		loglist=new ArrayList<LogPO>();
		templist=new ArrayList<Object>();
		path="log.txt";
		Input input = new Input();
		templist=input.input(path);
		objectToPO(templist,loglist);
	}
	
	public static LogData GetInstance()throws RemoteException{
		if(logdata==null){
			logdata=new LogData();
		}
		return logdata;
	}

	public void addLog(LogPO logpo) {
		loglist.add(logpo);
		new Output(POToObject(loglist),path);
	}
	public ArrayList<LogPO> getAllLog() {
		return loglist;
	}
	
	private void objectToPO(ArrayList<Object> olist,ArrayList<LogPO> lpolist){
		for(Object object:olist){
			lpolist.add((LogPO)object);
		}
	}
	
	private ArrayList<Object> POToObject(ArrayList<LogPO> lpolist){
		ArrayList<Object> objectlist=new ArrayList<Object>();
		for(LogPO lpo:lpolist){
			Object object=lpo;
			objectlist.add(object);
		}
		return objectlist;
	}

}
