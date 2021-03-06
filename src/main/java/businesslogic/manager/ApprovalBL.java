package businesslogic.manager;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import businesslogic.courier.CustomerBL;
import businesslogic.courier.ImportBL;
import businesslogic.courier.CourierBL;
import businesslogic.finance.AccountBL;
import businesslogic.logistics.CommodityBL;
import businesslogic.logistics.PresentBL;
import businesslogic.logistics.LogisticsLossBL;
import businesslogic.logistics.LogisticsOverflowBL;
import businesslogicservice.finance.GetCustomerBL;
import businesslogicservice.manager.ApprovalBLService;
import businesslogicservice.manager.GetCommodity;
import businesslogicservice.manager.GetDocument;
import businesslogicservice.manager.GetImport;
import businesslogicservice.manager.GetPresent;
import businesslogicservice.manager.GetSales;
import businesslogicservice.manager.GetStockLoss;
import businesslogicservice.manager.GetStockOverflow;
import data.manager.PromotionData;
import dataservice.manager.PromotionDataService;
import po.manager.BargainPO;
import po.manager.CustPromPO;
import po.manager.TotalPromPO;
import vo.courier.CustomerVO;
import vo.courier.ImportVO;
import vo.courier.CourierVO;
import vo.finance.CashExpensesVO;
import vo.finance.DocumentVO;
import vo.logistics.CommodityVO;
import vo.logistics.PresentVO;
import vo.logistics.LogisticsLossVO;
import vo.logistics.LogisticsOverflowVO;

/**
 * 
 * @date 2014年12月13日
 * @time 下午4:33:40
 * @author stk
 *
 */

/*
 * 审批单据逻辑
 */
public class ApprovalBL implements ApprovalBLService{
	SimpleDateFormat sdf;
	GetImport getImport;
	GetSales getSales;
	GetDocument getDocument;
	GetStockLoss getStockLoss;
	GetStockOverflow getStockOverflow;
	GetPresent getPresent;

	ArrayList<ImportVO> importAL;
	ArrayList<CourierVO> salesAL;
	ArrayList<DocumentVO> documentAL;
	ArrayList<LogisticsLossVO> stockLossAL;
	ArrayList<LogisticsOverflowVO> stockOverflowAL;
	ArrayList<PresentVO> presentAL;
	ArrayList<CashExpensesVO> cashExpensesAL;
	PromotionDataService data;
	//--------------------------------------------------------------
	public ApprovalBL() {
		sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			data = PromotionData.getInstance();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		getImport = new ImportBL();
		getSales = new CourierBL();
		getDocument = new AccountBL();
		getStockLoss = new LogisticsLossBL();
		getStockOverflow = new LogisticsOverflowBL();
		getPresent = new PresentBL();
	}
	//--------------------------------------------------------------
	public String[] show() {
		importAL = new ArrayList<ImportVO>();
		salesAL = new ArrayList<CourierVO>();
		documentAL = new ArrayList<DocumentVO>();
		stockLossAL = new ArrayList<LogisticsLossVO>();
		stockOverflowAL = new ArrayList<LogisticsOverflowVO>();
		presentAL = new ArrayList<PresentVO>();
		cashExpensesAL = new ArrayList<CashExpensesVO>();
		
		importAL = getImport.getUncheckedImport();
		salesAL = getSales.getUncheckedSales();
		documentAL = getDocument.getUncheckedDocuments();
		stockLossAL = getStockLoss.getUncheckedStockLoss();
		stockOverflowAL = getStockOverflow.getUncheckedStockOverflow();
		presentAL = getPresent.getUncheckedPresent();
		cashExpensesAL = getDocument.getUncheckedCashExpenses();
		
		String[] temp = new String[salesAL.size() + importAL.size() + documentAL.size() + stockLossAL.size() + stockOverflowAL.size() + presentAL.size() + cashExpensesAL.size()];
		int count = 0;
		//销售类
		for(int i = 0; i < salesAL.size(); i++) {
			temp[count] = salesAL.get(i).id;
			count++;
		}
		//快递接类
		for(int i = 0; i < importAL.size(); i++) {
			temp[count] = importAL.get(i).id;
			count++;
		}
		//财务类
		for(int i = 0; i < documentAL.size(); i++) {
			temp[count] = documentAL.get(i).id;
			count++;
		}
		//报损单
		for(int i = 0; i < stockLossAL.size(); i++) {
			temp[count] = stockLossAL.get(i).id;
			count++;
		}
		//报溢单
		for(int i = 0; i < stockOverflowAL.size(); i++) {
			temp[count] = stockOverflowAL.get(i).id;
			count++;
		}
		//赠送单
		for(int i = 0; i < presentAL.size(); i++) {
			temp[count] = presentAL.get(i).id;
			count++;
		}
		//现金费用单
		for(int i = 0; i < cashExpensesAL.size(); i++) {
			temp[count] = cashExpensesAL.get(i).id;
			count++;
		}
		return temp;
	}
	//-------------------------------------------------------
	//查找
	public CourierVO finSales(String id) {
		salesAL = new ArrayList<CourierVO>();
		salesAL = getSales.getUncheckedSales();
		for(CourierVO i : salesAL) {
			if(i.id.equals(id)) {
				return i;
			}
		}
		return null;
	}
	
