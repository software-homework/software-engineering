package ui.manager.approval;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ui.TableModel;
import vo.finance.CashExpensesVO;
import businesslogic.manager.ApprovalBL;
import businesslogicservice.manager.ApprovalBLService;

/**
 * 
 * @date 2014年12月9日
 * @time 下午8:05:51
 * @author stk
 *
 */

/*
 * 现金费用单详细信息
 */
@SuppressWarnings("serial")
public class ShowCashExpenses extends ShowApproval{
	private ApprovalBLService bl;
	private CashExpensesVO vo;
	//--------------------------------------------------------------------------
	public ShowCashExpenses(ApprovalPane pane, String id) {
		super(pane, id);
		//获取vo
		bl = new ApprovalBL();
		vo = bl.finCashExpenses(id);
		if(vo == null) {
			JOptionPane.showMessageDialog(null, "无单据信息！", "警告", JOptionPane.WARNING_MESSAGE);
			dispose();
		}
		//设置组件
		pane2 = new JPanel();
		pane2.setLayout(new GridLayout(4, 2, 40, 50));
		pane2.setBorder(BorderFactory.createEmptyBorder(20, 40, 0, 40));
		
		label1 = new JLabel("编号：", JLabel.RIGHT);
		label2 = new JLabel(vo.id, JLabel.LEFT);
		label3 = new JLabel("操作员：", JLabel.RIGHT);
		label4 = new JLabel(vo.operator, JLabel.LEFT);
		label5 = new JLabel("银行账户：", JLabel.RIGHT);
		label6 = new JLabel(vo.account, JLabel.LEFT);
		label7 = new JLabel("总额：", JLabel.RIGHT);
		label8 = new JLabel(""+vo.total, JLabel.LEFT);
		
		pane2.add(label1);
		pane2.add(label2);
		pane2.add(label3);
		pane2.add(label4);
		pane2.add(label5);
		pane2.add(label6);
		pane2.add(label7);
		pane2.add(label8);
		
		this.add(pane2, BorderLayout.NORTH);
		//设置表格数据
		data = new Object[vo.list.size()][3];
		for(int i = 0; i < vo.list.size(); i++) {
			String[] temp = vo.list.get(i).split(";");
			data[i][0] = temp[0];
			data[i][1] = temp[1];
			data[i][2] = temp[2];
		}
		columnName = new String[]{"条目名", "金额", "备注"};
		table = new JTable(new TableModel(data, columnName) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		sp = new JScrollPane(table);
		
		this.add(sp, BorderLayout.CENTER);
		//修改不可用
		update.setEnabled(false);
		update.setVisible(false);
		//监听
		exit.addActionListener(new ExitListener());
		//-----------------------------------------------------------------------------------------------
		this.setVisible(true);
	}
	//------------------------------------------------------------------------------------------------------
	//监听内部类
	private class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(JOptionPane.showConfirmDialog(null, "确定要关闭吗？", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				dispose();
			}
		}
	}
}
