package businesslogic.courier;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import businesslogic.logistics.CommodityBL;
import businesslogic.logistics.MessageBL;
import businesslogicservice.courier.CustomerBLService;
import businesslogicservice.courier.CourierBLService;
import businesslogicservice.finance.GetSalesBL;
import data.courier.CourierData;
import dataservice.courier.CourierDataService;
import po.courier.CourierPO;
import po.logistics.CommodityPO;
import vo.courier.CourierVO;
import vo.logistics.CommodityVO;
import vo.logistics.MessageVO;

public class CourierBL implements CourierBLService,businesslogic.logistics.GetSales,businesslogicservice.manager.GetSales,GetSalesBL{
	private CourierDataService data = null;
	GetCommodity gc=new CommodityBL();
	SetMessage sm=new MessageBL();
	public CourierBL(){
		try {
			data = CourierData.GetInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getsID() {
		ArrayList<CourierPO> list=new ArrayList<CourierPO>();
		list=data.getDataS();
		Date dt=new Date();
	    SimpleDateFormat matter1=new SimpleDateFormat("yyyyMMdd");
	    int count=0;
	    for(CourierPO spo:list){
	    	if(spo.getID().substring(0, 3).equals("XSD")&&spo.getID().substring(4, 12).equals(matter1.format(dt))){
	    		count++;
	    	}
	    }
	    if(count>=99999){
	    	return null;
	    }
	    else{
	    	return "XSD-"+matter1.format(dt)+"-"+String.format("%05d",count);
	    }
	}
	
	@Override
	public String getsrID() {
		ArrayList<CourierPO> list=new ArrayList<CourierPO>();
		list=data.getDataS();
		Date dt=new Date();
	    SimpleDateFormat matter1=new SimpleDateFormat("yyyyMMdd");
	    int count=0;
	    for(CourierPO spo:list){
	    	if(spo.getID().substring(0, 5).equals("XSTHD")&&spo.getID().substring(6, 14).equals(matter1.format(dt))){
	    		count++;
	    	}
	    }
	    if(count>=99999){
	    	return null;
	    }
	    else{
	    	return "XSTHD-"+matter1.format(dt)+"-"+String.format("%05d",count);
	    }
	}


	@Override
	public boolean CheckS(CourierVO svo) {
		ArrayList<CommodityPO> cpoList=new ArrayList<CommodityPO>();
		for(CommodityVO cvo:svo.salesList){
			CommodityPO cpo=new CommodityPO();
			gc.commodityVoToPo(cvo, cpo);
			cpoList.add(cpo);
		}
		CourierPO SPO=new CourierPO(svo.id,svo.customer,svo.repertory,svo.user,cpoList,svo.pretotal,svo.discount,svo.Voucher,svo.posttotal,svo.remakes,3);
		return data.insert(SPO);
	}
	
	public CourierVO findSales(String id){
		ArrayList<CourierPO> list=new ArrayList<CourierPO>();
		list=data.getDataS();
		for(CourierPO spo:list){
			if(spo.getID().equals(id)){
				return this.SaleslPOtoVO(spo);
			}
		}
		return null;
	}

	public void updateSales(CourierVO svo){
		ArrayList<CommodityPO> cpolist=new ArrayList<CommodityPO>();
		for(CommodityVO cvo:svo.salesList){
			CommodityPO cpo=new CommodityPO();
			gc.commodityVoToPo(cvo, cpo);
			cpolist.add(cpo);
		}
		CourierPO SPO=new CourierPO(svo.id,svo.customer,svo.repertory,svo.user,cpolist,svo.pretotal,svo.discount,svo.Voucher,svo.posttotal,svo.remakes,svo.approval);
		data.update(SPO);
	}
	
	public ArrayList<CourierVO> getUncheckedSales(){
		ArrayList<CourierPO> list = new ArrayList<CourierPO>();
		list=data.getDataS();
		ArrayList<CourierVO> newList=new ArrayList<CourierVO>();
		for(CourierPO spo:list){
			if(spo.getApproval()==3){
				newList.add(this.SaleslPOtoVO(spo));
			}
		}
		return newList;
	}
	
	public ArrayList<CourierVO> getCheckedSales(){
		ArrayList<CourierPO> list = new ArrayList<CourierPO>();
		list=data.getDataS();
		ArrayList<CourierVO> newList=new ArrayList<CourierVO>();
		for(CourierPO spo:list){
			if(spo.getApproval()==1){
				newList.add(this.SaleslPOtoVO(spo));
			}
		}
		return newList;
	}
	
	@Override
	public ArrayList<CourierVO> ShowS() {
		ArrayList<CourierPO> list = new ArrayList<CourierPO>();
		list=data.getDataS();
		ArrayList<CourierVO> newList=new ArrayList<CourierVO>();
		for(CourierPO spo:list){
			newList.add(this.SaleslPOtoVO(spo));
		}
		return newList;
	}
	
	public boolean approvalSales(String id,int approval){
		ArrayList<CourierPO> list = new ArrayList<CourierPO>();
		list=data.getDataS();
		CourierVO salesvo=new CourierVO();
		for(CourierPO spo:list){
			if(spo.getID().equals(id)){
				spo.setApproval(approval);
				data.update(spo);
				salesvo=this.SaleslPOtoVO(spo);
				break;
			}
		}
		if(salesvo.approval==1){
			CustomerBLService cbl=new CustomerBL();
			if(salesvo.id.substring(0, 3).equals("XSD")){
				cbl.updateCustomer(salesvo.customer, salesvo.posttotal,0 );
				for(CommodityVO cvo:salesvo.salesList){
					cvo.number=-cvo.number;
					if(gc.salesChange(cvo)==false){
						for(CourierPO spo:list){
							if(spo.getID().equals(id)){
								spo.setApproval(2);
								data.update(spo);
								break;
							}
						}
						return false;
					}
					else{
						sm.add(new MessageVO("销售",cvo.commodityname,cvo.model,-cvo.number,salesvo.customer,false));
					}
				}
			}
			else{
				cbl.updateCustomer(salesvo.customer, -salesvo.posttotal,0 );
				for(CommodityVO cvo:salesvo.salesList){
					if(gc.salesChange(cvo)==false){
						for(CourierPO spo:list){
							if(spo.getID().equals(id)){
								spo.setApproval(2);
								data.update(spo);
								break;
							}
						}
						return false;
					}
					else{
						sm.add(new MessageVO("销售退货",cvo.commodityname,cvo.model,cvo.number,salesvo.customer,false));
					}
				}
			}
		}
		return true;
	}
	
	private CourierVO SaleslPOtoVO(CourierPO spo){
		ArrayList<CommodityVO> cvoList=new ArrayList<CommodityVO>();
		for(CommodityPO cpo:spo.getSalesList()){
			cvoList.add(gc.commodityPoToVo(cpo));
		}
		CourierVO newSvo=new CourierVO();
		newSvo.id=spo.getID();
		newSvo.approval=spo.getApproval();
		newSvo.salesList=cvoList;
		newSvo.customer=spo.getCustomer();
		newSvo.discount=spo.getDiscount();
		newSvo.posttotal=spo.getPosttotal();
		newSvo.pretotal=spo.getPretotal();
		newSvo.remakes=spo.getRemakes();
		newSvo.user=spo.getUser();
		newSvo.repertory=spo.getRepertory();
		newSvo.Voucher=spo.getVoucher();
		return newSvo;
	}
	

}
