package data.courier;

import io.Input;
import io.Output;
import po.courier.CustomerPO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import dataservice.courier.CustomerDataService;

public class CustomerData extends UnicastRemoteObject implements CustomerDataService{
	private static final long serialVersionUID = -8868676826752697887L;
	private static CustomerData customerdata;
	private static ArrayList<CustomerPO> customerList;
    private static ArrayList<Object> templist;
    String path;
	private CustomerData()throws RemoteException{
		customerList=new ArrayList<CustomerPO>();
		templist=new ArrayList<Object>();
		path="customer.txt";
        Input input = new Input();
        templist=input.input(path);
        objectToPO(templist,customerList);
	}
	public static CustomerData GetInstance()throws RemoteException{
		if(customerdata==null){
			customerdata=new CustomerData();
		}
		return customerdata;
	}
    
	public CustomerPO find(String name) {
		for(CustomerPO cpo:customerList){
			if(cpo.getName().equals(name)){
				return cpo;
			}
		}
		return null;
	
	}

	public boolean insert(CustomerPO po) {
		boolean j=customerList.add(po);
		new Output(POToObject(customerList), path);
		return j;
		
	}

	public boolean delete(CustomerPO po) {
		for(CustomerPO cpo:customerList){
			if(cpo.getName().equals(po.getName())){
				customerList.remove(cpo);
				new Output(POToObject(customerList), path);
				return true;
			}
		}
		return false;
	}

	public boolean update(CustomerPO po) {
		for(CustomerPO cpo:customerList){
			if(cpo.getName().equals(po.getName())){
				customerList.remove(cpo);
				customerList.add(po);
				new Output(POToObject(customerList), path);
				return true;
			}
		}
		return false;
	}

	public ArrayList<CustomerPO> getData() {
		return customerList;
	}
	
	private void objectToPO(ArrayList<Object> olist,ArrayList<CustomerPO> cpolist){
		for(Object object:olist){
			cpolist.add((CustomerPO)object);
		}
	}
	
	private ArrayList<Object> POToObject(ArrayList<CustomerPO> cpolist){
		ArrayList<Object> objectlist=new ArrayList<Object>();
		for(CustomerPO cpo:cpolist){
			Object object=cpo;
			objectlist.add(object);
		}
		return objectlist;
	}

}