	public ImportVO finImport(String id) {
		importAL = new ArrayList<ImportVO>();
		importAL = getImport.getUncheckedImport();
		for(ImportVO i : importAL) {
			if(i.id.equals(id)) {
				return i;
			}
		}
		return null;
	}
	
	public DocumentVO finDocument(String id) {
		documentAL = new ArrayList<DocumentVO>();
		documentAL = getDocument.getUncheckedDocuments();
		for(DocumentVO i : documentAL) {
			if(i.id.equals(id)) {
				return i;
			}
		}
		return null;
	}
	
	public LogisticsLossVO finStockLoss(String id) {
		stockLossAL = new ArrayList<LogisticsLossVO>();
		stockLossAL = getStockLoss.getUncheckedStockLoss();
		for(LogisticsLossVO i : stockLossAL) {
			if(i.id.equals(id)) {
				return i;
			}
		}
		return null;
	}
	
	public LogisticsOverflowVO finStockOverflow(String id) {
		stockOverflowAL = new ArrayList<LogisticsOverflowVO>();
		stockOverflowAL = getStockOverflow.getUncheckedStockOverflow();
		for(LogisticsOverflowVO i : stockOverflowAL) {
			if(i.id.equals(id)) {
				return i;
			}
		}
		return null;
	}
	
	public PresentVO finPresent(String id) {
		presentAL = new ArrayList<PresentVO>();
		presentAL = getPresent.getUncheckedPresent();
		for(PresentVO i : presentAL){
			if(i.id.equals(id)) {
				return i;
			}
		}
		return null;
	}
	
	public CashExpensesVO finCashExpenses(String id) {
		cashExpensesAL = new ArrayList<CashExpensesVO>();
		cashExpensesAL = getDocument.getUncheckedCashExpenses();
		for(CashExpensesVO i : cashExpensesAL) {
			if(i.id.equals(id)) {
				return i;
			}
		}
		return null;
	}
	//修改
	public boolean modifySales(CourierVO vo) {
		getSales.updateSales(vo);
		return true;
	}
	
	public boolean modifyImport(ImportVO vo) {
		getImport.updateImport(vo);
		return true;
	}
	
