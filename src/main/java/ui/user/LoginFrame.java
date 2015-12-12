package ui.user;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import businesslogic.user.UserBL;
import businesslogicservice.user.UserBLService;
import ui.finance.frame.FinanceFrame;
import ui.manager.ManagerFrame;
import ui.salesman.SalesMain;
import ui.stockManage.StockMain;
import vo.user.UserVO;

@SuppressWarnings("serial")
public class LoginFrame extends JFrame implements ActionListener {
	JButton login;
	JButton cancel;

	JLabel[] jl = new JLabel[3];
	JComboBox<String> typeBC;
	JTextField nameTF;
	JPasswordField jPasswordField;

	int widthLabel = 100;
	int heightLabel = 30;
	int widthText = 150;
	int heightText = 30;
	Font font = new Font("幼圆", Font.BOLD, 16);
	Font font2 = new Font("幼圆", Font.BOLD, 14);
	Font font3 = new Font("黑体", Font.BOLD, 35);

	public LoginFrame() {
		this.setLayout(null);

		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		int height = screenSize.height * 3 / 5;
		int width = height * 5 / 4;
		this.setBounds((screenSize.width - width) / 2, (screenSize.height - height) / 2, width, height);
		this.setTitle("逆风快递系统");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		// -------------------------------------------------------------------------
		jl[0] = new JLabel("类  型:");
		jl[1] = new JLabel("用户名:");
		jl[2] = new JLabel("密  码:");
		for (int i = 0; i < 3; i++) {
			jl[i].setSize(widthLabel, heightLabel);
			jl[i].setLocation(280, (i + 1) * (heightLabel + 10) + 100);
			jl[i].setFont(font);
			this.add(jl[i]);
		}
		// -------------------------------------------------------------------------
		String[] s1 = { "管理员", "物流人员", "快递员", "财务人员", "总经理" };

		typeBC = new JComboBox<String>(s1);
		typeBC.setSize(widthText, heightText);
		typeBC.setLocation(380, 1 * (heightLabel + 10) + 100);
		typeBC.setFont(font);
		typeBC.addActionListener(this);
		this.add(typeBC);

		nameTF = new JTextField();
		nameTF.setSize(widthText, heightText);
		nameTF.setLocation(380, 2 * (heightLabel + 10) + 100);
		nameTF.setFont(font);
		this.add(nameTF);

		jPasswordField = new JPasswordField();
		jPasswordField.setEchoChar('*');
		jPasswordField.setSize(widthText, heightText);
		jPasswordField.setLocation(380, 3 * (heightLabel + 10) + 100);
		jPasswordField.setFont(font);
		this.getContentPane().add(jPasswordField);

		// -------------------------------------------------------------------------
		login = new JButton("登录");
		login.setFont(font);
		login.setBackground(Color.CYAN);
		login.setSize(widthLabel, heightLabel);
		login.setLocation(280, 5 * (heightLabel + 10) + 120);
		this.add(login);

		cancel = new JButton("取消");
		cancel.setFont(font);
		cancel.setBackground(Color.CYAN);
		cancel.setSize(widthLabel, heightLabel);
		cancel.setLocation(430, 5 * (heightLabel + 10) + 120);
		this.add(cancel);
		// -------------------------------------------------------------------------
		login.addActionListener(this);
		cancel.addActionListener(this);
		this.setVisible(true);
	}

	public void paint(Graphics g) {
		super.paint(g);

		float lineWidth = 3.0f;
		((Graphics2D) g).setStroke(new BasicStroke(lineWidth));
		((Graphics2D) g).setColor(Color.BLACK);
		((Graphics2D) g).drawLine(250, 80, 250, 380);
		((Graphics2D) g).setColor(Color.MAGENTA);
		((Graphics2D) g).drawLine(120, 150, 450, 150);

		((Graphics2D) g).setColor(Color.BLACK);
		((Graphics2D) g).setFont(font3);
		((Graphics2D) g).drawString("用户登录", 80, 120);
	}

	@SuppressWarnings("unused")
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == login) {
			UserBLService ubs = new UserBL();

			if (nameTF.getText().trim().length() == 0 || jPasswordField.getPassword().length == 0) {
				JOptionPane.showMessageDialog(null, "输入信息不能为空!", null, 0);
			} else {
				UserVO userVO = new UserVO(((String) typeBC.getSelectedItem()), nameTF.getText().trim(),
						String.copyValueOf(jPasswordField.getPassword()));
				boolean isSuccess = ubs.login(userVO);

				if (isSuccess) {
					if (((String) typeBC.getSelectedItem()).equals("物流人员")) {
						new StockMain(userVO);
					} else if (((String) typeBC.getSelectedItem()).equals("快递员")) {
						SalesMain salesMain = new SalesMain(userVO);
					} else if (((String) typeBC.getSelectedItem()).equals("财务人员")) {
						FinanceFrame financeFrame = new FinanceFrame(userVO);
					} else if (((String) typeBC.getSelectedItem()).equals("总经理")) {
						ManagerFrame managerFrame = new ManagerFrame(userVO);
					} else if (((String) typeBC.getSelectedItem()).equals("管理员"))
						new AdminFrame(userVO);

					this.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "不存在该用户!", null, 0);
					nameTF.setText("");
					jPasswordField.setText("");
				}
			}
		} else if (arg0.getSource() == cancel) {
			System.exit(0);
		}
	}

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new LoginFrame();
	}
}
