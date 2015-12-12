package ui.manager.check;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @date 2014年12月9日
 * @time 下午7:02:33
 * @author stk
 *
 */

/*
 * 搜索弹出框
 */
@SuppressWarnings("serial")
public class SearchDialog extends JDialog{
	SimpleDateFormat sdf;
	JPanel pane1;
	JPanel pane2;
	JButton confirm;
	JButton cancel;
	JComboBox<String> comboBox;
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
	JTextField text5;
	JTextField text6;
	//--------------------------------------------------------------------
	public SearchDialog() {
		sdf = new SimpleDateFormat("yyyyMMdd");
		this.setTitle("搜索");
		this.setModal(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//定义界面大小
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int frameHeight = screenSize.height * 3 / 4;
		int frameWidth = frameHeight * 4 / 5;
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
	//----------------------------------------------------------------------
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
}
