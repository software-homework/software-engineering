package ui.manager.check;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ui.TableModel;
import ui.manager.ManagerFrame;

/**
 * 
 * @date 2014年12月5日
 * @time 下午8:43:59
 * @author stk
 *
 */

/*
 * 查看所有表格的父类
 */
@SuppressWarnings("serial")
public class Check extends JPanel{
	ManagerFrame frame;//底层frame
	JPanel pane;//次面板
	JTable table;
	JScrollPane sp;
	JButton search;
	JButton output;
	Object[][] data;
	String[] columnName;
	//---------------------------------------------------------------------
	public Check(ManagerFrame frame) {
		this.frame = frame;//底层frame
		this.setLayout(new BorderLayout());
		//次面板
		pane = new JPanel();
		pane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		//设置按钮
		search = new JButton("搜索");
		output = new JButton("导出表格");
		
		pane.add(search);
		pane.add(output);
		
		this.add(pane, BorderLayout.NORTH);
		//设置表格
		table = new JTable();
		sp = new JScrollPane(table);
		this.add(sp, BorderLayout.CENTER);
	}
	//-----------------------------------------------------------------------
	public void showTable() {
		this.remove(table);//删除原先的表格
		table = new JTable(new TableModel(data, columnName) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		sp.setViewportView(table);
		revalidate();
	}
}
