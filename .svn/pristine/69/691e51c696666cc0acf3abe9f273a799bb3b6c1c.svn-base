package ui.manager.promotion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import ui.manager.ManagerFrame;
import vo.LogVO;
import vo.manager.CustPromVO;
import businesslogic.LogBL;
import businesslogic.manager.PromotionBL;
import businesslogicservice.LogBLService;
import businesslogicservice.manager.PromotionBLService;

/**
 * 
 * @date 2014年12月12日
 * @time 下午7:20:31
 * @author stk
 *
 */

/*
 * 针对客户的促销策略主panel
 */
@SuppressWarnings("serial")
public class CustPromPane extends PromotionPane{
	private PromotionBLService bl;
	//----------------------------------------------
	public CustPromPane(ManagerFrame frame) {
		super(frame);
		this.refresh();
		//按钮监听
		add.addActionListener(new AddListener());
		del.addActionListener(new DelListener());
	}
	//-------------------------------------------------------------------
	public void refresh() {
		bl = new PromotionBL();
		ArrayList<CustPromVO> list = bl.shoCustProm();//取得数据
		columnName = new String[]{"", "日期", "客户类型", "赠品名称", "折让金额", "代金券"};
		data = new Object[list.size()][6];
		for(int i = 0; i < list.size(); i++) {
			data[i][0] = Boolean.FALSE;
			data[i][1] = list.get(i).startTime +"-"+ list.get(i).endTime;
			data[i][2] = list.get(i).type;
			data[i][3] = list.get(i).goodsName;
			data[i][4] = list.get(i).discount;
			data[i][5] = list.get(i).voucher;
		}
		this.showTable();
	}
	//监听内部类
	private class AddListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//状态栏设置
			frame.setStatusBar("添加针对客户的促销策略");
			
			new AddCustPromDialog(CustPromPane.this);
		}
	}
	
	private class DelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(JOptionPane.showConfirmDialog(null,"确定要删除吗？","提示",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				bl = new PromotionBL();
				boolean judge = true;
				for(int i = 0; i < data.length; i++) {
					if(data[i][0] == Boolean.TRUE) {
						judge = judge && bl.delCustProm(new CustPromVO(((String)data[i][1]).substring(0, 10),
																	   ((String)data[i][1]).substring(11),
																	   (String)data[i][2],
																	   (String)data[i][3],
																	   Double.parseDouble(data[i][4].toString()),
																	   Double.parseDouble(data[i][5].toString())));
					}
				}
				if(judge) {
					//刷新表格
					refresh();
					//状态栏设置
					frame.setStatusBar("删除成功");
					LogBLService logBLService = new LogBL();
					logBLService.addLog(new LogVO(frame.getUserName(), "删除针对客户的促销策略"));
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
