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

import businesslogic.manager.CheckBL;
import businesslogicservice.manager.CheckBLService;

/**
 * 
 * @date 2014年12月13日
 * @time 下午5:07:19
 * @author stk
 *
 */

/*
 * 查看经营情况表的搜索弹窗
 */
@SuppressWarnings("serial")
public class SearchCondition extends SearchDialog{
	private CheckBLService bl;
	private CheckCondition pane;
	//-----------------------------------------------------------------
	public SearchCondition(CheckCondition pane) {
		super();
		this.pane = pane;
		//设置搜素框
		pane2 = new JPanel();
		pane2.setLayout(new GridLayout(2, 2, 40, 50));
		pane2.setBorder(BorderFactory.createEmptyBorder(200, 40, 200, 40));
		
		label1 = new JLabel("起始日期(yyyyMMdd)：", JLabel.RIGHT);
		label2 = new JLabel("结束日期(yyyyMMdd)：", JLabel.RIGHT);
		text1 = new JTextField();
		text2 = new JTextField();
		
		pane2.add(label1);
		pane2.add(text1);
		pane2.add(label2);
		pane2.add(text2);
		
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
				bl = new CheckBL();
				String[] str = new String[2];
				str[0] = text1.getText();
				str[1] = text2.getText();
				pane.data = bl.showCondition(str);
				pane.showTable();
				dispose();
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
