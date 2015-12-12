package ui.manager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import ui.manager.approval.ApprovalPane;
import ui.manager.check.CheckCondition;
import ui.manager.check.CheckDetail;
import ui.manager.check.CheckHistory;
import ui.manager.promotion.BargainPane;
import ui.manager.promotion.CustPromPane;
import ui.manager.promotion.TotalPromPane;
import ui.user.LoginFrame;
import ui.user.UserInfo;
import vo.user.UserVO;

/**
 * 
 * @date 2014年12月11日
 * @time 下午4:48:02
 * @author stk
 *
 */

/*
 * 总经理界面frame
 */
@SuppressWarnings("serial")
public class ManagerFrame extends JFrame{
	private UserVO vo;
	private JSplitPane splitPane;
	private JPanel pane;
	private ApprovalPane approvalPane;
	private JToolBar toolBar;
	private JLabel statusBar;
	private JMenuBar menuBar;
	private JMenu system;
	private JMenu check;
	private JMenu promotion;
	private JMenuItem user;
	private JMenuItem log;
	private JMenuItem logout;
	private JMenuItem exit;
	private JMenuItem checkDetail;
	private JMenuItem checkHistory;
	private JMenuItem checkCondition;
	private JMenuItem custProm;
	private JMenuItem bargain;
	private JMenuItem totalProm;
	//-----------------------------------------------------------
	public ManagerFrame(UserVO vo) {
		this.vo = vo;
		//定义界面大小
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int frameHeight = screenSize.height * 3 / 4;
		int frameWidth = frameHeight * 7 / 4;
		this.setBounds((screenSize.width - frameWidth) / 2, (screenSize.height - frameHeight) / 2, frameWidth, frameHeight);
		//设置状态栏
		statusBar = new JLabel("欢迎使用进销存系统！");
		toolBar = new JToolBar();
		toolBar.add(statusBar);
		toolBar.setFloatable(false);
		
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		//设置菜单栏
		menuBar = new JMenuBar();
		
		system = new JMenu("系统");
		check = new JMenu("查看");
		promotion = new JMenu("促销");
		
		user = new JMenuItem("用户信息");
		log = new JMenuItem("查看日志");
		logout = new JMenuItem("注销用户");
		exit  = new JMenuItem("退出系统");
		checkDetail = new JMenuItem("销售明细表");
		checkHistory = new JMenuItem("经营历程表");
		checkCondition = new JMenuItem("经营情况表");
		custProm = new JMenuItem("针对客户的促销策略");
		bargain = new JMenuItem("组合商品降价策略");
		totalProm = new JMenuItem("针对总价的促销策略");
		
		menuBar.add(system);
		menuBar.add(check);
		menuBar.add(promotion);
		
		system.add(user);
		system.add(log);
		system.add(logout);
		system.add(exit);
		check.add(checkDetail);
		check.add(checkHistory);
		check.add(checkCondition);
		promotion.add(custProm);
		promotion.add(bargain);
		promotion.add(totalProm);
		
		this.setJMenuBar(menuBar);
		//设置主面板
		pane = new JPanel();
		//设置审批面板
		approvalPane = new ApprovalPane(this);
		//设置分隔面板
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, approvalPane, pane);
		splitPane.setOneTouchExpandable(true);
		
		this.getContentPane().add(splitPane, BorderLayout.CENTER);
		//---------------------------------------------------------------
		this.setTitle("进销存系统-总经理");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		splitPane.setDividerLocation(0.2);//设置分隔窗口大小
		//监听
		user.addActionListener(new UserListener());
		log.addActionListener(new LogListener());
		logout.addActionListener(new LogoutListener());
		exit.addActionListener(new ExitListener());
		checkDetail.addActionListener(new CheckDetailListener());
		checkHistory.addActionListener(new CheckHistoryListener());
		checkCondition.addActionListener(new CheckConditionListener());
		custProm.addActionListener(new CustPromListener());
		bargain.addActionListener(new BargainListener());
		totalProm.addActionListener(new TotalPromListener());
	}
	//------------------------------------------------------------------------
	public void setStatusBar(String msg) {
		statusBar.setText(msg);
	}
	
	public String getUserName() {
		return vo.name;
	}
	//监听内部类
	private class UserListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			new UserInfo(vo, ManagerFrame.this);
		}
	}
	
	private class LogListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			new Log();
		}
	}
	
	private class LogoutListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(JOptionPane.showConfirmDialog(null, "确定要注销吗？", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				dispose();
				new LoginFrame();
			}
		}
	}
	
	private class ExitListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(JOptionPane.showConfirmDialog(null, "确定要退出吗？", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				dispose();
			}
		}
	}
	
	private class CheckDetailListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			splitPane.remove(pane);
			
			pane = new CheckDetail(ManagerFrame.this);
			splitPane.setRightComponent(pane);
			splitPane.setDividerLocation(0.2);
			//状态栏设置
			statusBar.setText("查看销售明细表");
			
			revalidate();
		}
	}
	
	private class CheckHistoryListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			splitPane.remove(pane);
			
			pane = new CheckHistory(ManagerFrame.this);
			splitPane.setRightComponent(pane);
			splitPane.setDividerLocation(0.2);
			//状态栏设置
			statusBar.setText("查看经营历程表");
			
			revalidate();
		}
	}
	
	private class CheckConditionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			splitPane.remove(pane);
			
			pane = new CheckCondition(ManagerFrame.this);
			splitPane.setRightComponent(pane);
			splitPane.setDividerLocation(0.2);
			//状态栏设置
			statusBar.setText("查看经营情况表");
			
			revalidate();
		}
	}

	private class CustPromListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			splitPane.remove(pane);
			
			pane = new CustPromPane(ManagerFrame.this);
			splitPane.setRightComponent(pane);
			splitPane.setDividerLocation(0.2);
			//状态栏设置
			statusBar.setText("制定针对客户的促销策略");
			
			revalidate();
		}
	}
	
	private class BargainListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			splitPane.remove(pane);
			
			pane = new BargainPane(ManagerFrame.this);
			splitPane.setRightComponent(pane);
			splitPane.setDividerLocation(0.2);
			//状态栏设置
			statusBar.setText("制定组合商品降价策略");
			
			revalidate();
		}
	}
	
	private class TotalPromListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			splitPane.remove(pane);
			
			pane = new TotalPromPane(ManagerFrame.this);
			splitPane.setRightComponent(pane);
			splitPane.setDividerLocation(0.2);
			//状态栏设置
			statusBar.setText("制定针对总价的促销策略");
			
			revalidate();
		}
	}
}
