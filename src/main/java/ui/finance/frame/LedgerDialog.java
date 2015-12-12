package ui.finance.frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import vo.finance.LedgerVO;
import vo.logistics.LogisticsVO;
import businesslogic.finance.AccountBL;
import businesslogic.logistics.CommodityBL;
import businesslogicservice.finance.AccountBLService;
import businesslogicservice.finance.GetCommodityBL;

@SuppressWarnings("serial")
public class LedgerDialog extends JDialog{
	LedgerVO ledgerVO;
	
	JLabel statusBar;
	JToolBar toolBar;
	
	JPanel mainPanel;
	JTable table1;
	JScrollPane jsp1;
	JTable table2;
	JScrollPane jsp2;
	JTable table3;
	JScrollPane jsp3;
	public LedgerDialog(FinanceFrame jFrame, String title, boolean modal, LedgerVO ledgerVO){
		super(jFrame, title, modal);
		this.ledgerVO = ledgerVO;
		
		this.setLocation(500, 150);
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(400, 400);
		
		this.statusBar = new JLabel("账目类型：" + ledgerVO.type + "     " 
				+ "创建时间：" + ledgerVO.time.substring(0, 4) + "/" + ledgerVO.time.substring(4,  6) + "/" + ledgerVO.time.substring(6,  8));
	    toolBar = new JToolBar();
		toolBar.add(statusBar);
		toolBar.setFloatable(false);
		
		this.createTable1();
		this.createTable2();
		this.createTable3();
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(3, 1));
		mainPanel.add(jsp1);
		mainPanel.add(jsp2);
		mainPanel.add(jsp3);
		
		this.add(statusBar, BorderLayout.NORTH);
		this.add(mainPanel, BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	private void createTable1() {
		GetCommodityBL getCommodityBL = new CommodityBL();
		AccountBLService accountBL = new AccountBL();
		ArrayList<LedgerVO> ledgerVOs = accountBL.showLedger();
		ArrayList<LogisticsVO> stockVOs = new ArrayList<LogisticsVO>();
	
		int index = 0;
		for(int i = 0; i < ledgerVOs.size(); i++){
			if(ledgerVOs.get(i).time.equals(ledgerVO.time)){
				index = i - 1;
				break;
			}
		}
		
		if(index == -1)
			stockVOs = getCommodityBL.merge("00000000", this.ledgerVO.time);
		else
			stockVOs = getCommodityBL.merge(ledgerVOs.get(index).time, this.ledgerVO.time);
		
		Object[] column = new Object[]{"快递名称", "快递型号", "平均进价", "平均售价"};
		Object[][] data = new Object[stockVOs.size()][4];
		
		DecimalFormat df = new DecimalFormat("#.00");
		for(int i = 0; i < stockVOs.size(); i++){
			data[i][0] = stockVOs.get(i).name;
			data[i][1] = stockVOs.get(i).model;
			data[i][2] = df.format(stockVOs.get(i).importmoney / stockVOs.get(i).importnumber);
			data[i][3] = df.format(stockVOs.get(i).salesmoney / stockVOs.get(i).salesnumber);
		}
		table1 = new JTable(data, column);
		jsp1 = new JScrollPane(table1);
	}
	private void createTable2() {
		Object[] column = new Object[]{"客户类型", "客户名", "联系方式", "应收", "应付"};
		Object[][] data = new Object[ledgerVO.customerVO.size()][5];
		for(int i = 0; i < ledgerVO.customerVO.size(); i++){
			data[i][0] = ledgerVO.customerVO.get(i).type;
			data[i][1] = ledgerVO.customerVO.get(i).name;
			data[i][2] = ledgerVO.customerVO.get(i).phone;
			data[i][3] = ledgerVO.customerVO.get(i).proceed;
			data[i][4] = ledgerVO.customerVO.get(i).pay;
		}
		table2 = new JTable(data, column);
		jsp2 = new JScrollPane(table2);
	}
	private void createTable3() {
		Object[] column = new Object[]{"账户名", "余额"};
		Object[][] data = new Object[ledgerVO.accountVO.size()][2];
		for(int i = 0; i < ledgerVO.accountVO.size(); i++){
			data[i][0] = ledgerVO.accountVO.get(i).id;
			data[i][1] = ledgerVO.accountVO.get(i).amountOfAccount;

		}
		table3 = new JTable(data, column);
		jsp3 = new JScrollPane(table3);
	}
}
