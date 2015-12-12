package ui.manager.approval;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ui.TableModel;
import ui.manager.ManagerFrame;
import vo.LogVO;
import businesslogic.LogBL;
import businesslogic.manager.ApprovalBL;
import businesslogicservice.LogBLService;
import businesslogicservice.manager.ApprovalBLService;

/**
 * 
 * @date 2014年12月13日
 * @time 下午3:53:14
 * @author stk
 *
 */

/*
 * 审批单据panel
 */
@SuppressWarnings("serial")
public class ApprovalPane extends JPanel{
	private ApprovalBLService bl;
	ManagerFrame frame;
	private JPanel pane;
	private JTable table;
	private JScrollPane sp;
	private JLabel label;
	private JButton confirm;
	private JButton refresh;
	private Object[][] data;
	private String[] columnName;
	//------------------------------------------------
	public ApprovalPane(ManagerFrame frame) {
		this.frame = frame;
		
		this.setLayout(new BorderLayout());
		//次面板
		pane = new JPanel();
		pane.setLayout(new FlowLayout(FlowLayout.CENTER));
		//设置按钮
		confirm = new JButton("审批");
		refresh = new JButton("刷新");
		
		pane.add(confirm);
		pane.add(refresh);
		
		this.add(pane, BorderLayout.SOUTH);
		//设置标签
		label = new JLabel("审批单据", JLabel.CENTER);
		
		this.add(label, BorderLayout.NORTH);
		//设置表格
		table = new JTable();
		sp = new JScrollPane(table);
		this.add(sp, BorderLayout.CENTER);
		
		this.refresh();
		//按钮监听
		confirm.addActionListener(new ConfirmListener());
		refresh.addActionListener(new RefreshListener());
	}
	//-----------------------------------------------------------------------
	public void refresh() {
		bl = new ApprovalBL();
		String[] temp = bl.show();//获取数据
		columnName = new String[]{"", "编号"};
		data = new Object[temp.length][2];
		for(int i = 0; i < temp.length; i++) {
			data[i][0] = Boolean.FALSE;
			data[i][1] = temp[i];
		}
		this.remove(table);
		confirm.setEnabled(false);//审批按钮设为不可用
		table = new JTable(new TableModel(data, columnName));
		table.getColumnModel().getColumn(0).setMaxWidth(30);//设置第一列宽度
		//监听
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1) {
					//单击
					boolean judge = false;
					for(int i = 0; i < data.length; i++) {
						if(data[i][0] == Boolean.TRUE) {
							judge = true;
							break;
						}
					}
					if(judge) {
						confirm.setEnabled(true);
					}else {
						confirm.setEnabled(false);
					}
				}else if(e.getClickCount() == 2) {
					//双击
					String id = (String)data[table.getSelectedRow()][1];
					switch(id.split("-")[0]) {
					case "JHD" : {}
					case "JHTHD" : {
						new ShowImport(ApprovalPane.this, id);
						break;
					}
					case "XSD" : {}
					case "XSTHD" : {
						new ShowSale(ApprovalPane.this, id);
						break;
					}
					case "SKD" : {}
					case "FKD" : {
						new ShowDocument(ApprovalPane.this, id);
						break;
					}
					case "BYD" : {
						new ShowStockOverflow(ApprovalPane.this, id);
						break;
					}
					case "BSD" : {
						new ShowStockLoss(ApprovalPane.this, id);
						break;
					}
					case "ZSD" : {
						new ShowPresent(ApprovalPane.this, id);
						break;
					}
					case "XJFYD" : {
						new ShowCashExpenses(ApprovalPane.this, id);
						break;
					}
					default : {
						System.out.println("单据编号错误。");
					}
					}
				}
			}
		});
		sp.setViewportView(table);
		revalidate();
	}
	//监听内部类
	private class ConfirmListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int judge = JOptionPane.showConfirmDialog(null, "单据将通过审批？通过请选择“是”， 不通过请选择“否”。", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			int approval = 0;
			//判断修改状态
			if(judge == JOptionPane.YES_OPTION) {
				approval = 1;
			}else if(judge == JOptionPane.NO_OPTION) {
				approval = 2;
			}
			//组织数据
			ArrayList<String> list = new ArrayList<String>();
			for(int i = 0; i < data.length; i++) {
				if(data[i][0] == Boolean.TRUE) {
					list.add((String)data[i][1]);
				}
			}
			String[] temp = list.toArray(new String[list.size()]);
			bl = new ApprovalBL();
			int[] condition = bl.approve(temp, approval);
			String msg = "审批单据成功"+ condition[0] +"项，失败"+ condition[1] +"项。";
			LogBLService logBLService = new LogBL();
			logBLService.addLog(new LogVO(frame.getUserName(), msg));
			JOptionPane.showMessageDialog(null, msg, "信息", JOptionPane.INFORMATION_MESSAGE);
			refresh();
		}
	}
	
	private class RefreshListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			refresh();
		}
	}
}
