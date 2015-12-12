package businesslogic.courier;

import java.util.ArrayList;

import businesslogicservice.courier.CustomerBLService;
import businesslogicservice.finance.GetCustomerBL;
import data.courier.CustomerData;
import dataservice.courier.CustomerDataService;
import po.courier.CustomerPO;
import vo.courier.CustomerVO;

public class CustomerBL implements CustomerBLService,GetCustomerBL{
	private CustomerDataService data = null;
	
	//op
	
	public CustomerBL(){
		try {
			data = CustomerData.GetInstance();
		} catch (Exception e) {
			e.printStackTrace();
	    }
	}

	@Override
	public int getID() {
		ArrayList<CustomerPO> list = new ArrayList<CustomerPO>();
		list=data.getData();
		int id=1;
		loop:for(id=1;id<=9999;id++){
			boolean judge=true;
			for(CustomerPO cpo:list){
				if(cpo.getID()==id){
					judge=false;
				}
			}
			if(judge){
				break loop;
			}
		}
		return id;
	}

	@Override
	public boolean AddC(CustomerVO customer) {
		ArrayList<CustomerPO> list = new ArrayList<CustomerPO>();
		list=data.getData();
		boolean judge=true;
		for(CustomerPO cpo:list){
			if(cpo.getName().equals(customer.name)){
				judge=false;
			}
		}
		if(judge){
			CustomerPO newCPO=this.CustomerVOtoPO(customer);
			data.insert(newCPO);
		}
		return judge;
	}

	@Override
	public boolean DeleteC(CustomerVO customer) {
		boolean judge=true;
		CustomerPO cpo=data.find(customer.name);
		if((cpo.getPay()-cpo.getProceed())==0){
			data.delete(cpo);
		}	
		else{
			judge=false;
		}
		return judge;
	}

	@Override
	public boolean UpdateC(CustomerVO customer) {
		CustomerPO cpo=data.find(customer.name);
		cpo.setLevel(customer.level);
		cpo.setAddress(customer.address);
		cpo.setPhone(customer.phone);
		cpo.setLimit(customer.limit);
		cpo.setPostcode(customer.postcode);
		cpo.setUser(customer.user);
		return data.update(cpo);
	}

	@Override
	public ArrayList<CustomerVO> ShowC() {
		ArrayList<CustomerPO> list = new ArrayList<CustomerPO>();
		list=data.getData();
		ArrayList<CustomerVO> newList=new ArrayList<CustomerVO>();
		for(CustomerPO CPO:list){
			newList.add(this.CustomerPOtoVO(CPO));
		}
		return newList;
	}

	@Override
	public ArrayList<CustomerVO> FinC(CustomerVO customer) {
		ArrayList<CustomerPO> list = new ArrayList<CustomerPO>();
		ArrayList<CustomerVO> newList=new ArrayList<CustomerVO>();
		list=data.getData();
		for(CustomerPO CPO:list){
			if(String.valueOf(CPO.getID()).equals(customer.name)){
				newList.add(this.CustomerPOtoVO(CPO));
			}
			else if(CPO.getName().indexOf(customer.name)!=-1){
				newList.add(this.CustomerPOtoVO(CPO));
			}
		}
		
		return newList;
	}
	
	public boolean updateCustomer(String name,double newProceed,double newPay ){
		CustomerPO cpo=data.find(name);
		cpo.setPay(cpo.getPay()+newPay);
		cpo.setProceed(cpo.getProceed()+newProceed);
		return data.update(cpo);		
	}
	
	public CustomerVO findSingleCustomer(String name){
		if(data.find(name).equals(null)){
			return null;
		}
		else{
			CustomerPO cpo=data.find(name);
			return CustomerPOtoVO(cpo);
		}
		
		
	}
	
	private CustomerPO CustomerVOtoPO(CustomerVO CVO){
		CustomerPO newCPO;
		newCPO=new CustomerPO(CVO.id,CVO.type,CVO.level,CVO.name,CVO.phone,CVO.email,CVO.address,CVO.postcode,CVO.limit,CVO.proceed,CVO.pay,CVO.user);
	    return newCPO;
	}
	
	private CustomerVO CustomerPOtoVO(CustomerPO CPO){
		CustomerVO newCVO=new CustomerVO();
		newCVO.id=CPO.getID();
		newCVO.level=CPO.getLevel();
		newCVO.type=CPO.getType();
		newCVO.name=CPO.getName();
		newCVO.phone=CPO.getPhone();
		newCVO.address=CPO.getAddress();
		newCVO.postcode=CPO.getPostcode();
		newCVO.limit=CPO.getLimit();
		newCVO.proceed=CPO.getProceed();
		newCVO.pay=CPO.getPay();
		newCVO.user=CPO.getUser();
		newCVO.email=CPO.getEmail();
		return newCVO;
	}
	
	

}
