package ui.manager.promotion;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * 
 * @date 2014年12月9日
 * @time 下午7:03:13
 * @author stk
 *
 */

/*
 * 促销策略所有dialog父类
 */
@SuppressWarnings("serial")
public class AddPromotionDialog extends JDialog{
	SimpleDateFormat sdf;
	JPanel pane1;
	JPanel pane2;
	JPanel pane3;
	JTable table;
	JButton confirm;
	JButton cancel;
	JLabel label1;
	JLabel label2;
	JLabel label3;
	JLabel label4;
	JLabel label5;
	JLabel label6;
	JTextField text1;
	JTextField text2;
	JTextField text3;
	JTextField text4;
	Object[][] data;
	String[] columnName;
	//-------------------------------------------------
	public AddPromotionDialog()	{
		sdf = new SimpleDateFormat("yyyy/MM/dd");
		this.setTitle("添加促销策略");
		this.setModal(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//定义界面大小
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int frameHeight = screenSize.height * 3 / 4;
		int frameWidth = frameHeight * 5 / 4;
		this.setBounds((screenSize.width - frameWidth) / 2, (screenSize.height - frameHeight) / 2, frameWidth, frameHeight);
		//设置按钮面板
		pane1 = new JPanel();
		pane1.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		confirm = new JButton("确定");
		cancel = new JButton("取消");
		
		pane1.add(confirm);
		pane1.add(cancel);
		
		this.getContentPane().add(pane1, BorderLayout.SOUTH);
	}
	//-----------------------------------------------------
	//生成商品信息string
	public String getInfo() {
		boolean judge = false;
		String goodsName = "";
		for(int i = 0; i < data.length; i++) {
			if(data[i][0] == Boolean.TRUE) {
				goodsName = goodsName +","+ data[i][1];
				judge = true;
			}
		}
		if(judge) {
			return goodsName.substring(1);
		}else {
			return "无";
		}
	}
	//日期格式判断
	public boolean isDate(String str) {
		try {
			if(str.equals(sdf.format(sdf.parse(str)))) {
				return true;
			}else {
				return false;
			}
		} catch (ParseException e) {
			return false;
		}
	}
	//日期输入判断
	public boolean isDateRight(String input1, String input2) {
		try {
			if(input1.trim().equals("") || input2.trim().equals("")) {
				//日期输入为空白
				JOptionPane.showMessageDialog(null, "请输入日期！", "警告", JOptionPane.WARNING_MESSAGE);
				return false;
			}else if(isDate(input1) == false || isDate(input2) == false) {
				//日期格式错误
				JOptionPane.showMessageDialog(null, "请输入正确的日期格式！如2014/01/06。", "警告", JOptionPane.WARNING_MESSAGE);
				return false;
			} else if(sdf.parse(input1).compareTo(sdf.parse(input2)) > 0) {
				//日期先后错误
				JOptionPane.showMessageDialog(null, "结束日期必须在起始日期之后！", "警告", JOptionPane.WARNING_MESSAGE);
				return false;
			}else {
				return true;
			}
		} catch (HeadlessException | ParseException e) {
			return false;
		}
	}
	//数字输入判断
	public boolean isNumberRight(String input) {
		if(input.trim().equals("")) {
			//输入为空白
			JOptionPane.showMessageDialog(null, "请填写完整！", "警告", JOptionPane.WARNING_MESSAGE);
			return false;
		}else {
			try {
				Double.parseDouble(input);
				return true;
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "请输入正确的数字！", "警告", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
	}
	//判断商品是否为空
	public boolean hasGoods() {
		for (int i = 0; i < data.length; i++) {
			if (data[i][0] == Boolean.TRUE) {
				return true;
			}
		}
		return false;
	}
}
