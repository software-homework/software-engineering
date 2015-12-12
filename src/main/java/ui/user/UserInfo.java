package ui.user;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import vo.user.UserVO;

/**
 * 
 * @date 2014年12月13日
 * @time 下午6:13:40
 * @author stk
 *
 */

/*
 * 显示用户信息
 */
@SuppressWarnings("serial")
public class UserInfo extends JDialog {
	private JFrame frame;
	private UserVO vo;
	private JPanel pane1;
	private JPanel pane2;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JLabel label5;
	private JLabel label6;
	private JLabel label7;
	private JPasswordField password;
	private JButton edit;
	private JButton exit;

	// -------------------------------------------------------------
	public UserInfo(UserVO vo, JFrame frame) {
		this.vo = vo;
		this.frame = frame;
		this.setTitle("用户信息");
		this.setModal(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// 定义界面大小
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int frameHeight = screenSize.height * 1 / 2;
		int frameWidth = frameHeight;
		this.setBounds((screenSize.width - frameWidth) / 2, (screenSize.height - frameHeight) / 2, frameWidth,
				frameHeight);
		// 设置界面
		pane1 = new JPanel();
		pane1.setLayout(new FlowLayout(FlowLayout.RIGHT));

		edit = new JButton("修改密码");
		exit = new JButton("关闭");

		pane1.add(edit);
		pane1.add(exit);

		this.getContentPane().add(pane1, BorderLayout.SOUTH);
		// ---------------------------------------------------------------
		pane2 = new JPanel();
		pane2.setLayout(new GridLayout(4, 2, 40, 30));
		pane2.setBorder(BorderFactory.createEmptyBorder(70, 20, 60, 40));

		label1 = new JLabel("用户名：", JLabel.RIGHT);
		label2 = new JLabel(vo.name, JLabel.LEFT);
		label3 = new JLabel("密码：", JLabel.RIGHT);
		password = new JPasswordField(vo.password);
		password.setEnabled(false);
		label4 = new JLabel("用户类型：", JLabel.RIGHT);
		label5 = new JLabel(vo.type, JLabel.LEFT);

		pane2.add(label1);
		pane2.add(label2);
		pane2.add(label3);
		pane2.add(password);
		pane2.add(label4);
		pane2.add(label5);
		pane2.add(label6);
		pane2.add(label7);

		this.getContentPane().add(pane2, BorderLayout.CENTER);
		// 监听
		edit.addActionListener(new EditListener());
		exit.addActionListener(new ExitListener());
		// ------------------------------------------------------------------------
		this.setVisible(true);
	}

	// -------------------------------------------------------------
	// 监听内部类
	private class EditListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new ChangePassword(vo);
			dispose();
			frame.dispose();
			new LoginFrame();
		}
	}

	private class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
}
