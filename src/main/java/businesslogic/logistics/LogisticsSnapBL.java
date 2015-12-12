package businesslogic.logistics;

import java.util.ArrayList;

import businesslogicservice.logistics.LogisticsSnapBLService;
import data.logistics.LogisticsSnapData;
import dataservice.logistics.LogisticsSnapDataService;
import po.logistics.LogisticsSnapPO;
import vo.logistics.LogisticsSnapVO;

public class LogisticsSnapBL implements LogisticsSnapBLService{
	private LogisticsSnapDataService ssds;
	
	public LogisticsSnapBL(){
		try {
			ssds = LogisticsSnapData.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean add(LogisticsSnapVO ssv){
		if(ssv == null){
			return false;
		}
		
		if(showStocksnap().size() == 0){
			ssv.batchNumber = "1";
		}
		else{
			if(getLast().batch.equals(ssv.batch)){
				ssv.batchNumber = getLast().batchNumber;
			}
			else if(!getLast().batch.substring(0, getLast().batch.indexOf(" ")).equals(ssv.batch.substring(0, ssv.batch.indexOf(" ")))){
				ssds.remove();
				ssv.batchNumber = "1";
			}
			else{
				int i = Integer.parseInt(getLast().batchNumber);
			    ssv.batchNumber = String.valueOf(i + 1);
			}
		}
		
		if(ssds.add(stocksnapVoToPo(ssv))){
			return true;
		}
		return false;
	}
	
	public LogisticsSnapPO stocksnapVoToPo(LogisticsSnapVO ssv){
		if(ssv == null){
			return null;
		}
		LogisticsSnapPO ssp = new LogisticsSnapPO();
		ssp.setName(ssv.name);
		ssp.setModel(ssv.model);
		ssp.setNumber(ssv.number);
		ssp.setAverage(ssv.average);
		ssp.setBatch(ssv.batch);
		ssp.setBatchNumber(ssv.batchNumber);
		ssp.setTime(ssv.outTime);
		ssp.setRow(ssv.row);
		return ssp;
	}
	
	public LogisticsSnapVO stocksnapPoToVo(LogisticsSnapPO ssp){
		if(ssp == null){
			return null;
		}
		LogisticsSnapVO ssv = new LogisticsSnapVO();
		ssv.name = ssp.getName();
		ssv.model = ssp.getModel();
		ssv.number = ssp.getNumber();
		ssv.average = ssp.getAverage();
		ssv.batch = ssp.getBatch();
		ssv.batchNumber = ssp.getBatchNumber();
		ssv.outTime = ssp.getTime();
		ssv.row = ssp.getRow();
		return ssv;
	}
	
	public ArrayList<LogisticsSnapVO> showStocksnap(){
		ArrayList<LogisticsSnapVO> list = new ArrayList<LogisticsSnapVO>();
		for(LogisticsSnapPO ssp : ssds.getStocksnap()){
			list.add(stocksnapPoToVo(ssp));
		}
		return list;
	}
	
	public LogisticsSnapVO getLast(){
		return stocksnapPoToVo(ssds.getLast());
	}
}
