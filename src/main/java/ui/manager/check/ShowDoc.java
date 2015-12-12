package ui.manager.check;

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
import vo.finance.DocumentVO;
import businesslogic.finance.AccountBL;
import businesslogicservice.manager.GetDocument;

/**
 * 
 * @date 2014年12月12日
 * @time 下午7:13:13
 * @author stk
 *
 */

/*
 * 显示财务类单据
 */
@SuppressWarnings("serial")
public class ShowDoc extends Show{
	private GetDocument get;
	private DocumentVO vo;
	//-------------------------------------------------------------------------
	public ShowDoc(String id) {
		super(id);
		get = new AccountBL();
		//获取vo
		vo = get.findOneDocument(id);
		if(vo == null) {
			JOptionPane.showMessageDialog(null, "无单据信息！", "警告", JOptionPane.WARNING_MESSAGE);
			dispose();
		}
		//设置组件
		pane2 = new JPanel();
		pane2.setLayout(new GridLayout(5, 2, 40, 30));
		pane2.setBorder(BorderFactory.createEmptyBorder(20, 40, 0, 40));
		
		label1 = new JLabel("编号：", JLabel.RIGHT);
		label2 = new JLabel(vo.id, JLabel.LEFT);
		label3 = new JLabel(vo.customerType + "：", JLabel.RIGHT);
		label4 = new JLabel(vo.customerName, JLabel.LEFT);
		label5 = new JLabel("操作员：", JLabel.RIGHT);
		label6 = new JLabel(vo.operator, JLabel.LEFT);
		label7 = new JLabel("总额：", JLabel.RIGHT);
		label8 = new JLabel(""+vo.total, JLabel.LEFT);
		label9 = new JLabel("转账金额：", JLabel.RIGHT);
		
		pane2.add(label1);
		pane2.add(label2);
		pane2.add(label3);
		pane2.add(label4);
		pane2.add(label5);
		pane2.add(label6);
		pane2.add(label7);
		pane2.add(label8);
		pane2.add(label9);
		
		this.add(pane2, BorderLayout.NORTH);
		//设置表格数据
		data = new Object[vo.transferList.size()][3];
		for(int i = 0; i < vo.transferList.size(); i++) {
			String[] temp = vo.transferList.get(i).split(";");
			data[i][0] = temp[0];
			data[i][1] = temp[1];
			data[i][2] = temp[2];
		}
		columnName = new String[]{"银行账户", "转账金额", "备注"};
		table = new JTable(new TableModel(data, columnName) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		sp = new JScrollPane(table);
		
		this.add(sp, BorderLayout.CENTER);
		//监听
		exit.addActionListener(new ExitListener());
		//-----------------------------------------------------------------------------------------------
		this.setVisible(true);
	}
	//------------------------------------------------------------------------------------------------------
	//监听内部类
	private class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
}
