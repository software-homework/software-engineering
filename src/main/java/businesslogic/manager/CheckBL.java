package businesslogic.manager;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import vo.courier.ImportVO;
import vo.courier.CourierVO;
import vo.logistics.CommodityVO;
import vo.logistics.PresentVO;
import vo.logistics.LogisticsLossVO;
import vo.logistics.LogisticsOverflowVO;
import businesslogic.courier.ImportBL;
import businesslogic.courier.CourierBL;
import businesslogic.logistics.CommodityBL;
import businesslogic.logistics.PresentBL;
import businesslogic.logistics.LogisticsLossBL;
import businesslogic.logistics.LogisticsOverflowBL;
import businesslogicservice.finance.GetCheckBL;
import businesslogicservice.manager.CheckBLService;
import businesslogicservice.manager.GetCommodity;
import businesslogicservice.manager.GetImport;
import businesslogicservice.manager.GetPresent;
import businesslogicservice.manager.GetSales;
import businesslogicservice.manager.GetStockLoss;
import businesslogicservice.manager.GetStockOverflow;

/**
 * 
 * @date 2014年12月13日
 * @time 下午5:16:30
 * @author stk
 *
 */

/*
 * 查看表格逻辑
 */
public class CheckBL implements CheckBLService, GetCheckBL{
	SimpleDateFormat sdf;
	//------------------------------------------------------
	public CheckBL() {
		sdf = new SimpleDateFormat("yyyyMMdd");
	}
	//------------------------------------------------------
	public Object[][] showCondition(String[] str) {
		Object[][] temp = new Object[1][8];
		GetImport getImport = new ImportBL();
		GetSales getSales = new CourierBL();
		GetStockLoss getStockLoss = new LogisticsLossBL();
		GetStockOverflow getStockOverflow = new LogisticsOverflowBL();
		GetPresent getPresent = new PresentBL();
		GetCommodity getCommodity = new CommodityBL();
		
		ArrayList<ImportVO> importAL = new ArrayList<ImportVO>();
		ArrayList<CourierVO> salesAL = new ArrayList<CourierVO>();
		ArrayList<LogisticsLossVO> stockLossAL = new ArrayList<LogisticsLossVO>();
		ArrayList<LogisticsOverflowVO> stockOverflowAL = new ArrayList<LogisticsOverflowVO>();
		ArrayList<PresentVO> presentAL = new ArrayList<PresentVO>();
		ArrayList<CommodityVO> commodityAL = new ArrayList<CommodityVO>();

		importAL = getImport.getCheckedImport();
		salesAL = getSales.getCheckedSales();
		stockLossAL = getStockLoss.getCheckedStockLoss();
		stockOverflowAL = getStockOverflow.getCheckedStockOverflow();
		presentAL = getPresent.getCheckedPresent();
		commodityAL = getCommodity.getCommodity();
		//销售收入
		double money = 0;
		for(CourierVO i : salesAL) {
			String[] date = i.id.split("-");
			try {
				if((sdf.parse(str[0]).compareTo(sdf.parse(date[1])) <= 0) && (sdf.parse(date[1]).compareTo(sdf.parse(str[1])) <= 0)) {
					if (date[0].equals("XSD")) {
						money += i.pretotal;
					} else if (date[0].equals("XSTHD")) {
						money -= i.pretotal;
					}
				}
			} catch (ParseException e) {
			}
		}
		temp[0][0] = money;
		//商品类收入
		money = 0;
		for (LogisticsOverflowVO i : stockOverflowAL) {
			String[] date = i.id.split("-");
			try {
				if((sdf.parse(str[0]).compareTo(sdf.parse(date[1])) <= 0) && (sdf.parse(date[1]).compareTo(sdf.parse(str[1])) <= 0)) {
					for (CommodityVO j : commodityAL) {
						if (j.commodityname.equals(i.name)) {
							money += j.lastPriceIn * i.overNumber;
						}
					}
				}
			} catch (ParseException e) {
			}
		}
		temp[0][1] = money;
		//折让
		money = 0;
		for (CourierVO i :salesAL) {
			String[] date = i.id.split("-");
			try {
				if((sdf.parse(str[0]).compareTo(sdf.parse(date[1])) <= 0) && (sdf.parse(date[1]).compareTo(sdf.parse(str[1])) <= 0)) {
					if (date[0].equals("XSD")) {
						money += i.discount;
						money += i.Voucher;
					} else if (date[0].equals("XSTHD")) {
						money -= i.discount;
						money -= i.Voucher;
					}
				}
			} catch (ParseException e) {
			}
		}
		temp[0][2] = money;
		//折让后总收入
		temp[0][3] = Double.parseDouble(temp[0][0].toString()) + Double.parseDouble(temp[0][1].toString()) - Double.parseDouble(temp[0][2].toString());
		//销售成本
		money = 0;
		for(ImportVO i : importAL) {
			String[] date = i.id.split("-");
			try {
				if((sdf.parse(str[0]).compareTo(sdf.parse(date[1])) <= 0) && (sdf.parse(date[1]).compareTo(sdf.parse(str[1])) <= 0)) {
					if (date[0].equals("JHD")) {
						money += i.total;
					} else if (date[0].equals("JHTHD")) {
						money -= i.total;
					}
				}
			} catch (ParseException e) {
			}
		}
		temp[0][4] = money;
		//商品类支出
		money = 0;
		//报损
		for (LogisticsLossVO i : stockLossAL) {
			String[] date = i.id.split("-");
			try {
				if((sdf.parse(str[0]).compareTo(sdf.parse(date[1])) <= 0) && (sdf.parse(date[1]).compareTo(sdf.parse(str[1])) <= 0)) {
					for (CommodityVO j : commodityAL) {
						if (j.commodityname.equals(i.name)) {
							money += j.lastPriceIn * i.lossNumber;
							break;
						}
					}
				}
			} catch (ParseException e) {
			}
		}
		//赠送
		for (PresentVO i : presentAL) {
			String[] date = i.id.split("-");
			try {
				if((sdf.parse(str[0]).compareTo(sdf.parse(date[1])) <= 0) && (sdf.parse(date[1]).compareTo(sdf.parse(str[1])) <= 0)) {
					for (String j : i.presentList) {
						for (CommodityVO k : commodityAL) {
							if (k.commodityname.equals(j.split(";")[0])) {
								money += Double.parseDouble(j.split(";")[2]) * k.lastPriceIn;
								break;
							}
						}
					}
				}
			} catch (ParseException e) {
			}
		}
		temp[0][5] = money;
		//总支出
		temp[0][6] = Double.parseDouble(temp[0][4].toString()) + Double.parseDouble(temp[0][5].toString());
		//利润
		temp[0][7] = Double.parseDouble(temp[0][3].toString()) - Double.parseDouble(temp[0][6].toString());
		return temp;
	}
	//--------------------------------------------------
	public boolean output(String path, Object[][] data, String[] name) {
		//name的第一项为sheet名，其余为表头
		try {
			WritableWorkbook wbook = Workbook.createWorkbook(new File(path));
			WritableSheet wsheet = wbook.createSheet(name[0], 0);
			WritableFont wf = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false);
			WritableCellFormat titleFormat = new WritableCellFormat(wf);
			//获取表头
			String[] title = new String[name.length - 1];
			for (int i = 1; i < name.length; i++) {
				title[i - 1] = name[i];
			}
			//设置表头
			for (int i = 0; i < title.length; i++) {
				Label excelTitle = new Label(i, 0, title[i], titleFormat);
				wsheet.addCell(excelTitle);
			}
			//设置内容
			for (int i = 0; i < data.length; i++) {
				for(int j = 0; j < name.length - 1; j++) {
					Label l = new Label(j, i, data[i][j].toString());
					wsheet.addCell(l);
				}
			}
			//写入文件
			wbook.write();
			wbook.close();
			return true;
		} catch (Exception e) {
			System.out.println("文件创建失败");
			return false;
		}
	}
}
