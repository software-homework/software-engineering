package data.manager;

import io.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import dataservice.manager.*;
import po.manager.*;

/**
 * 
 * @date 2014年12月16日
 * @time 下午7:36:21
 * @author stk
 *
 */

/*
 * 制定促销策略数据
 */
public class PromotionData extends UnicastRemoteObject implements PromotionDataService{
	private static final long serialVersionUID = -8868676826752697887L;
	private static PromotionData data;
	private ArrayList<Object> custPromAL;
	private ArrayList<Object> bargainAL;
	private ArrayList<Object> totalPromAL;
	//--------------------------------------------------------
	private PromotionData()throws RemoteException{
		Input in = new Input();
		//从文件中读取
		custPromAL = in.input("promotionforcustomer.txt");
		bargainAL = in.input("bargain.txt");
		totalPromAL = in.input("promotionfortotal.txt");
	}
	
	public static PromotionData getInstance() throws RemoteException{
		if(data==null){
			data = new PromotionData();
		}
		
		return data;
	}
	//---------------------------------------------------------
	public boolean addCustProm(CustPromPO po) {
		int judge = this.finCustProm(po);
		if(judge == -1) {
			custPromAL.add(po);
			this.custPromOutput();
			return true;
		}
		return false;
	}
	
	public boolean delCustProm(CustPromPO po) {
		int judge = this.finCustProm(po);
		if(judge != -1) {
			custPromAL.remove(judge);
			this.custPromOutput();
			return true;
		}
		return false;
	}
	
	public ArrayList<CustPromPO> shoCustProm() {
		ArrayList<CustPromPO> temp = new ArrayList<CustPromPO>();
		for(Object i : custPromAL) {
			temp.add((CustPromPO)i);
		}
		return temp;
	}
	
	private int finCustProm(CustPromPO po) {
		CustPromPO temp;
		for(Object i : custPromAL) {
			temp = (CustPromPO)i;
			if(temp.getStartTime().equals(po.getStartTime())
				&& temp.getEndTime().equals(po.getEndTime())
				&& temp.getType().equals(po.getType())
				&& temp.getGoodsName().equals(po.getGoodsName())
				&& temp.getDiscount() == po.getDiscount()
				&& temp.getVoucher() == po.getVoucher()) {
				return custPromAL.indexOf(i);
			}
		}
		return -1;
	}
	 private void custPromOutput() {
		 new Output(custPromAL, "promotionforcustomer.txt");
	 }
	//---------------------------------------------------------
	public boolean addBargain(BargainPO po) {
		int judge = this.finBargain(po);
		if(judge == -1) {
			bargainAL.add(po);
			this.bargainOutput();
			return true;
		}
		return false;
	}
	
	public boolean delBargain(BargainPO po) {
		int judge = this.finBargain(po);
		if(judge != -1) {
			bargainAL.remove(judge);
			this.bargainOutput();
			return true;
		}
		return false;
	}
	
	public ArrayList<BargainPO> shoBargain() {
		ArrayList<BargainPO> temp = new ArrayList<BargainPO>();
		for(Object i : bargainAL) {
			temp.add((BargainPO)i);
		}
		return temp;
	}
	
	private int finBargain(BargainPO po) {
		BargainPO temp;
		for(Object i : bargainAL) {
			temp = (BargainPO)i;
			if(temp.getStartTime().equals(po.getStartTime())
				&& temp.getEndTime().equals(po.getEndTime())
				&& temp.getGoodsName().equals(po.getGoodsName())
				&& temp.getPromotion() == po.getPromotion()) {
				return bargainAL.indexOf(i);
			}
		}
		return -1;
	}
	
	private void bargainOutput() {
		new Output(bargainAL, "bargain.txt");
	}
	//-----------------------------------------------------------
	public boolean addTotalProm(TotalPromPO po) {
		int judge = this.finTotalProm(po);
		if(judge == -1) {
			totalPromAL.add(po);
			this.totalPromOutput();
			return true;
		}
		return false;
	}
	
	public boolean delTotalProm(TotalPromPO po) {
		int judge = this.finTotalProm(po);
		if(judge != -1) {
			totalPromAL.remove(judge);
			this.totalPromOutput();
			return true;
		}
		return false;
	}
	
	public ArrayList<TotalPromPO> shoTotalProm() {
		ArrayList<TotalPromPO> temp = new ArrayList<TotalPromPO>();
		for(Object i : totalPromAL) {
			temp.add((TotalPromPO)i);
		}
		return temp;
	}
	
	private int finTotalProm(TotalPromPO po) {
		TotalPromPO temp;
		for(Object i : totalPromAL) {
			temp = (TotalPromPO)i;
			if(temp.getStartTime().equals(po.getStartTime())
				&& temp.getEndTime().equals(po.getEndTime())
				&& temp.getTotal() == po.getTotal()
				&& temp.getGoodsName().equals(po.getGoodsName())
				&& temp.getVoucher() == po.getVoucher()) {
				return totalPromAL.indexOf(i);
			}
		}
		return -1;
	}
	
	private void totalPromOutput() {
		new Output(totalPromAL, "promotionfortotal.txt");
	}
}
