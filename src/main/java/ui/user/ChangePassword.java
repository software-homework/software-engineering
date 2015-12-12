package ui.user;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import businesslogic.LogBL;
import businesslogic.user.UserBL;
import businesslogicservice.LogBLService;
import businesslogicservice.user.UserBLService;
import vo.LogVO;
import vo.user.UserVO;

/**
 * 
 * @date 2014年12月13日
 * @time 下午8:12:59
 * @author stk
 *
 */

/*
 * 修改用户密码dialog
 */
@SuppressWarnings("serial")
public class ChangePassword extends JDialog {
	private UserVO oldVO;
	private JPanel pane1;
	private JPanel pane2;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JPasswordField password1;
	private JPasswordField password2;
	private JPasswordField password3;
	private JButton confirm;
	private JButton cancel;

	// ----------------------------------------------------------
	public ChangePassword(UserVO oldVO) {
		this.oldVO = oldVO;
		this.setTitle("修改密码");
		this.setModal(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// 定义界面大小
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int frameHeight = screenSize.height * 3 / 7;
		int frameWidth = frameHeight;
		this.setBounds((screenSize.width - frameWidth) / 2, (screenSize.height - frameHeight) / 2, frameWidth,
				frameHeight);
		// 设置界面
		pane1 = new JPanel();
		pane1.setLayout(new FlowLayout(FlowLayout.RIGHT));

		confirm = new JButton("确认修改");
		cancel = new JButton("取消");

		pane1.add(confirm);
		pane1.add(cancel);

		this.getContentPane().add(pane1, BorderLayout.SOUTH);
		// ---------------------------------------------------------
		pane2 = new JPanel();
		pane2.setLayout(new GridLayout(3, 2, 40, 30));
		pane2.setBorder(BorderFactory.createEmptyBorder(70, 20, 60, 40));

		label1 = new JLabel("原密码：", JLabel.RIGHT);
		password1 = new JPasswordField();
		label2 = new JLabel("新密码：", JLabel.RIGHT);
		password2 = new JPasswordField();
		label3 = new JLabel("确认密码：", JLabel.RIGHT);
		password3 = new JPasswordField();

		pane2.add(label1);
		pane2.add(password1);
		pane2.add(label2);
		pane2.add(password2);
		pane2.add(label3);
		pane2.add(password3);

		this.getContentPane().add(pane2, BorderLayout.CENTER);
		// 监听
		confirm.addActionListener(new ConfirmListener());
		cancel.addActionListener(new CancelListener());
		// ---------------------------------------------------------------------
		this.setVisible(true);
	}

	// -------------------------------------------------------------
	// 监听内部类
	private class ConfirmListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!String.valueOf(password2.getPassword()).trim().equals("")) {
				if (String.valueOf(password2.getPassword()).equals(String.valueOf(password3.getPassword()))) {
					UserBLService bl = new UserBL();
					ArrayList<UserVO> list = new ArrayList<UserVO>();
					list = bl.findUser();
					for (UserVO i : list) {
						if (i.name.equals(oldVO.name) && i.type.equals(oldVO.type)) {
							if (String.valueOf(password1.getPassword()).equals(i.password)) {
								if (bl.updateUser(oldVO,
										new UserVO(i.type, i.name, String.valueOf(password2.getPassword())))) {
									LogBLService logBLService = new LogBL();
									logBLService.addLog(new LogVO(oldVO.name, "修改用户密码"));
									JOptionPane.showMessageDialog(null, "修改用户密码成功！", "信息",
											JOptionPane.INFORMATION_MESSAGE);
								} else {
									JOptionPane.showMessageDialog(null, "修改用户密码失败！", "错误", JOptionPane.ERROR_MESSAGE);
								}
								dispose();
							} else {
								JOptionPane.showMessageDialog(null, "原密码错误！", "警告", JOptionPane.WARNING_MESSAGE);
							}
							break;
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "确认密码与新密码不匹配！", "警告", JOptionPane.WARNING_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "新密码不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	private class CancelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (JOptionPane.showConfirmDialog(null, "确认要取消修改吗？", "提示", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				dispose();
			}
		}
	}
}
