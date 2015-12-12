package ui.manager.check;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * 
 * @date 2014年12月6日
 * @time 下午8:56:57
 * @author stk
 *
 */

/*
 * 显示单据信息的父类
 */
@SuppressWarnings("serial")
public class Show extends JDialog{
	JPanel pane1;
	JPanel pane2;
	JTable table;
	JScrollPane sp;
	JLabel label1;
	JLabel label2;
	JLabel label3;
	JLabel label4;
	JLabel label5;
	JLabel label6;
	JLabel label7;
	JLabel label8;
	JLabel label9;
	JLabel label10;
	JLabel label11;
	JLabel label12;
	JLabel label13;
	JLabel label14;
	JLabel label15;
	JLabel label16;
	JLabel label17;
	JLabel label18;
	JLabel label19;
	JButton exit;
	Object[][] data;
	String[] columnName;
	String id;
	//--------------------------------------------------------
	public Show(String id) {
		this.id = id;
		this.setTitle("单据信息-" + this.title(id));
		this.setModal(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLayout(new BorderLayout());
		//定义界面大小
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int frameHeight = screenSize.height * 3 / 4;
		int frameWidth = frameHeight * 3 / 4;
		this.setBounds((screenSize.width - frameWidth) / 2, (screenSize.height - frameHeight) / 2, frameWidth, frameHeight);
		//设置组件
		pane1 = new JPanel();
		pane1.setLayout(new FlowLayout(FlowLayout.RIGHT));
		exit = new JButton("关闭");
		pane1.add(exit);
		this.add(pane1, BorderLayout.SOUTH);
	}
	//------------------------------------------------------------------------
	private String title(String id) {
		String str = "";
		switch(id.split("-")[0]) {
		case "JHD" : str = "进货单";break;
		case "JHTHD" : str = "进货退货单";break;
		case "XSD" : str = "销售单";break;
		case "XSTHD" : str = "销售退货单";break;
		case "SKD" : str = "收款单";break;
		case "FKD" : str = "付款单";break;
		case "BYD" : str = "报溢单";break;
		case "BSD" : str = "报损单";break;
		case "ZSD" : str = "赠送单";break;
		case "XJFYD" : str = "现金费用单";break;
		default : System.out.println("单据编号出错");
		}
		return str;
	}
}
