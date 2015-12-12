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
import vo.stockManage.PresentVO;
import businesslogic.finance.AccountBL;
import businesslogicservice.manager.GetDocument;

/**
 * 
 * @date 2014年12月12日
 * @time 下午7:14:04
 * @author stk
 *
 */

/*
 * 显示赠送单
 */
@SuppressWarnings("serial")
public class ShowPre extends Show{
	private GetDocument get;
	private PresentVO vo;
	//-------------------------------------------------------------------
	public ShowPre(String id) {
		super(id);
		get = new AccountBL();
		//获取vo
		vo = get.findOnePresent(id);
		if(vo == null) {
			JOptionPane.showMessageDialog(null, "无单据信息！", "警告", JOptionPane.WARNING_MESSAGE);
			dispose();
		}
		//设置组件
		pane2 = new JPanel();
		pane2.setLayout(new GridLayout(1, 2, 40, 30));
		pane2.setBorder(BorderFactory.createEmptyBorder(20, 40, 0, 40));
		
		label1 = new JLabel("编号：", JLabel.RIGHT);
		label2 = new JLabel(vo.id, JLabel.LEFT);
		
		pane2.add(label1);
		pane2.add(label2);
		
		this.add(pane2, BorderLayout.NORTH);
		//设置表格数据
		data = new Object[vo.presentList.size()][3];
		for(int i = 0; i < vo.presentList.size(); i++) {
			String[] temp = vo.presentList.get(i).split(";");
			data[i][0] = temp[0];
			data[i][1] = temp[1];
			data[i][2] = temp[2];
		}
		columnName = new String[]{"商品名称", "商品型号", "数量"};
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
