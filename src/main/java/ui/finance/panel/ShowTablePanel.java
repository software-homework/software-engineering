package ui.finance.panel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;

import ui.finance.frame.FinanceFrame;
import vo.LogVO;
import businesslogic.LogBL;
import businesslogicservice.LogBLService;

@SuppressWarnings("serial")
public class ShowTablePanel extends JPanel implements ActionListener{
	FinanceFrame jFrame;
	
	String[] name = new String[]{"导出表格"};
	JButton[] jButton = new JButton[name.length];

	int widthOfButton = 100;
	int heightOfButton = 30;
	
	public ShowTablePanel(FinanceFrame jFrame){
		this.jFrame = jFrame;
		
		this.setLayout(null);
		this.setPreferredSize(new Dimension(widthOfButton, 0));
		
		for(int i = 0; i < this.name.length; i++){
			this.jButton[i] = new JButton(this.name[i]);
			this.jButton[i].setSize(widthOfButton, heightOfButton);
			this.jButton[i].setLocation(0, i * heightOfButton);	
			this.add(this.jButton[i]);
			this.jButton[i].addActionListener(this);
			
			
		}
	}
	
	@SuppressWarnings("static-access")
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == jButton[0]){
			int column =this.jFrame.getTable().getColumnCount();
			JTableToExcel toExcel = new JTableToExcel();
			if(column == 1){
				File f = new File("经营历程表.xls");
				toExcel.export(f, "经营历程表", "", this.jFrame.getTable());
				//日志
				LogBLService logBLService = new LogBL();
				logBLService.addLog(new LogVO(jFrame.userVO.name, "导出经营历程表"));			
			}
			else if(column == 6){
				File f = new File("销售明细表.xls");
				toExcel.export(f, "销售明细表", "", this.jFrame.getTable());
				//日志
				LogBLService logBLService = new LogBL();
				logBLService.addLog(new LogVO(jFrame.userVO.name, "导出销售明细表"));
			}
			else if(column == 8){
				File f = new File("经营情况表.xls");
				toExcel.export(f, "经营情况表", "", this.jFrame.getTable());
				//日志
				LogBLService logBLService = new LogBL();
				logBLService.addLog(new LogVO(jFrame.userVO.name, "导出经营情况表"));
			}
		}
	}
}
