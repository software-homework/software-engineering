package ui.manager.promotion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import ui.manager.ManagerFrame;
import vo.LogVO;
import vo.manager.TotalPromVO;
import businesslogic.LogBL;
import businesslogic.manager.PromotionBL;
import businesslogicservice.LogBLService;
import businesslogicservice.manager.PromotionBLService;

/**
 * 
 * @date 2014年12月12日
 * @time 下午7:20:51
 * @author stk
 *
 */

/*
 * 针对总价的促销策略主panel
 */
@SuppressWarnings("serial")
public class TotalPromPane extends PromotionPane{
	private PromotionBLService bl;
	//------------------------------------------------------
	public TotalPromPane(ManagerFrame frame) {
		super(frame);
		this.refresh();
		//按钮监听
		add.addActionListener(new AddListener());
		del.addActionListener(new DelListener());
	}
	//--------------------------------------------------------------------
	public void refresh() {
		bl = new PromotionBL();
		ArrayList<TotalPromVO> list = bl.shoTotalProm();//取得数据
		columnName = new String[]{"", "日期", "总价", "赠品名称", "代金券"};
		data = new Object[list.size()][5];
		for(int i = 0; i < list.size(); i++) {
			data[i][0] = Boolean.FALSE;
			data[i][1] = list.get(i).startTime +"-"+ list.get(i).endTime;
			data[i][2] = list.get(i).total;
			data[i][3] = list.get(i).goodsName;
			data[i][4] = list.get(i).voucher;
		}
		this.showTable();
	}
	//监听内部类
	private class AddListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//状态栏设置
			frame.setStatusBar("添加针对总价的促销策略");
			
			new AddTotalPromDialog(TotalPromPane.this);
		}
	}
	
	private class DelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(JOptionPane.showConfirmDialog(null,"确定要删除吗？","提示",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				bl = new PromotionBL();
				boolean judge = true;
				for(int i = 0; i < data.length; i++) {
					if(data[i][0] == Boolean.TRUE) {
						judge = judge && bl.delTotalProm(new TotalPromVO(((String)data[i][1]).substring(0, 10),
																		 ((String)data[i][1]).substring(11),
																		 Double.parseDouble(data[i][2].toString()),
																		 (String)data[i][3],
																		 Double.parseDouble(data[i][4].toString())));
					}
				}
				if(judge) {
					//刷新表格
					refresh();
					//状态栏设置
					frame.setStatusBar("删除成功");
					LogBLService logBLService = new LogBL();
					logBLService.addLog(new LogVO(frame.getUserName(), "删除针对总价的促销策略"));
				}else {
					JOptionPane.showMessageDialog(null, "删除失败！", "错误", JOptionPane.ERROR_MESSAGE);
					//刷新表格
					refresh();
					//状态栏设置
					frame.setStatusBar("删除失败");
				}
			}
		}
	}
}
