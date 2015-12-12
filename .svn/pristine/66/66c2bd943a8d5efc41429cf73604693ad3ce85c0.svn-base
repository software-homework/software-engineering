package ui.manager.check;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

import ui.TableModel;
import ui.manager.ManagerFrame;

/**
 * 
 * @date 2014年12月7日
 * @time 下午4:08:25
 * @author stk
 *
 */

/*
 * 查看经营历程表
 */
@SuppressWarnings("serial")
public class CheckHistory extends Check{
	//----------------------------------------------------------------------
	public CheckHistory(ManagerFrame frame) {
		super(frame);
		columnName = new String[]{"编号"};
		//按钮不可用
		output.setEnabled(false);
		output.setVisible(false);
		//监听
		search.addActionListener(new SearchListener());
	}
	//----------------------------------------------------------------------
	public void showTable() {
		this.remove(table);//删除原先的表格
		table = new JTable(new TableModel(data, columnName) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		//监听
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					String id = (String)data[table.getSelectedRow()][0];
					switch(id.split("-")[0]) {
					case "JHD" : {}
					case "JHTHD" : {
						new ShowImp(id);
						break;
					}
					case "XSD" : {}
					case "XSTHD" : {
						new ShowSal(id);
						break;
					}
					case "SKD" : {}
					case "FKD" : {
						new ShowDoc(id);
						break;
					}
					case "BYD" : {
						new ShowStoOf(id);
						break;
					}
					case "BSD" : {
						new ShowStoL(id);
						break;
					}
					case "ZSD" : {
						new ShowPre(id);
						break;
					}
					case "XJFYD" : {
						new ShowCaEx(id);
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
	private class SearchListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new SearchHistory(CheckHistory.this);
		}
	}
}
