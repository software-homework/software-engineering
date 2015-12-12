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
import vo.courier.ImportVO;
import businesslogic.finance.AccountBL;
import businesslogicservice.manager.GetDocument;

/**
 * 
 * @date 2014年12月12日
 * @time 下午7:13:36
 * @author stk
 *
 */

/*
 * 显示快递接类单据
 */
@SuppressWarnings("serial")
public class ShowImp extends Show{
	private GetDocument get;
	private ImportVO vo;
	//-------------------------------------------------------------------
	public ShowImp(String id) {
		super(id);
		get = new AccountBL();
		//获取vo
		vo = get.findOneImport(id);
		if(vo == null) {
			JOptionPane.showMessageDialog(null, "无单据信息！", "警告", JOptionPane.WARNING_MESSAGE);
			dispose();
		}
		//设置组件
		pane2 = new JPanel();
		pane2.setLayout(new GridLayout(7, 2, 40, 30));
		pane2.setBorder(BorderFactory.createEmptyBorder(20, 40, 0, 40));
		
		label1 = new JLabel("单据编号：", JLabel.RIGHT);
		label2 = new JLabel(vo.id, JLabel.LEFT);
		label3 = new JLabel("供应商：", JLabel.RIGHT);
		label4 = new JLabel(vo.customer, JLabel.LEFT);
		label5 = new JLabel("仓库：", JLabel.RIGHT);
		label6 = new JLabel(""+vo.repertory, JLabel.LEFT);
		label7 = new JLabel("操作员：", JLabel.RIGHT);
		label8 = new JLabel(vo.user, JLabel.LEFT);
		label9 = new JLabel("总额：", JLabel.RIGHT);
		label10 = new JLabel(""+vo.total, JLabel.LEFT);
		label11 = new JLabel("备注：", JLabel.RIGHT);
		label12 = new JLabel(vo.remakes, JLabel.LEFT);
		label13 = new JLabel("快递信息：", JLabel.RIGHT);
		
		pane2.add(label1);
		pane2.add(label2);
		pane2.add(label3);
		pane2.add(label4);
		pane2.add(label5);
		pane2.add(label6);
		pane2.add(label7);
		pane2.add(label8);
		pane2.add(label9);
		pane2.add(label10);
		pane2.add(label11);
		pane2.add(label12);
		pane2.add(label13);
		
		this.add(pane2, BorderLayout.NORTH);
		//设置表格数据
		data = new Object[vo.importList.size()][3];
		for(int i = 0; i < vo.importList.size(); i++) {
			data[i][0] = vo.importList.get(i).commodityname;
			data[i][1] = vo.importList.get(i).number;
			data[i][2] = vo.importList.get(i).priceIn;
		}
		columnName = new String[]{"快递名称", "数量", "单价"};
		table = new JTable(new TableModel(data, columnName) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		sp = new JScrollPane(table);
		
		this.add(sp, BorderLayout.CENTER);
		//监听
		exit.addActionListener(new ExitListener());
		//--------------------------------------------------------------------------------------
		this.setVisible(true);
	}
	//------------------------------------------------------------
	//监听内部类
	private class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
}
