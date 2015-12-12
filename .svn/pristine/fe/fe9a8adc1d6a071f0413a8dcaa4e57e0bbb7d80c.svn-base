package ui.manager.promotion;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ui.TableModel;
import ui.manager.ManagerFrame;

/**
 * 
 * @date 2014年12月3日
 * @time 下午9:10:41
 * @author stk
 *
 */

/*
 * 总经理所有panel的父类
 */
@SuppressWarnings("serial")
public class PromotionPane extends JPanel{
	ManagerFrame frame;//底层frame
	JPanel pane;
	JTable table;
	JScrollPane sp;
	JButton add;
	JButton del;
	Object[][] data;
	String[] columnName;
	//-----------------------------------------------
	public PromotionPane(ManagerFrame frame) {
		this.frame = frame;//底层frame
		this.setLayout(new BorderLayout());
		//次面板
		pane = new JPanel();
		pane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		//设置按钮
		add = new JButton("添加");
		del = new JButton("删除");
		
		pane.add(add);
		pane.add(del);
		
		this.add(pane, BorderLayout.NORTH);
		//设置表格
		table = new JTable();
		sp = new JScrollPane(table);
		this.add(sp, BorderLayout.CENTER);
	}
	//-------------------------------------------------------
	public void showTable() {
		this.remove(table);//删除原先的表格
		del.setEnabled(false);//将删除按钮设为不可用
		table = new JTable(new TableModel(data, columnName));
		table.getColumnModel().getColumn(0).setMaxWidth(30);//设置第一列宽度
		//监听
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				boolean judge = false;
				for(int i = 0; i < data.length; i++) {
					if(data[i][0] == Boolean.TRUE) {
						judge = true;
						break;
					}
				}
				if(judge) {
					del.setEnabled(true);
				}else {
					del.setEnabled(false);
				}
			}
		});
		sp.setViewportView(table);
		revalidate();
	}
}
