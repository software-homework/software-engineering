package ui.user;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import businesslogic.user.UserBL;
import businesslogicservice.user.UserBLService;
import vo.user.UserVO;

@SuppressWarnings("serial")
public class DialogAdd extends JDialog implements ActionListener {
	UserVO userVO;
	AdminFrame adminFrame;
	JButton confirm;
	JButton cancel;

	JLabel[] jl = new JLabel[4];
	JComboBox<String> typeBC;
	JTextField nameTF;
	JPasswordField jPasswordField;
	JComboBox<String> permissionBC;

	int width;
	int height;
	int widthLabel = 100;
	int heightLabel = 30;
	int widthText = 150;
	int heightText = 30;

	public DialogAdd(AdminFrame adminFrame, UserVO userVO) {
		super(adminFrame, "添加", true);
		this.adminFrame = adminFrame;
		this.userVO = userVO;
		this.setLayout(null);

		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		height = screenSize.height * 1 / 2 - 40;
		width = height;
		this.setBounds((screenSize.width - width) / 2, (screenSize.height - height) / 2, width, height);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		// -------------------------------------------------------------------------
		jl[0] = new JLabel("类  型:");
		jl[1] = new JLabel("用户名:");
		jl[2] = new JLabel("密  码:");
		jl[3] = new JLabel("权  限:");
		for (int i = 0; i < 4; i++) {
			jl[i].setSize(widthLabel, heightLabel);
			jl[i].setLocation((width - 250) / 2, (i + 1) * (heightLabel + 10));
			this.add(jl[i]);
		}
		// -------------------------------------------------------------------------
		String[] s1 = { "物流人员", "进货销售人员", "财务人员", "总经理" };
		typeBC = new JComboBox<String>(s1);
		typeBC.setSize(widthText, heightText);
		typeBC.setLocation((width - 250) / 2 + 100, 1 * (heightLabel + 10));
		this.add(typeBC);

		nameTF = new JTextField();
		nameTF.setSize(widthText, heightText);
		nameTF.setLocation((width - 250) / 2 + 100, 2 * (heightLabel + 10));
		this.add(nameTF);

		jPasswordField = new JPasswordField();
		jPasswordField.setEchoChar('*');
		jPasswordField.setSize(widthText, heightText);
		jPasswordField.setLocation((width - 250) / 2 + 100, 3 * (heightLabel + 10));
		this.getContentPane().add(jPasswordField);

		String[] s2 = { "经理", "普通职员" };
		permissionBC = new JComboBox<String>(s2);
		permissionBC.setSize(widthText, heightText);
		permissionBC.setLocation((width - 250) / 2 + 100, 4 * (heightLabel + 10));
		this.add(permissionBC);

		// ---------------------------------------------------------------------------
		confirm = new JButton("确定");
		confirm.setSize(widthLabel, heightLabel);
		confirm.setLocation((width - 250) / 2, 5 * (heightLabel + 10) + 40);
		this.add(confirm);

		cancel = new JButton("取消");
		cancel.setSize(widthLabel, heightLabel);
		cancel.setLocation((width - 250) / 2 + 150, 5 * (heightLabel + 10) + 40);
		this.add(cancel);
		// ---------------------------------------------------------------------------
		this.typeBC.addActionListener(this);
		this.confirm.addActionListener(this);
		this.cancel.addActionListener(this);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == confirm) {
			UserBLService userBL = new UserBL();
			String type = (String) typeBC.getSelectedItem();
			String name = nameTF.getText().trim();
			String pass = String.copyValueOf(jPasswordField.getPassword());
			String permission = (String) permissionBC.getSelectedItem();
			if (name.trim().length() == 0 || pass.trim().length() == 0)
				JOptionPane.showMessageDialog(null, "输入信息不能为空!", null, 0);
			else {
				UserVO userVO = new UserVO(type, name, pass);
				boolean isSuccess = userBL.addUser(userVO);
				if (isSuccess)
					JOptionPane.showMessageDialog(null, "操作成功!", null, 1);
				else
					JOptionPane.showMessageDialog(null, "已存在该用户!", null, 0);

				this.dispose();
			}
		} else if (arg0.getSource() == typeBC) {
			if (((String) typeBC.getSelectedItem()).equals("总经理")) {
				this.permissionBC.setSelectedIndex(0);
				this.permissionBC.setEnabled(false);
			} else
				this.permissionBC.setEnabled(true);
		} else if (arg0.getSource() == cancel)
			this.dispose();
	}
}
