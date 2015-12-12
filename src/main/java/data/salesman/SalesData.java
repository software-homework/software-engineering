package data.salesman;

import io.Input;
import io.Output;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import po.salesman.SalesPO;
import dataservice.salesman.SalesDataService;

public class SalesData extends UnicastRemoteObject implements SalesDataService{
	private static final long serialVersionUID = -8868676826752697887L;
	private static SalesData salesdata;
	private static ArrayList<SalesPO> salesList;
	private static ArrayList<Object> templist;
	String path;
	private SalesData()throws RemoteException{
		salesList=new ArrayList<SalesPO>();
		templist=new ArrayList<Object>();
		path="sales.txt";
        Input input = new Input();
        templist=input.input(path);
        objectToPO(templist,salesList);
	}
	public static SalesData GetInstance()throws RemoteException{
		if(salesdata==null){
			salesdata=new SalesData();
		}
		return salesdata;
	}
	
	

	@Override
	public boolean insert(SalesPO po) {
		boolean j=salesList.add(po);
		new Output(POToObject(salesList), path);
		return j;
	}

	@Override
	public ArrayList<SalesPO> getDataS() {
		return salesList;
	}
	@Override
	public void update(SalesPO po) {
		for(SalesPO spo:salesList){
			if(spo.getID().equals(po.getID())){
				salesList.remove(spo);
				salesList.add(po);
				new Output(POToObject(salesList), path);
				break;
			}
		}
		
	}
	private void objectToPO(ArrayList<Object> olist,ArrayList<SalesPO> spolist){
		for(Object object:olist){
			spolist.add((SalesPO)object);
		}
	}
	private ArrayList<Object> POToObject(ArrayList<SalesPO> spolist){
		ArrayList<Object> objectlist=new ArrayList<Object>();
		for(SalesPO spo:spolist){
			Object object=spo;
			objectlist.add(object);
		}
		return objectlist;
	}

}
