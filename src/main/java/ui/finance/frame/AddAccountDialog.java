package ui.finance.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import vo.LogVO;
import vo.finance.AccountVO;
import businesslogic.LogBL;
import businesslogic.finance.AccountBL;
import businesslogicservice.LogBLService;
import businesslogicservice.finance.AccountBLService;

@SuppressWarnings("serial")
public class AddAccountDialog extends JDialog implements ActionListener
{
	FinanceFrame jFrame; 
	
	int frameHeight = 250;
	int frameWidth = 350;
	
	JButton confirm;
	JButton cancel;
	
	JLabel id;
	JLabel amount;
	int widthOfLabel = 100;
	int heightOfLabel = 30;
	
	JTextField jtf1;
	JTextField jtf2;
	int widthOfText = 150;
	int heightOfText = 30;
	
	public AddAccountDialog(FinanceFrame jFrame, String title, boolean modal)
	{
		super(jFrame, title, modal);
		
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
		
		//JLael
		id = new JLabel("账户名称:");
		this.id.setSize(widthOfLabel, heightOfLabel);
		this.id.setLocation(50, 40);
		this.add(this.id);
		
		amount = new JLabel("账户余额:");
		this.amount.setSize(widthOfLabel, heightOfLabel);
		this.amount.setLocation(50, 110);
		this.add(this.amount);
		
		//JTextField
		jtf1 = new JTextField();
		this.jtf1.setSize(widthOfText, heightOfText);
		this.jtf1.setLocation(150, 40);
		this.add(this.jtf1);
		
		jtf2 = new JTextField();
		this.jtf2.setSize(widthOfText, heightOfText);
		this.jtf2.setLocation(150, 110);
		this.add(this.jtf2);
		
		
		this.getRootPane().setDefaultButton(confirm);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0)
	{
		if(arg0.getSource() == this.confirm)
		{
			if(this.jtf1.getText().trim().length() == 0 || this.jtf2.getText().trim().length() == 0)
				JOptionPane.showMessageDialog(null, "输入信息不能为空!", null, 0);
			else
			{
				AccountBLService accountBLService = new AccountBL();
				
				DecimalFormat df = new DecimalFormat("#.00"); 
				String balance = df.format(Double.parseDouble(this.jtf2.getText().trim()));
				boolean isSuccess = false;
				isSuccess = accountBLService.addAccount(this.jtf1.getText().trim(), balance);
			
				ArrayList<AccountVO> data;
				if(isSuccess)
				{	
					this.jFrame.statusBar.setText("账户添加成功!");
					data = accountBLService.findAll(this.jtf1.getText().trim());
					/*****更新日志**/
					LogBLService logBLService = new LogBL();
					logBLService.addLog(new LogVO(jFrame.userVO.name, "添加新账户"));
				}
				else
				{
					JOptionPane.showMessageDialog(null, "数据库中已存在该帐户!", null, 0);
					data = accountBLService.findAccount(this.jtf1.getText().trim());
				}
				
				this.jFrame.remove(this.jFrame.sp);
				this.jFrame.add(this.jFrame.createTable(data, null, null, null, null), BorderLayout.CENTER);
				this.jFrame.setVisible(true);

				//关闭窗口
				this.dispose();
			}
		}
		else if(arg0.getSource() == this.cancel)
		{ 
			this.dispose();
		}
	}
}
