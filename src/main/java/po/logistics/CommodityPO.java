package po.logistics;

import java.io.Serializable;


public class CommodityPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5206687312494113984L;
	String id;
	String commodityname;
	String model;
	int number;
	double priceIn;
	double priceOut;
	double lastPriceIn;
	double lastPriceOut;
	int dangerNumber;
	boolean Isdelete;
	
	public CommodityPO(){
		this.Isdelete = true;
	}
	
	public CommodityPO(String commodityname,String model,int priceIn,int priceOut){
		this.commodityname=commodityname;
		this.model=model;
		this.priceIn=priceIn;
		this.priceOut=priceOut;
		this.Isdelete = true;
	}
	
	public void setId(String id){
		this.id=id;
	}
	
	public String getId(){
		return id;
	}
	
	public void setCommodityName(String name){
		this.commodityname=name;
	}
	
	public String getCommodityName(){
		return commodityname;
	}
	
	public void setModel(String model){
		this.model=model;
	}
	
	public String getModel(){
		return model;
	}
	
	public void setNumber(int number){
		this.number=number;
	}
	
	public int getNumber(){
		return number;
	}
	
	public void setPriceIn(double priceIn){
		this.priceIn=priceIn;
	}
	
	public double getPriceIn(){
		return priceIn;
	}
	
	public void setPriceOut(double priceOut){
		this.priceOut=priceOut;
	}
	
	public double getPriceOut(){
		return priceOut;
	}
	
	public void setLastPriceIn(double lastPriceIn){
		this.lastPriceIn=lastPriceIn;
	}
	
	public double getLastPriceIn(){
		return lastPriceIn;
	}
	
	public void setLastPriceOut(double lastPriceOut){
		this.lastPriceOut=lastPriceOut;
	}
	
	public double getLastPriceOut(){
		return lastPriceOut;
	}
	
	public void setDangerNumber(int number){
		this.dangerNumber = number;
	}
	
	public int getDangerNumber(){
		return dangerNumber;
	}
	
	public boolean getIsDelete(){
		return Isdelete;
	}
	
	public void setIsDelete(boolean b){
		this.Isdelete = b;
	}
}
