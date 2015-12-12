package vo.logistics;

public class LogisticsSnapVO {
	public String name;
	public String model;
	public int number;
	public double average;
	public String batch;
	public String batchNumber;
	public String outTime;
	public int row;
	
	public LogisticsSnapVO(){
		
	}
	
	public LogisticsSnapVO(int row,String commodityname,String model,int number,double average,String batch){
		this.name=commodityname;
		this.model=model;
		this.number=number;
		this.average=average;
		this.batch=batch;
		this.row=row;
	}
	
}
