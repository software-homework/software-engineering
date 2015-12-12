﻿package businesslogic.salesman;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import businesslogic.stockManage.CommodityBL;
import businesslogic.stockManage.MessageBL;
import businesslogicservice.finance.GetSalesBL;
import businesslogicservice.salesman.CustomerBLService;
import businesslogicservice.salesman.SalesBLService;
import data.salesman.SalesData;
import dataservice.salesman.SalesDataService;
import po.salesman.SalesPO;
import po.stockManage.CommodityPO;
import vo.salesman.SalesVO;
import vo.stockManage.CommodityVO;
import vo.stockManage.MessageVO;

public class SalesBL implements SalesBLService,businesslogic.stockManage.GetSales,businesslogicservice.manager.GetSales,GetSalesBL{
	private SalesDataService data = null;
	GetCommodity gc=new CommodityBL();
	SetMessage sm=new MessageBL();
	public SalesBL(){
		try {
			data = SalesData.GetInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getsID() {
		ArrayList<SalesPO> list=new ArrayList<SalesPO>();
		list=data.getDataS();
		Date dt=new Date();
	    SimpleDateFormat matter1=new SimpleDateFormat("yyyyMMdd");
	    int count=0;
	    for(SalesPO spo:list){
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
		ArrayList<SalesPO> list=new ArrayList<SalesPO>();
		list=data.getDataS();
		Date dt=new Date();
	    SimpleDateFormat matter1=new SimpleDateFormat("yyyyMMdd");
	    int count=0;
	    for(SalesPO spo:list){
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
	public boolean CheckS(SalesVO svo) {
		ArrayList<CommodityPO> cpoList=new ArrayList<CommodityPO>();
		for(CommodityVO cvo:svo.salesList){
			CommodityPO cpo=new CommodityPO();
			gc.commodityVoToPo(cvo, cpo);
			cpoList.add(cpo);
		}
		SalesPO SPO=new SalesPO(svo.id,svo.customer,svo.repertory,svo.user,cpoList,svo.pretotal,svo.discount,svo.Voucher,svo.posttotal,svo.remakes,3);
		return data.insert(SPO);
	}
	
	public SalesVO findSales(String id){
		ArrayList<SalesPO> list=new ArrayList<SalesPO>();
		list=data.getDataS();
		for(SalesPO spo:list){
			if(spo.getID().equals(id)){
				return this.SaleslPOtoVO(spo);
			}
		}
		return null;
	}

	public void updateSales(SalesVO svo){
		ArrayList<CommodityPO> cpolist=new ArrayList<CommodityPO>();
		for(CommodityVO cvo:svo.salesList){
			CommodityPO cpo=new CommodityPO();
			gc.commodityVoToPo(cvo, cpo);
			cpolist.add(cpo);
		}
		SalesPO SPO=new SalesPO(svo.id,svo.customer,svo.repertory,svo.user,cpolist,svo.pretotal,svo.discount,svo.Voucher,svo.posttotal,svo.remakes,svo.approval);
		data.update(SPO);
	}
	
	public ArrayList<SalesVO> getUncheckedSales(){
		ArrayList<SalesPO> list = new ArrayList<SalesPO>();
		list=data.getDataS();
		ArrayList<SalesVO> newList=new ArrayList<SalesVO>();
		for(SalesPO spo:list){
			if(spo.getApproval()==3){
				newList.add(this.SaleslPOtoVO(spo));
			}
		}
		return newList;
	}
	
	public ArrayList<SalesVO> getCheckedSales(){
		ArrayList<SalesPO> list = new ArrayList<SalesPO>();
		list=data.getDataS();
		ArrayList<SalesVO> newList=new ArrayList<SalesVO>();
		for(SalesPO spo:list){
			if(spo.getApproval()==1){
				newList.add(this.SaleslPOtoVO(spo));
			}
		}
		return newList;
	}
	
	@Override
	public ArrayList<SalesVO> ShowS() {
		ArrayList<SalesPO> list = new ArrayList<SalesPO>();
		list=data.getDataS();
		ArrayList<SalesVO> newList=new ArrayList<SalesVO>();
		for(SalesPO spo:list){
			newList.add(this.SaleslPOtoVO(spo));
		}
		return newList;
	}
	
	public boolean approvalSales(String id,int approval){
		ArrayList<SalesPO> list = new ArrayList<SalesPO>();
		list=data.getDataS();
		SalesVO salesvo=new SalesVO();
		for(SalesPO spo:list){
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
						for(SalesPO spo:list){
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
						for(SalesPO spo:list){
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
	
	private SalesVO SaleslPOtoVO(SalesPO spo){
		ArrayList<CommodityVO> cvoList=new ArrayList<CommodityVO>();
		for(CommodityPO cpo:spo.getSalesList()){
			cvoList.add(gc.commodityPoToVo(cpo));
		}
		SalesVO newSvo=new SalesVO();
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
