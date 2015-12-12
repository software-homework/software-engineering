package ui.logistics;

import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import businesslogic.logistics.LogisticsSnapBL;
import jxl.CellView;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import vo.logistics.LogisticsSnapVO;
import businesslogicservice.logistics.LogisticsSnapBLService;

@SuppressWarnings("serial")
public class CheckLogistics extends JDialog{
	LogisticsSnapBLService ssbs;
	final CheckLogistics cs;
	public JTable table;
	public JScrollPane scrollTable;
	public ArrayList<LogisticsSnapVO> stocksnapList = new ArrayList<LogisticsSnapVO>();
	
	public CheckLogistics(final LogisticsMain sm){
		cs = this;
		ssbs = new LogisticsSnapBL();
		
		this.setSize(900, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int)(width - this.getWidth()) / 2,(int)(height - this.getHeight()) / 2);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("库存盘点");
		this.setModal(true);
		
		this.stocksnapList = ssbs.showStocksnap();
		
        this.createExcel("d:\\export.xls");
        
        sm.setText("创建成功!");
	
	//	this.setVisible(true);
	}

	public void createExcel(String path) {
		try{
			WritableWorkbook wbook = Workbook.createWorkbook(new File(path));
			WritableSheet wsheet = wbook.createSheet("库存盘点", 0);
			WritableFont wf = new WritableFont(WritableFont.ARIAL, 10,WritableFont.NO_BOLD, false);
			WritableCellFormat titleFormat = new WritableCellFormat(wf);
			
			CellView cellView = new CellView();  
			cellView.setAutosize(true); 
			
			String column[] = new String[]{"行号","商品名称", "型号", "库存数量", "库存均价", "批次", "批号", "出厂日期"};
			for(int i = 0;i < column.length;i ++){
				Label excelTitle = new Label(i, 0, column[i], titleFormat);
				wsheet.addCell(excelTitle);
			}
			
			int bestWidth[] = new int[column.length];
			for(int i = 0;i < column.length;i ++){
				int w = column[i].length() + getChineseNum(column[i]);
				if(bestWidth[i] < w){
					bestWidth[i] = w;
				}
				
			}
			
			for(int i = 1;i < this.stocksnapList.size() + 1;i ++){
				LogisticsSnapVO ssv = this.stocksnapList.get(i - 1);
				Label l0 = new Label(0,i,"" + ssv.row);
				int width0 = String.valueOf(ssv.row).length() + getChineseNum(String.valueOf(ssv.row));
				if(bestWidth[0] < width0){
					bestWidth[0] = width0;
				}
				Label l1 = new Label(1,i,ssv.name);
				int width1 = ssv.name.length() + getChineseNum(ssv.name);
				if(bestWidth[1] < width1){
					bestWidth[1] = width1;
				}
				Label l2 = new Label(2,i,ssv.model);
				int width2 = ssv.model.length() + getChineseNum(ssv.model);
				if(bestWidth[2] < width2){
					bestWidth[2] = width2;
				}

				Label l3 = new Label(3,i,"" + ssv.number);
				int width3 = String.valueOf(ssv.number).length() + getChineseNum(String.valueOf(ssv.number));
				if(bestWidth[3] < width3){
					bestWidth[3] = width3;
				}

				Label l4 = new Label(4,i,"" + ssv.average);
				int width4 = String.valueOf(ssv.average).length() + getChineseNum(String.valueOf(ssv.average));
				if(bestWidth[4] < width4){
					bestWidth[4] = width4;
				}

				Label l5 = new Label(5,i,ssv.batch);
				int width5 = ssv.batch.length() + getChineseNum(ssv.batch);
				if(bestWidth[5] < width5){
					bestWidth[5] = width5;
				}
				
				Label l6 = new Label(6,i,ssv.batchNumber);
				int width6 = ssv.batchNumber.length() + getChineseNum(ssv.batchNumber);
				if(bestWidth[6] < width6){
					bestWidth[6] = width6;
				}
				
				Label l7 = new Label(7,i,ssv.outTime);
				int width7 = ssv.outTime.length() + getChineseNum(ssv.outTime);
				if(bestWidth[7] < width7){
					bestWidth[7] = width7;
				}
				
				wsheet.addCell(l0);
				wsheet.addCell(l1);
				wsheet.addCell(l2);
				wsheet.addCell(l3);
				wsheet.addCell(l4);
				wsheet.addCell(l5);
				wsheet.addCell(l6);
				wsheet.addCell(l7);
			}
			
			for(int i = 0;i < bestWidth.length;i ++){
				wsheet.setColumnView(i, bestWidth[i]);
			}
				
			wbook.write();			
			wbook.close();			
		}
		catch (Exception e) {				
			
		}
	}
	
	public static int getChineseNum(String context){    ///统计context中是汉字的个数
        int lenOfChinese=0;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");    //汉字的Unicode编码范围
        Matcher m = p.matcher(context);
        while(m.find()){
            lenOfChinese++;
        }
        return lenOfChinese;
    }

}
