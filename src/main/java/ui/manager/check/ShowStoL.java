package ui.manager.check;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import businesslogic.finance.AccountBL;
import businesslogicservice.manager.GetDocument;
import vo.logistics.LogisticsLossVO;

/**
 * 
 * @date 2014年12月12日
 * @time 下午7:14:30
 * @author stk
 *
 */

/*
 * 显示报损单
 */
@SuppressWarnings("serial")
public class ShowStoL extends Show{
	private GetDocument get;
	private LogisticsLossVO vo;
	//-----------------------------------------------------------------
	public ShowStoL(String id) {
		super(id);
		get = new AccountBL();
		//获取vo
		vo = get.findOneStockLoss(id);
		if(vo == null) {
			JOptionPane.showMessageDialog(null, "无单据信息！", "警告", JOptionPane.WARNING_MESSAGE);
			dispose();
		}
		//设置组件
		pane2 = new JPanel();
		pane2.setLayout(new GridLayout(4, 2, 40, 50));
		pane2.setBorder(BorderFactory.createEmptyBorder(150, 40, 0, 40));
		
		label1 = new JLabel("编号：", JLabel.RIGHT);
		label2 = new JLabel(vo.id, JLabel.LEFT);
		label3 = new JLabel("商品名称：", JLabel.RIGHT);
		label4 = new JLabel(vo.name, JLabel.LEFT);
		label5 = new JLabel("商品型号：", JLabel.RIGHT);
		label6 = new JLabel(vo.model, JLabel.LEFT);
		label7 = new JLabel("报损数量：", JLabel.RIGHT);
		label8 = new JLabel(""+vo.lossNumber, JLabel.LEFT);
		
		pane2.add(label1);
		pane2.add(label2);
		pane2.add(label3);
		pane2.add(label4);
		pane2.add(label5);
		pane2.add(label6);
		pane2.add(label7);
		pane2.add(label8);
		
		this.add(pane2, BorderLayout.NORTH);
		//监听
		exit.addActionListener(new ExitListener());
		//-----------------------------------------------------------------------------------------------
		this.setVisible(true);
	}
	//-------------------------------------------------------------------------------------------------------
	//监听内部类
	private class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
}
