package ui.finance.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ui.finance.frame.FinanceFrame;
import vo.LogVO;
import vo.finance.LedgerVO;
import businesslogic.LogBL;
import businesslogic.finance.AccountBL;
import businesslogicservice.LogBLService;
import businesslogicservice.finance.AccountBLService;

@SuppressWarnings("serial")
public class LedgerPanel extends JPanel implements ActionListener{
	FinanceFrame jFrame;
	
	String[] name = new String[]{"建账", "查账"};
	JButton[] jButton = new JButton[name.length];

	int widthOfButton = 100;
	int heightOfButton = 30;
	
	public LedgerPanel(FinanceFrame jFrame){
		this.jFrame = jFrame;
	
		this.setLayout(null);
		this.setPreferredSize(new Dimension(widthOfButton, 0));
		
		for(int i = 0; i < this.name.length; i++){
			this.jButton[i] = new JButton(this.name[i]);
			this.jButton[i].setSize(widthOfButton, heightOfButton);
			this.jButton[i].setLocation(0, i * heightOfButton);			
			this.jButton[i].addActionListener(this);
			this.add(this.jButton[i]);
		}
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == jButton[0]){
			int n = JOptionPane.showConfirmDialog(null,"是否确定");
			if(n == 0){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String time = sdf.format(new Date());
				String type = new String();
				if(time.substring(4, 8).equals("0101"))
					type = "期初建账";
				else 
					type = "非期初建账";
				
				AccountBLService accountBL = new AccountBL();
				boolean isSuccess =  accountBL.AddLedger(time, type);
				
				if(isSuccess){
					JOptionPane.showMessageDialog(null, "建账成功", null, 1);
					/*****更新日志**/
					LogBLService logBLService = new LogBL();
					logBLService.addLog(new LogVO(jFrame.userVO.name, "建账"));
					/*****刷新表格**/
					AccountBLService accountBLService = new AccountBL();
					ArrayList<LedgerVO> ledger = accountBLService.showLedger();
					String[][] data = new String[ledger.size()][2];
					for(int i = 0; i < ledger.size(); i++){
						data[i][0] = ledger.get(i).time.substring(0, 4) + "/" + ledger.get(i).time.substring(4, 6) + "/" + ledger.get(i).time.substring(6, 8);
						data[i][1] = ledger.get(i).type;
					}
					this.jFrame.remove(this.jFrame.getScrollPanel());
					this.jFrame.add(this.jFrame.createTable(null, null, null, null, data), BorderLayout.CENTER);
					this.jFrame.setVisible(true);
				}
				else
					JOptionPane.showMessageDialog(null, "重复建账!操作失败!", null, 0);
			}
		}
		else if(arg0.getSource() == jButton[1]){
			AccountBLService accountBLService = new AccountBL();
			ArrayList<LedgerVO> ledger = accountBLService.showLedger();
			String[][] data = new String[ledger.size()][2];
			for(int i = 0; i < ledger.size(); i++){
				data[i][0] = ledger.get(i).time.substring(0, 4) + "/" + ledger.get(i).time.substring(4, 6) + "/" + ledger.get(i).time.substring(6, 8);
				data[i][1] = ledger.get(i).type;
			}
			//刷新Table
			this.jFrame.remove(this.jFrame.getScrollPanel());
			this.jFrame.add(this.jFrame.createTable(null, null, null, null, data), BorderLayout.CENTER);
			this.jFrame.setVisible(true);
		}
	}
}
