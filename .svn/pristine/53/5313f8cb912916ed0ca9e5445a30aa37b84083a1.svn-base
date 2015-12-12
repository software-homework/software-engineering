package ui.salesman;

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
import javax.swing.JToolBar;

import ui.salesman.customer.CustomerPanel;
import ui.salesman.importmanage.ImportPanel;
import ui.salesman.salesmanage.SalesPanel;
import ui.user.LoginFrame;
import ui.user.UserInfo;
import vo.user.UserVO;
import businesslogic.salesman.CustomerBL;
import businesslogic.salesman.ImportBL;
import businesslogic.salesman.SalesBL;
import businesslogicservice.salesman.CustomerBLService;
import businesslogicservice.salesman.ImportBLService;
import businesslogicservice.salesman.SalesBLService;

@SuppressWarnings("serial")
public class SalesMain extends JFrame{
	CustomerBLService customerbl;
	ImportBLService importbl;
	SalesBLService salesbl;
	JMenuBar mb;
	JMenu system;
	JMenu operation;
	JMenuItem logout,exit,key,customer,addimport,addsales;
	JPanel panel;
	private JLabel statusBar;
	JToolBar toolBar;
	private String username;
	private String userlevel;
	UserVO uvo;
	public SalesMain(UserVO uservo){
		customerbl=new CustomerBL();
		importbl=new ImportBL();
		salesbl=new SalesBL();
		uvo=uservo;
		setUsername(uservo.name);
		setUserlevel(uservo.permission);
		
		//界面定义
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int frameHeight = screenSize.height * 3 / 4;
		int frameWidth = frameHeight * 7 / 4;
		this.setBounds((screenSize.width - frameWidth) / 2, (screenSize.height - frameHeight) / 2, frameWidth, frameHeight);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setTitle("进销存系统-销售管理人员"+"    "+"账户名: "+uservo.name+"  "+"权限: "+uservo.permission);
		
		
		//菜单定义
		mb = new JMenuBar();
		system = new JMenu("系统");
		operation = new JMenu("销售操作");
		logout = new JMenuItem("注销");
		exit = new JMenuItem("退出系统");
		key = new JMenuItem("用户信息");
		customer = new JMenuItem("客户管理");
		addimport = new JMenuItem("进货单管理");
		addsales = new JMenuItem("销售单管理");
		system.add(key);
		system.add(logout);
		system.add(exit);
		operation.add(customer);
		operation.add(addimport);
		operation.add(addsales);
		mb.add(system);
		mb.add(operation);
		this.setJMenuBar(mb);
		
		//监听菜单
		ActionListener keylistener=new keyListener();
		key.addActionListener(keylistener);
		ActionListener logoutlistener=new logoutListener();
		logout.addActionListener(logoutlistener);
		ActionListener exitlistener=new exitListener();
		exit.addActionListener(exitlistener);
		ActionListener customerlistener=new customerListener();
		customer.addActionListener(customerlistener);
		ActionListener addimportlistener=new addimportListener();
		addimport.addActionListener(addimportlistener);
		ActionListener addsaleslistener=new addsalesListener();
		addsales.addActionListener(addsaleslistener);
		//客户管理
		panel=new CustomerPanel(customerbl.ShowC(),SalesMain.this);
		this.getContentPane().add(panel,BorderLayout.CENTER);
		//设置状态栏
		statusBar = new JLabel(" ");
		toolBar = new JToolBar();
		toolBar.add(statusBar);
		toolBar.setFloatable(false);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);

		
		this.setVisible(true);
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setStatusBar(String status){
		statusBar.setText(status);
	}

	public String getUserlevel() {
		return userlevel;
	}

	public void setUserlevel(String userlevel) {
		this.userlevel = userlevel;
	}

	class customerListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			getContentPane().remove(panel);
			
			panel=new CustomerPanel(customerbl.ShowC(),SalesMain.this);
			getContentPane().add(panel,BorderLayout.CENTER);
			
			getContentPane().revalidate();
		}
	}
	
	class addimportListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			getContentPane().remove(panel);
			
			panel=new ImportPanel(SalesMain.this);
			getContentPane().add(panel,BorderLayout.CENTER);
			
			getContentPane().revalidate();
		}
	}
	class addsalesListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			getContentPane().remove(panel);
			
			panel=new SalesPanel(SalesMain.this);
			getContentPane().add(panel,BorderLayout.CENTER);
			
			getContentPane().revalidate();
		}
	}
	class logoutListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(JOptionPane.showConfirmDialog(null, "确定要注销吗？", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION){
				dispose();
			new LoginFrame();
			}
			
		}
	}
	class exitListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(JOptionPane.showConfirmDialog(null, "确定要退出吗？", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION){
				dispose();
			}
			
		}
	}
	class keyListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			new UserInfo(uvo,SalesMain.this);
		}
	}
		
		
	
		
		
		

}