	public boolean modifyPresent(PresentVO vo) {
		getPresent.updatePresent(vo);
		return true;
	}
	//------------------------------------------------------
	public int[] approve(String[] id, int approval) {
		//排序
		if(id.length > 1) {
			String temp;
			for(int i = 0; i < id.length - 1; i++) {
				String[] tempi = id[i].split("-");
				for(int j = i + 1; j < id.length; j++) {
					String[] tempj = id[j].split("-");
					try {
						if(sdf.parse(tempi[1]).compareTo(sdf.parse(tempj[1])) > 0) {
							temp = id[i];
							id[i] = id[j];
							id[j] = temp;
						}else if(sdf.parse(tempi[1]).compareTo(sdf.parse(tempj[1])) == 0) {
							if((tempi[0].equals("XSD") || tempi[0].equals("XSTHD")) && (tempj[0].equals("JHD") || tempj[0].equals("JHTHD"))) {
								temp = id[i];
								id[i] = id[j];
								id[j] = temp;
							}else if(tempi[0].equals("XSTHD") && tempj[0].equals("XSD")) {
								temp = id[i];
								id[i] = id[j];
								id[j] = temp;
							}else if(tempi[0].equals("JHTHD") && tempj[0].equals("JHD")) {
								temp = id[i];
								id[i] = id[j];
								id[j] = temp;
							}
						}
					} catch (ParseException e) {
						System.out.println("日期比较错误");
					}
				}
			}
		}
		//审批
		int[] condition = {0,0};
		for(int i = 0; i < id.length; i++) {
			switch(id[i].split("-")[0]) {
			case "JHD" : {}
			case "JHTHD" : {
				if (getImport.approvalImport(id[i], approval)) {
					condition[0]++;
				} else {
					condition[1]++;
				}
				break;
			}
			case "XSD" : {
				if (approval == 1) {
					if (this.promote(id[i])) {
						condition[0]++;
					} else {
						condition[1]++;
					}
				} else {
					if (getSales.approvalSales(id[i], approval)) {
						condition[0]++;
					} else {
						condition[1]++;
					}
				}
				break;
			}
			case "XSTHD" : {
				if (getSales.approvalSales(id[i], approval)) {
					condition[0]++;
				} else {
					condition[1]++;
				}
				break;
			}
			case "SKD" : {}
			case "FKD" : {
				if (getDocument.updateDocuments(id[i], approval)) {
					condition[0]++;
				} else {
					condition[1]++;
				}
				break;
			}
			case "BYD" : {
				if (getStockOverflow.approvalStockOverflow(id[i], approval)) {
					condition[0]++;
				} else {
					condition[1]++;
				}
				break;
			}
			case "BSD" : {
				if (getStockLoss.approvalStockLoss(id[i], approval)) {
					condition[0]++;
				} else {
					condition[1]++;
				}
				break;
			}
			case "ZSD" : {
				if (getPresent.approvalPresent(id[i], approval)) {
					condition[0]++;
				} else {
					condition[1]++;
				}
				break;
			}
			case "XJFYD" : {
				if (getDocument.updateCashExpenses(id[i], approval)) {
					condition[0]++;
				} else {
					condition[1]++;
				}
				break;
			}
			default : {
				System.out.println("单据编号错误。");
				condition[1]++;
			}
			}
		}
		return condition;
	}
	
