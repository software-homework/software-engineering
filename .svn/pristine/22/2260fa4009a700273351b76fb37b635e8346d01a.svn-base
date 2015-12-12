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

import vo.LogVO;
import vo.finance.AccountVO;
import vo.user.UserVO;
import businesslogic.LogBL;
import businesslogic.finance.AccountBL;
import businesslogicservice.LogBLService;
import businesslogicservice.finance.AccountBLService;
import exception.ExistPO;
import exception.NotFoundPO;

@SuppressWarnings("serial")
public class UpdateAccountDialog extends JDialog implements ActionListener
{
	UserVO userVO;
	FinanceFrame jFrame;
	
	int frameHeight = 250;
	int frameWidth = 350;
	
	JButton confirm;
	JButton cancel;
	
	JLabel initial_id;
	JLabel new_id;
	int widthOfLabel = 100;
	int heightOfLabel = 30;
	
	JTextField jtf1;
	JTextField jtf2;
	int widthOfText = 150;
	int heightOfText = 30;
	
	public UpdateAccountDialog(FinanceFrame jFrame, String title, boolean modal, UserVO userVO)
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
		initial_id = new JLabel("原账户名:");
		this.initial_id.setSize(widthOfLabel, heightOfLabel);
		this.initial_id.setLocation(50, 40);
		this.add(this.initial_id);
		
		new_id = new JLabel("新账户名:");
		this.new_id.setSize(widthOfLabel, heightOfLabel);
		this.new_id.setLocation(50, 110);
		this.add(this.new_id);
		
		//TextField
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
			else if((this.jtf1.getText().trim()).equals(this.jtf2.getText().trim()))
			{
				JOptionPane.showMessageDialog(null, "更改的账户名不能与原账户名称相同!", null, 0);
				this.jtf2.setText("");
			}
			else
			{
				boolean isSuccess = false;
				AccountBLService accountBLService = new AccountBL();
				
				ArrayList<AccountVO> data = new ArrayList<AccountVO>();
				try
				{
					isSuccess = accountBLService.updateAccount(this.jtf1.getText().trim(), this.jtf2.getText().trim(), "0");
				
					if(isSuccess)
					{
						this.jFrame.statusBar.setText("账户更新成功!");
						data = accountBLService.findAll(this.jtf2.getText().trim());
						//日志
						LogBLService logBLService = new LogBL();
						logBLService.addLog(new LogVO(jFrame.userVO.name, "更新账户"));
					}
				} 
				catch (NotFoundPO e)
				{
					JOptionPane.showMessageDialog(null, e.getMessage(), null, 0);
					data = accountBLService.findAll("");
				} 
				catch (ExistPO e) 
				{
					JOptionPane.showMessageDialog(null, e.getMessage(), null, 0);
					data = accountBLService.findAccount(this.jtf2.getText().trim());
				}
				finally
				{
					this.jFrame.remove(this.jFrame.sp);
					this.jFrame.add(this.jFrame.createTable(data, null, null, null, null), BorderLayout.CENTER);
					this.jFrame.setVisible(true);
					
					this.dispose();
				}			
			}
		}
		else if(arg0.getSource() == this.cancel)
			this.dispose();
	}

}
