package po.stockManage;

import java.io.Serializable;

@SuppressWarnings("serial")
public class StocksnapPO implements Serializable{
	String name;
	String model;
	int number;
	double average;
	String batch;
	String batchNumber;
	String outTime;
	int row;
	
	public StocksnapPO(){
		
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getModel(){
		return model;
	}
	
	public void setModel(String model){
		this.model = model;
	}
	
	public int getNumber(){
		return number;
	}
	
	public void setNumber(int number){
		this.number = number;
	}
	
	public double getAverage(){
		return average;
	}
	
	public void setAverage(double average){
		this.average = average;
	}
	
	public String getBatch(){
		return batch;
	}
	
	public void setBatch(String batch){
		this.batch = batch;
	}
	
	public String getBatchNumber(){
		return batchNumber;
	}
	
	public void setBatchNumber(String batchNumber){
		this.batchNumber = batchNumber;
	}
	
	public String getTime(){
		return outTime;
	}
	
	public void setTime(String outTime){
		this.outTime = outTime;
	}
	
	public int getRow(){
		return row;
	}
	
	public void setRow(int row){
		this.row = row;
	}
}
