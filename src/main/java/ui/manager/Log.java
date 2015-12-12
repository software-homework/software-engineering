package ui.manager;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ui.TableModel;
import vo.LogVO;
import businesslogic.LogBL;
import businesslogicservice.LogBLService;

/**
 * 
 * @date 2014年12月11日
 * @time 下午4:32:27
 * @author stk
 *
 */

/*
 * 查看日志
 */
@SuppressWarnings("serial")
public class Log extends JDialog{
	public Log() {
		//定义界面大小
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int frameHeight = screenSize.height * 3 / 4;
		int frameWidth = frameHeight * 7 / 4;
		this.setBounds((screenSize.width - frameWidth) / 2, (screenSize.height - frameHeight) / 2, frameWidth, frameHeight);
		//----------------------------------------------------------------
		LogBLService get = new LogBL();
		ArrayList<LogVO> list = new ArrayList<LogVO>();
		list = get.showLog();
		String[][] data = new String[list.size()][4];
		for (int i = 0; i < data.length; i++) {
			data[i][0] = list.get(i).date;
			data[i][1] = list.get(i).time;
			data[i][2] = list.get(i).user;
			data[i][3] = list.get(i).action;
		}
		String[] columnName = {"日期", "时间", "操作员", "操作"};
		this.getContentPane().add(new JScrollPane(new JTable(new TableModel(data, columnName) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		})));
		//---------------------------------------------------------------
		this.setTitle("查看日志");
		this.setVisible(true);
	}
}
