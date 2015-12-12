package ui.manager.approval;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ui.TableModel;
import vo.LogVO;
import vo.logistics.PresentVO;
import businesslogic.LogBL;
import businesslogic.manager.ApprovalBL;
import businesslogicservice.LogBLService;
import businesslogicservice.manager.ApprovalBLService;

/**
 * 
 * @date 2014年12月12日
 * @time 下午7:16:39
 * @author stk
 *
 */

/*
 * 赠送单详细信息
 */
@SuppressWarnings("serial")
public class ShowPresent extends ShowApproval{
	private ApprovalBLService bl;
	private PresentVO vo;
	//---------------------------------------------------------------------------------------
	public ShowPresent(ApprovalPane pane, String id) {
		super(pane, id);
		//获取vo
		bl = new ApprovalBL();
		vo = bl.finPresent(id);
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
		update.addActionListener(new UpdateListener());
		exit.addActionListener(new ExitListener());
		//-----------------------------------------------------------------------------------------------
		this.setVisible(true);
	}
	//------------------------------------------------------------------------------------------------------
	//监听内部类
	private class UpdateListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(update.getText().equals("修改")) {
				remove(table);
				//表格可编辑
				columnName = new String[]{"商品名称", "商品型号", "数量（可修改）"};
				table = new JTable(new TableModel(data, columnName) {
					public boolean isCellEditable(int row, int column) {
						return (column == 2);//设置可编辑
					}
				});
				sp.setViewportView(table);
				revalidate();
				update.setText("确认");//按钮变化
			}else if(update.getText().equals("确认")) {
				//修改vo
				ArrayList<String> temp = new ArrayList<String>();
				for(int i = 0; i < data.length; i++) {
					temp.add((String)data[i][0] + (String)data[i][1] + (String)data[i][2]);
				}
				vo.presentList = temp;
				if(JOptionPane.showConfirmDialog(null, "确认修改吗？", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					update.setText("完成");//按钮变化
				}
			}else if(update.getText().equals("完成")) {
				//状态栏设置
				if(bl.modifyPresent(vo)) {
					pane.frame.setStatusBar("修改成功");
					LogBLService logBLService = new LogBL();
					logBLService.addLog(new LogVO(pane.frame.getUserName(), "修改赠送单"));
				}else {
					pane.frame.setStatusBar("修改失败");
				}
				dispose();
				//刷新表格
				pane.refresh();
			}
		}
	}
	
	private class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(JOptionPane.showConfirmDialog(null, "确定要关闭吗？", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				dispose();
			}
		}
	}
}
