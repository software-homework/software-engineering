package ui.manager.check;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import businesslogic.finance.AccountBL;
import businesslogicservice.manager.GetDocument;

/**
 * 
 * @date 2014年12月7日
 * @time 下午4:20:12
 * @author stk
 *
 */

/*
 * 查看销售明细表的搜索弹窗
 */
@SuppressWarnings("serial")
public class SearchDetail extends SearchDialog{
	private GetDocument bl;
	private CheckDetail pane;
	//--------------------------------------------------------------
	public SearchDetail(CheckDetail pane) {
		super();
		this.pane = pane;
		//设置搜素框
		pane2 = new JPanel();
		pane2.setLayout(new GridLayout(6, 2, 40, 50));
		pane2.setBorder(BorderFactory.createEmptyBorder(50, 40, 50, 40));
		
		label1 = new JLabel("起始日期(yyyyMMdd)：", JLabel.RIGHT);
		label2 = new JLabel("结束日期(yyyyMMdd)：", JLabel.RIGHT);
		label3 = new JLabel("快递名：", JLabel.RIGHT);
		label4 = new JLabel("客户：", JLabel.RIGHT);
		label5 = new JLabel("业务员：", JLabel.RIGHT);
		label6 = new JLabel("仓库：", JLabel.RIGHT);
		text1 = new JTextField();
		text2 = new JTextField();
		text3 = new JTextField();
		text4 = new JTextField();
		text5 = new JTextField();
		text6 = new JTextField("1");
		text6.setEnabled(false);
		
		pane2.add(label1);
		pane2.add(text1);
		pane2.add(label2);
		pane2.add(text2);
		pane2.add(label3);
		pane2.add(text3);
		pane2.add(label4);
		pane2.add(text4);
		pane2.add(label5);
		pane2.add(text5);
		pane2.add(label6);
		pane2.add(text6);
		
		this.add(pane2, BorderLayout.CENTER);
		//监听
		confirm.addActionListener(new ConfirmListener());
		cancel.addActionListener(new CancelListener());
		//-----------------------------------------------------------------
		this.setVisible(true);
	}
	//---------------------------------------------------------------------
	//监听内部类
	private class ConfirmListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			//判断输入格式
			if(isDateRight(text1.getText(), text2.getText())) {
				if(text3.getText().trim().equals("") || text4.getText().trim().equals("") || text5.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "请填写完整！", "警告", JOptionPane.WARNING_MESSAGE);
				}else {
					bl = new AccountBL();
					pane.data = bl.showDetail(text1.getText(), text2.getText(), text3.getText(), text4.getText(), text5.getText());
					pane.showTable();
					dispose();
				}
			}
		}
	}
	
	private class CancelListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if(JOptionPane.showConfirmDialog(null, "确定要关闭吗？", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				dispose();
			}
		}
	}
}
