package ui.finance.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import vo.finance.AccountVO;
import vo.user.UserVO;
import businesslogic.finance.AccountBL;
import businesslogicservice.finance.AccountBLService;

@SuppressWarnings("serial")
public class FindAccountDialog extends JDialog implements ActionListener
{
	UserVO userVO;
	FinanceFrame jFrame; 
	
	int frameHeight = 250;
	int frameWidth = 350;
	
	JButton confirm;
	JButton cancel;
	
	JLabel keyword;
	int widthOfLabel = 100;
	int heightOfLabel = 30;
	
	JTextField jtf;
	int widthOfText = 150;
	int heightOfText = 30;
	
	public FindAccountDialog(FinanceFrame jFrame, String title, boolean modal, UserVO userVO)
	{
		super(jFrame, title, modal);
		
		this.userVO = userVO;
		this.jFrame = jFrame;
		
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		Toolkit kit = Toolkit.getDefaultToolkit();    	// 定义工具包
	    Dimension screenSize = kit.getScreenSize();   	// 获取屏幕的尺寸			
	    this.setBounds((screenSize.width - frameWidth) / 2, (screenSize.height - frameHeight) / 3, frameWidth, frameHeight);
		
		//Button
		confirm = new JButton("<html>确定(<u>Y</u>)</html>)");
		this.confirm.setSize(this.confirm.getPreferredSize());
		this.confirm.setLocation(70, 170);
		this.confirm.addActionListener(this);
		this.add(confirm);
		
		cancel = new JButton("<html>取消(<u>N</u>)</html>)");
		this.cancel.setSize(this.cancel.getPreferredSize());
		this.cancel.setLocation(200, 170);
		this.cancel.addActionListener(this);
		this.add(cancel);
		
		//JLabel
		keyword = new JLabel("查找关键字:");
		this.keyword.setSize(widthOfLabel, heightOfLabel);
		this.keyword.setLocation(50, 60);
		this.add(this.keyword);
		
		//JTextField
		jtf = new JTextField();
		this.jtf.setSize(widthOfText, heightOfText);
		this.jtf.setLocation(150, 60);
		this.add(this.jtf);
		
		
		this.getRootPane().setDefaultButton(confirm);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource() == this.confirm)
		{
			if(this.jtf.getText().trim().length() == 0)
				JOptionPane.showMessageDialog(null, "输入信息不能为空!", null, 0);
			else
			{
				ArrayList<AccountVO> data;
				AccountBLService accountBLService = new AccountBL();
				
				data = accountBLService.fuzzySearchAccount(this.jtf.getText().trim());
			
				this.jFrame.remove(this.jFrame.sp);
				this.jFrame.add(this.jFrame.createTable(data, null, null, null, null), BorderLayout.CENTER);
				this.jFrame.setVisible(true);

				//关闭窗口
				this.dispose();
			}
		}
		else if(arg0.getSource() == this.cancel)
			this.dispose();		
	}
}
