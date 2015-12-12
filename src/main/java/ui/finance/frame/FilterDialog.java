package ui.finance.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ui.finance.panel.ShowTablePanel;
import businesslogic.finance.AccountBL;
import businesslogicservice.finance.AccountBLService;

@SuppressWarnings("serial")
public class FilterDialog extends JDialog implements ActionListener
{	
	FinanceFrame jFrame; 
	
	int frameHeight = 360;
	int frameWidth = 480;
	
	JButton confirm;
	JButton cancel;
	
	JLabel[] jl = new JLabel[5];
	int widthOfLabel = 150;
	int heightOfLabel = 30;
	JLabel to = new JLabel("to");
	
	JTextField jtf1;
	JTextField jtf2;
	JTextField[] jtf = new JTextField[3];
	int widthOfText1 = 120;
	int widthOfText2 = 270;
	int heightOfText = 30;
	
	String[] s = {"仓库1"};
	JComboBox<String> cangKu = new JComboBox<String>(s);
		
	public FilterDialog(FinanceFrame jFrame, String title, boolean modal, String[] label) 
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
		this.confirm.setLocation(150, 260);
		this.confirm.addActionListener(this);
		this.add(confirm);
		
		cancel = new JButton("<html>取消(<u>N</u>)</html>)");
		this.cancel.setSize(this.cancel.getPreferredSize());
		this.cancel.setLocation(250, 260);
		this.cancel.addActionListener(this);
		this.add(cancel);
		
		//JLabel
		for(int i = 0; i < 5; i++)
		{
			jl[i] = new JLabel(label[i]);
			
			jl[i].setSize(widthOfLabel, heightOfLabel);
	    	jl[i].setLocation((frameWidth - 420) / 2, (i + 1) * (heightOfLabel + 10));
	    	this.add(jl[i]);
		}
		to.setSize(30, 30);
		to.setLocation((frameWidth - 420) / 2 + 270, 1 * (heightOfLabel + 10));
    	this.add(to);
		//---------------------------------------------------------------------------
		jtf1 = new JTextField();
		jtf1.setSize(widthOfText1, heightOfText);
		jtf1.setLocation((frameWidth - 420) / 2 + 150, 1 * (heightOfLabel + 10));
		this.add(jtf1);
		
		jtf2 = new JTextField();
		jtf2.setSize(widthOfText1, heightOfText);
		jtf2.setLocation((frameWidth - 420) / 2 + 300, 1 * (heightOfLabel + 10));
		this.add(jtf2);
		for(int i = 0; i < 3; i++)
		{
			jtf[i] = new JTextField();
			
			jtf[i].setSize(widthOfText2, heightOfText);
			jtf[i].setLocation((frameWidth - 420) / 2 + 150, (i + 2) * (heightOfLabel + 10));
	    	this.add(jtf[i]);
		}
		//---------------------------------------------------------------------------
		cangKu.setSize(widthOfText2, heightOfText);
		cangKu.setLocation((frameWidth - 420) / 2 + 150, 5 * (heightOfLabel + 10));
	    this.add(cangKu);
		//---------------------------------------------------------------------------
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0){
		if(arg0.getSource() == this.confirm){
			String time1 = jtf1.getText().trim();
			String time2 = jtf2.getText().trim();
			String notDecided = jtf[0].getText().trim();
			String customer = jtf[1].getText().trim();
			String operator = jtf[2].getText().trim();
			if(time1.length() == 0 || time2.length() == 0 || notDecided.length() == 0 ||
					customer.length() == 0 || operator.length() == 0)
				JOptionPane.showMessageDialog(null, "输入信息不能为空!", null, 0);
			else{
				AccountBLService accountBLService = new AccountBL();
				String[][] data = accountBLService.showDetail(time1, time2, notDecided,
						customer, operator);

				this.jFrame.remove(this.jFrame.sp);
				this.jFrame.add(this.jFrame.createTable(null, data, null, null, null), BorderLayout.CENTER);
				this.jFrame.setVisible(true);

				this.jFrame.remove(this.jFrame.jPanel);
				this.jFrame.jPanel = new ShowTablePanel(this.jFrame);
				this.jFrame.add(this.jFrame.jPanel, BorderLayout.WEST);
				this.jFrame.setVisible(true);
				//关闭窗口
				this.dispose();
			}
		}
		else if(arg0.getSource() == this.cancel){
			this.dispose();
		}
	}
}
