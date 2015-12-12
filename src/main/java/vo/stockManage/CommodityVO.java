package vo.stockManage;

public class CommodityVO implements Cloneable{
	public String id;
	public String commodityname;
	public String model;
	public int number;
	public double priceIn;
	public double priceOut;
	public double lastPriceIn;
	public double lastPriceOut;
	public int dangerNumber;
	public boolean isDelete;
	
	@Override
	public Object clone(){
		// TODO Auto-generated method stub
		try
		{
			return super.clone();
		} catch (CloneNotSupportedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String toString(){
		return commodityname;
	}
	
	public CommodityVO(){
		
	}
	
	public CommodityVO(String commodityname,String model,double priceIn,double priceOut){
		this.commodityname=commodityname;
		this.model=model;
		this.priceIn=priceIn;
		this.priceOut=priceOut;
		this.isDelete = true;
	}
	
}
