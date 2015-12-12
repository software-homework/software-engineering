package ui.manager.approval;

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
import vo.LogVO;
import vo.courier.ImportVO;
import businesslogic.LogBL;
import businesslogic.manager.ApprovalBL;
import businesslogicservice.LogBLService;
import businesslogicservice.manager.ApprovalBLService;

/**
 * 
 * @date 2014年12月12日
 * @time 下午7:16:10
 * @author stk
 *
 */

/*
 * 进货类单据详细信息弹窗
 */
@SuppressWarnings("serial")
public class ShowImport extends ShowApproval{
	private ApprovalBLService bl;
	private ImportVO vo;
	//------------------------------------------------------
	public ShowImport(ApprovalPane pane, String id) {
		super(pane, id);
		//获取vo
		bl = new ApprovalBL();
		vo = bl.finImport(id);
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
		label13 = new JLabel("商品信息：", JLabel.RIGHT);
		
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
		columnName = new String[]{"商品名称", "数量", "单价"};
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
		//--------------------------------------------------------------------------------------
		this.setVisible(true);
	}
	//------------------------------------------------------------
	//监听内部类
	private class UpdateListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(update.getText().equals("修改")) {
				remove(table);
				//表格可编辑
				columnName = new String[]{"商品名称", "数量（可修改）", "单价（可修改）"};
				table = new JTable(new TableModel(data, columnName) {
					public boolean isCellEditable(int row, int column) {
						return (column != 0);//设置可编辑
					}
				});
				sp.setViewportView(table);
				revalidate();
				update.setText("确认");//按钮变化
			}else if(update.getText().equals("确认")){
				//修改vo
				double total = 0;
				for(int i = 0; i < vo.importList.size(); i++) {
					vo.importList.get(i).number = Integer.parseInt(data[i][1].toString());
					vo.importList.get(i).priceIn = Double.parseDouble(data[i][2].toString());
					total += Integer.parseInt(data[i][1].toString()) * Double.parseDouble(data[i][2].toString());
				}
				vo.total = total;
				
				label10.setText(""+vo.total);
				
				if(JOptionPane.showConfirmDialog(null, "确认修改吗？", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					update.setText("完成");//按钮变化
				}
			}else if(update.getText().equals("完成")) {
				//状态栏设置
				if(bl.modifyImport(vo)) {
					pane.frame.setStatusBar("修改成功");
					LogBLService logBLService = new LogBL();
					logBLService.addLog(new LogVO(pane.frame.getUserName(), "修改进货类单据"));
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
