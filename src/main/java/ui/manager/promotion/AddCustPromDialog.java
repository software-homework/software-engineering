package ui.manager.promotion;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import ui.TableModel;
import vo.LogVO;
import vo.manager.CustPromVO;
import vo.stockManage.CommodityVO;
import businesslogic.LogBL;
import businesslogic.manager.PromotionBL;
import businesslogic.stockManage.CommodityBL;
import businesslogicservice.LogBLService;
import businesslogicservice.manager.GetCommodity;
import businesslogicservice.manager.PromotionBLService;

/**
 * 
 * @date 2014年12月12日
 * @time 下午7:19:37
 * @author stk
 *
 */

/*
 * 添加针对客户的促销策略弹窗dialog
 */
@SuppressWarnings("serial")
public class AddCustPromDialog extends AddPromotionDialog{
	private PromotionBLService bl;
	private GetCommodity get;
	private CustPromPane pane;
	private JComboBox<String> comboBox;
	//------------------------------------------------
	public AddCustPromDialog(CustPromPane pane) {
		super();
		this.pane = pane;
		//设置填充面板
		pane2 = new JPanel();
		pane2.setLayout(new GridLayout(5, 2, 50, 50));
		pane2.setBorder(BorderFactory.createEmptyBorder(100, 0, 70, 70));
		
		label1 = new JLabel("起始日期(yyyy/MM/dd)：", JLabel.RIGHT);
		label2 = new JLabel("结束日期(yyyy/MM/dd)：", JLabel.RIGHT);
		label3 = new JLabel("客户类型：", JLabel.RIGHT);
		label4 = new JLabel("折让金额：", JLabel.RIGHT);
		label5 = new JLabel("代金券：", JLabel.RIGHT);
		text1 = new JTextField();
		text2 = new JTextField();
		comboBox = new JComboBox<String>(new String[]{"五级", "一级普通用户", "五级VIP客户"});
		text3 = new JTextField();
		text4 = new JTextField();
		
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
		
		this.getContentPane().add(pane2, BorderLayout.CENTER);
		//设置表格面板
		pane3 = new JPanel();
		pane3.setLayout(new BoxLayout(pane3, BoxLayout.Y_AXIS));
		
		label6 = new JLabel("赠品：", JLabel.LEFT);
		label6.setPreferredSize(new Dimension(100, 100));
		
		get = new CommodityBL();
		ArrayList<CommodityVO> list = get.getCommodity();//获取数据
		data = new Object[list.size()][2];
		for(int i = 0; i < list.size(); i++) {
			data[i][0] = Boolean.FALSE;
			data[i][1] = list.get(i).commodityname;
		}
		columnName = new String[]{"", "商品名称"};
		table = new JTable(new TableModel(data, columnName));
		table.getColumnModel().getColumn(0).setMaxWidth(30);//设置第一列宽度
		JScrollPane sp = new JScrollPane(table);
		sp.setPreferredSize(new Dimension(250, 1000));
		
		pane3.add(label6);
		pane3.add(sp);
		
		this.getContentPane().add(pane3, BorderLayout.WEST);
		//按钮监听
		confirm.addActionListener(new ConfirmListener());
		cancel.addActionListener(new CancelListener());
		//-------------------------------------------------------------
		this.setVisible(true);
	}
	//--------------------------------------------------------------------------
	//监听内部类
	private class ConfirmListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if(isDateRight(text1.getText(), text2.getText())) {
				if(isNumberRight(text3.getText()) && isNumberRight(text4.getText())) {
					//生成商品信息string
					String goodsName = getInfo();
					
					bl = new PromotionBL();
					if(bl.addCustProm(new CustPromVO(text1.getText(),
													 text2.getText(),
													 (String)comboBox.getSelectedItem(),
													 goodsName,
													 Double.parseDouble(text3.getText()),
													 Double.parseDouble(text4.getText())))) {
						//刷新表格
						pane.refresh();
						dispose();
						//状态栏设置
						pane.frame.setStatusBar("添加成功");
						LogBLService logBLService = new LogBL();
						logBLService.addLog(new LogVO(pane.frame.getUserName(), "添加针对客户的促销策略"));
					}else {
						JOptionPane.showMessageDialog(null, "添加失败！", "错误",JOptionPane.ERROR_MESSAGE);
						//状态栏设置
						pane.frame.setStatusBar("添加失败");
					}
				}
			}
		}
	}
	
	private class CancelListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if(JOptionPane.showConfirmDialog(null,"确定取消添加吗?","提示",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				//刷新表格
				pane.refresh();
				dispose();
				//状态栏设置
				pane.frame.setStatusBar("取消添加");
			}
		}
	}
}
