package ui.manager.check;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import businesslogic.finance.AccountBL;
import businesslogicservice.manager.GetDocument;

/**
 * 
 * @date 2014年12月7日
 * @time 下午4:21:06
 * @author stk
 *
 */

/*
 * 查看经营历程表的搜索弹窗
 */
@SuppressWarnings("serial")
public class SearchHistory extends SearchDialog{
	private GetDocument bl;
	private CheckHistory pane;
	//---------------------------------------------------------------------
	public SearchHistory(CheckHistory pane) {
		super();
		this.pane = pane;
		//设置搜素框
		pane2 = new JPanel();
		pane2.setLayout(new GridLayout(6, 2, 40, 50));
		pane2.setBorder(BorderFactory.createEmptyBorder(50, 40, 50, 40));
		
		label1 = new JLabel("起始日期(yyyyMMdd)：", JLabel.RIGHT);
		label2 = new JLabel("结束日期(yyyyMMdd)：", JLabel.RIGHT);
		label3 = new JLabel("单据类型：", JLabel.RIGHT);
		label4 = new JLabel("客户：", JLabel.RIGHT);
		label5 = new JLabel("业务员：", JLabel.RIGHT);
		label6 = new JLabel("仓库：", JLabel.RIGHT);
		text1 = new JTextField();
		text2 = new JTextField();
		comboBox = new JComboBox<String>(new String[]{"销售类单据", "快递接类单据", "财务类单据", "物流类单据"});
		text3 = new JTextField();
		text4 = new JTextField();
		text5 = new JTextField("1");
		text5.setEnabled(false);
		
		pane2.add(label1);
		pane2.add(text1);
		pane2.add(label2);
		pane2.add(text2);
		pane2.add(label3);
		pane2.add(comboBox);
		pane2.add(label4);
		pane2.add(text3);
		pane2.add(label5);
		pane2.add(text4);
		pane2.add(label6);
		pane2.add(text5);
		
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
				if(text3.getText().trim().equals("") || text4.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "请填写完整！", "警告", JOptionPane.WARNING_MESSAGE);
				}else {
					bl = new AccountBL();
					pane.data = bl.showHistory(text1.getText(), text2.getText(), (String)comboBox.getSelectedItem(), text3.getText(), text4.getText());
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
