package data.courier;

import io.Input;
import io.Output;
import po.courier.ImportPO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import dataservice.courier.ImportDataService;

public class ImportData extends UnicastRemoteObject implements ImportDataService{
	private static final long serialVersionUID = -8868676826752697887L;
	private static ImportData importdata;
	private static ArrayList<ImportPO> importList;
	private static ArrayList<Object> templist;
	String path;
	private ImportData()throws RemoteException{
		importList=new ArrayList<ImportPO>();
		templist=new ArrayList<Object>();
		path="import.txt";
        Input input = new Input();
        templist=input.input(path);
        objectToPO(templist,importList);
	}
	public static ImportData GetInstance()throws RemoteException{
		if(importdata==null){
			importdata=new ImportData();
		}
		return importdata;
	}
	
	

	@Override
	public boolean insert(ImportPO po) {
		boolean j=importList.add(po);
		new Output(POToObject(importList), path);
		return j;
	}

	@Override
	public ArrayList<ImportPO> getDataI() {
		return importList;
	}
	@Override
	public void update(ImportPO po) {
		for(ImportPO ipo:importList){
			if(ipo.getID().equals(po.getID())){
				importList.remove(ipo);
				importList.add(po);
				new Output(POToObject(importList), path);
				break;
			}
		}
	}
	private void objectToPO(ArrayList<Object> olist,ArrayList<ImportPO> ipolist){
		for(Object object:olist){
			ipolist.add((ImportPO)object);
		}
	}
	private ArrayList<Object> POToObject(ArrayList<ImportPO> ipolist){
		ArrayList<Object> objectlist=new ArrayList<Object>();
		for(ImportPO ipo:ipolist){
			Object object=ipo;
			objectlist.add(object);
		}
		return objectlist;
	}

}
