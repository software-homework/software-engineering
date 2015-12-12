package ui.manager.check;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import ui.manager.ManagerFrame;
import vo.LogVO;
import businesslogic.LogBL;
import businesslogic.manager.CheckBL;
import businesslogicservice.LogBLService;
import businesslogicservice.manager.CheckBLService;

/**
 * 
 * @date 2014年12月12日
 * @time 下午7:17:35
 * @author stk
 *
 */

/*
 * 查看销售明细表
 */
@SuppressWarnings("serial")
public class CheckDetail extends Check{
	private CheckBLService bl;
	//------------------------------------------------------------------------
	public CheckDetail(ManagerFrame frame) {
		super(frame);
		columnName = new String[]{"时间", "快递名", "型号", "数量", "单价", "总额"};
		//监听
		search.addActionListener(new SearchListener());
		output.addActionListener(new OutputListener());
	}
	//----------------------------------------------------------------------
	//监听内部类
	private class SearchListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new SearchDetail(CheckDetail.this);
		}
	}
	
	private class OutputListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			bl = new CheckBL();
			//状态栏设置
			if(bl.output("salesdetail.xls", CheckDetail.this.data, new String[]{"销售明细表", "时间", "快递名", "型号", "数量", "单价", "总额"})) {
				JOptionPane.showMessageDialog(null, "导出表格成功！", "信息", JOptionPane.INFORMATION_MESSAGE);
				frame.setStatusBar("导出表格成功");
				LogBLService logBLService = new LogBL();
				logBLService.addLog(new LogVO(frame.getUserName(), "导出销售明细表"));
			}else {
				JOptionPane.showMessageDialog(null, "导出表格失败！", "错误", JOptionPane.ERROR_MESSAGE);
				frame.setStatusBar("导出表格失败");
			}
		}
	}
}
