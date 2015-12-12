package ui.finance.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ui.finance.panel.ShowTablePanel;
import businesslogic.manager.CheckBL;
import businesslogicservice.finance.GetCheckBL;

@SuppressWarnings("serial")
public class FilterDialog3 extends JDialog implements ActionListener{
	FinanceFrame jFrame; 
	
	int frameHeight = 300;
	int frameWidth = 480;
	JButton confirm;
	JButton cancel;
	JLabel jl = new JLabel("时间区间：");
	int widthOfLabel = 150;
	int heightOfLabel = 30;
	JLabel to = new JLabel("to");
	JTextField jtf1;
	JTextField jtf2;
	int widthOfText = 120;
	int heightOfText = 30;
	public FilterDialog3(FinanceFrame jFrame, String title, boolean modal) {
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
		this.confirm.setLocation(150, 200);
		this.confirm.addActionListener(this);
		this.add(confirm);
		
		cancel = new JButton("<html>取消(<u>N</u>)</html>)");
		this.cancel.setSize(this.cancel.getPreferredSize());
		this.cancel.setLocation(250, 200);
		this.cancel.addActionListener(this);
		this.add(cancel);
		//
		jl.setSize(widthOfLabel, heightOfLabel);
		jl.setLocation(30, 100);
    	this.add(jl);
		to.setSize(30, 30);
		to.setLocation(300, 100);
    	this.add(to);
    	//
    	jtf1 = new JTextField();
		jtf1.setSize(widthOfText, heightOfText);
		jtf1.setLocation(180, 100);
		this.add(jtf1);
		jtf2 = new JTextField();
		jtf2.setSize(widthOfText, heightOfText);
		jtf2.setLocation(330, 100);
		this.add(jtf2);
		
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == confirm){
			GetCheckBL getCheckBL = new CheckBL();
			if(this.jtf1.getText().trim().length() == 0 || this.jtf2.getText().trim().length() == 0)
				JOptionPane.showMessageDialog(null, "输入时间不能为空!", null, 0);
			else{
				Object[][] temp = getCheckBL.showCondition(new String[]{this.jtf1.getText().trim(), this.jtf2.getText().trim()});
				String[][] data = new String[temp.length][8];
				for(int i = 0; i < temp.length; i++)
					for(int j = 0; j < 8; j++)
						data[i][j] = temp[i][j].toString();
				this.jFrame.remove(this.jFrame.sp);
				this.jFrame.add(this.jFrame.createTable(null, null, null, data, null), BorderLayout.CENTER);
				this.jFrame.setVisible(true);
				
				this.jFrame.remove(this.jFrame.jPanel);
				this.jFrame.jPanel = new ShowTablePanel(this.jFrame);
				this.jFrame.add(this.jFrame.jPanel, BorderLayout.WEST);
				this.jFrame.setVisible(true);
				//关闭窗口
				this.dispose();
			}
		}
		else if(arg0.getSource() == cancel)
			this.dispose();
	}
}
