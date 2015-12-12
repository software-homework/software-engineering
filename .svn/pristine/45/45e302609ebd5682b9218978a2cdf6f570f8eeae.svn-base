package ui.finance.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import vo.finance.CashExpensesVO;

@SuppressWarnings("serial")
public class ShowCashExpenses extends JDialog{
		FinanceFrame jFrame;
		CashExpensesVO cashExpensesVO;
		int frameHeight = 520;
		int frameWidth = 400;
		
		JTable jt1;
		JTable jt2;
		JScrollPane jsp1;
		JScrollPane jsp2;
		JPanel jp;
		JLabel jl;
		JLabel top;
			
		public ShowCashExpenses(FinanceFrame jFrame, String title, boolean modal, CashExpensesVO cashExpensesVO) {
			super(jFrame, title, modal);
			//获取
			this.jFrame = jFrame;
			this.cashExpensesVO = cashExpensesVO;
			//设置组件
			this.setLayout(new BorderLayout());
			this.setResizable(false);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			Toolkit kit = Toolkit.getDefaultToolkit();    	// 定义工具包
		    Dimension screenSize = kit.getScreenSize();   	// 获取屏幕的尺寸			
		    this.setBounds((screenSize.width - frameWidth) / 2, (screenSize.height - frameHeight) / 3, frameWidth, frameHeight);
			//
		    String[][] account = new String[1][1];
		    account[0][0] = this.cashExpensesVO.account;
		    jt1 = new JTable(account,
		    		new String[]{"账户名"});
			jsp1 = new JScrollPane(jt1);
			//
			ArrayList<String> temp = new ArrayList<String>(cashExpensesVO.list);
			String[][] list = new String[temp.size()][3];
			for(int i = 0; i < temp.size(); i++){
				list[i][0] = temp.get(i).split(";")[0];
				list[i][1] = temp.get(i).split(";")[1];
				list[i][2] = temp.get(i).split(";")[2];
			}
			jt2 = new JTable(list, new String[]{"条目名", "金额", "备注"});
			jsp2 = new JScrollPane(jt2);
			//add to jp
			jp = new JPanel();
			jp.setLayout(new GridLayout(2,1));
			jp.add(jsp1);
			jp.add(jsp2);
			//add
			jl = new JLabel("总和: " + cashExpensesVO.total);
			//------------------------------------------------------------------------------
			top = new JLabel(cashExpensesVO.id + "    " + "操作员: " + cashExpensesVO.operator);
			this.add(top, BorderLayout.NORTH);
			this.add(jp, BorderLayout.CENTER);
			this.add(jl, BorderLayout.SOUTH);
		
			this.setVisible(true);
		}
}
