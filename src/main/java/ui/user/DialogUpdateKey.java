package ui.user;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import businesslogic.user.UserBL;
import businesslogicservice.user.UserBLService;
import vo.user.UserVO;

@SuppressWarnings("serial")
public class DialogUpdateKey extends JDialog implements ActionListener{
	AdminFrame adminFrame;
	UserVO user;
	
	JPasswordField jpf1 = new JPasswordField();JLabel jl1 = new JLabel("新密码：");
	JButton confirm = new JButton("确定");
	JButton cancel = new JButton("取消");
	public DialogUpdateKey(UserVO user, AdminFrame adminFrame){
		super(adminFrame, "修改密码", true);
		this.adminFrame = adminFrame;
		this.user = user;
	    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(300, 150);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridLayout(2, 2));
		
		jpf1.setEchoChar('*');
		jpf1.setPreferredSize(new Dimension(100, 30));
		JPanel jp1 = new JPanel();jp1.add(jl1);
		JPanel jp2 = new JPanel();jp2.add(jpf1);
		JPanel jp3 = new JPanel();jp3.add(confirm);
		JPanel jp4 = new JPanel();jp4.add(cancel);
		
		this.add(jp1);this.add(jp2);
		this.add(jp3);this.add(jp4);
		/*******/
		confirm.addActionListener(this);
		cancel.addActionListener(this);
		
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == confirm){
			UserBLService userBL = new UserBL();
			userBL.updateAdmin(String.copyValueOf(jpf1.getPassword()));
			JOptionPane.showMessageDialog(null, "成功!", null, 1);
			this.dispose();
		}
		else if(arg0.getSource() == cancel)
			this.dispose();
	}

}