	private boolean promote(String id) {
		
		ArrayList<CustomerVO> customer = new ArrayList<CustomerVO>();
		ArrayList<CustPromPO> custAL = new ArrayList<CustPromPO>();
		ArrayList<BargainPO> bargAL = new ArrayList<BargainPO>();
		ArrayList<TotalPromPO> totaAL = new ArrayList<TotalPromPO>();
		CourierVO temp = this.finSales(id);
		String goods = "";
		//针对客户
		String type = "";
		GetCustomerBL get = new CustomerBL();
		customer = get.ShowC();
		for (CustomerVO i : customer) {
			if (i.name.equals(temp.customer)) {
				type = i.level;
				break;
			}
		}
		custAL = data.shoCustProm();
		for (CustPromPO i : custAL) {
			String[] d1 = i.getStartTime().split("\\/");
			String date1 = d1[0] + d1[1] + d1[2];
			String[] d2 = i.getEndTime().split("\\/");
			String date2 = d2[0] + d2[1] + d2[2];
			try {
				if ((sdf.parse(date1).compareTo(sdf.parse(temp.id.split("-")[1])) <= 0) && (sdf.parse(temp.id.split("-")[1]).compareTo(sdf.parse(date2)) <= 0)) {
					if (i.getType().equals(type)) {
						temp.discount += i.getDiscount();
						temp.Voucher += i.getVoucher();
						if (!i.getGoodsName().equals("无")) {
							goods = goods +","+ i.getGoodsName();
						}
						break;
					}
				}
			} catch (ParseException e) {
				System.out.println("日期错误");
			}
		}
		//组合快递
		boolean judge = false;
		String name = "";
		for (CommodityVO i : temp.salesList) {
			name = name +","+ i.commodityname;
			judge = true;
		}
		if (judge) {
			name = name.substring(1);
			bargAL = data.shoBargain();
			for (BargainPO i : bargAL) {
				String[] d1 = i.getStartTime().split("\\/");
				String date1 = d1[0] + d1[1] + d1[2];
				String[] d2 = i.getEndTime().split("\\/");
				String date2 = d2[0] + d2[1] + d2[2];
				try {
					if ((sdf.parse(date1).compareTo(sdf.parse(temp.id.split("-")[1])) <= 0) && (sdf.parse(temp.id.split("-")[1]).compareTo(sdf.parse(date2)) <= 0)) {
						if (i.getGoodsName().equals(name)) {
							temp.discount += i.getPromotion();
							break;
						}
					}
				} catch (ParseException e) {
					System.out.println("日期错误");
				}
			}
		}
		//针对总价
		totaAL = data.shoTotalProm();
		for (TotalPromPO i : totaAL) {
			String[] d1 = i.getStartTime().split("\\/");
			String date1 = d1[0] + d1[1] + d1[2];
			String[] d2 = i.getEndTime().split("\\/");
			String date2 = d2[0] + d2[1] + d2[2];
			try {
				if ((sdf.parse(date1).compareTo(sdf.parse(temp.id.split("-")[1])) <= 0) && (sdf.parse(temp.id.split("-")[1]).compareTo(sdf.parse(date2)) <= 0)) {
					if (temp.pretotal >= i.getTotal()) {
						temp.Voucher += i.getVoucher();
						if (!i.getGoodsName().equals("无")) {
							goods = goods +","+ i.getGoodsName();
						}
					}
				}
			} catch (ParseException e) {
				System.out.println("日期错误");
			}
		}
		//修改单据
		temp.posttotal = temp.pretotal - temp.discount - temp.Voucher;
		if (temp.posttotal < 0) {
			temp.posttotal = 0;
		}
		this.modifySales(temp);
		//发放赠品
		if (!goods.equals("")) {
			goods = goods.substring(1);
			String[] goodsname = goods.split(",");
			
			GetCommodity getC = new CommodityBL();
			ArrayList<CommodityVO> vodata = new ArrayList<CommodityVO>();
			vodata = getC.getCommodity();
			
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < goodsname.length; i++) {
				String t = "";
				for (CommodityVO j : vodata) {
					if (j.commodityname.equals(goodsname[i])) {
						t = j.model;
						break;
					}
				}
				boolean has = false;
				for (int j = 0; j < list.size(); j++) {
					String[] str = list.get(j).split(";");
					if (str[0].equals(goodsname[i])) {
						list.set(j, (str[0] +";"+ str[1] +";"+ (Integer.parseInt(str[2]) + 1)));
						has = true;
						break;
					}
				}
				if (!has) {
					list.add(goodsname[i] +";"+ t +";1");
				}
			}
			PresentVO vo = new PresentVO(3, list);
			if (!getPresent.add(vo)) {
				JOptionPane.showMessageDialog(null, "赠品发放失败！", "警告", JOptionPane.WARNING_MESSAGE);
			}
		}
		return getSales.approvalSales(id, 1);
	}
}
