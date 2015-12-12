package data.courier;

import io.Input;
import io.Output;
import po.courier.CourierPO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import dataservice.courier.CourierDataService;

public class CourierData extends UnicastRemoteObject implements CourierDataService{
	private static final long serialVersionUID = -8868676826752697887L;
	private static CourierData salesdata;
	private static ArrayList<CourierPO> salesList;
	private static ArrayList<Object> templist;
	String path;
	private CourierData()throws RemoteException{
		salesList=new ArrayList<CourierPO>();
		templist=new ArrayList<Object>();
		path="sales.txt";
        Input input = new Input();
        templist=input.input(path);
        objectToPO(templist,salesList);
	}
	public static CourierData GetInstance()throws RemoteException{
		if(salesdata==null){
			salesdata=new CourierData();
		}
		return salesdata;
	}
	
	

	@Override
	public boolean insert(CourierPO po) {
		boolean j=salesList.add(po);
		new Output(POToObject(salesList), path);
		return j;
	}

	@Override
	public ArrayList<CourierPO> getDataS() {
		return salesList;
	}
	@Override
	public void update(CourierPO po) {
		for(CourierPO spo:salesList){
			if(spo.getID().equals(po.getID())){
				salesList.remove(spo);
				salesList.add(po);
				new Output(POToObject(salesList), path);
				break;
			}
		}
		
	}
	private void objectToPO(ArrayList<Object> olist,ArrayList<CourierPO> spolist){
		for(Object object:olist){
			spolist.add((CourierPO)object);
		}
	}
	private ArrayList<Object> POToObject(ArrayList<CourierPO> spolist){
		ArrayList<Object> objectlist=new ArrayList<Object>();
		for(CourierPO spo:spolist){
			Object object=spo;
			objectlist.add(object);
		}
		return objectlist;
	}

}
