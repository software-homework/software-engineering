//现金费用单
package ui.finance.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import businesslogic.LogBL;
import businesslogic.finance.AccountBL;
import businesslogicservice.LogBLService;
import businesslogicservice.finance.AccountBLService;
import vo.LogVO;
import vo.finance.AccountVO;
import vo.user.UserVO;

@SuppressWarnings("serial")
public class CashExpensesDialog extends JDialog implements ActionListener {
	FinanceFrame jFrame;
	int frameHeight = 520;
	int frameWidth = 400;

	UserVO userVO;
	String operator; // 操作员
	String time;
	long idNum;
	String id; // 编号
	String account;
	ArrayList<String> list = new ArrayList<String>();
	double total = 0;
	AccountBLService accountBLService = new AccountBL();

	JButton confirm;
	JLabel top;
	JScrollPane jsp1;
	JTable jt1;
	JScrollPane jsp2;
	JTable jt2;
	JPanel jp = new JPanel();
	JPanel jp2 = new JPanel();

	public CashExpensesDialog(FinanceFrame jFrame, String title, boolean modal, UserVO userVO) {
		super(jFrame, title, modal);
		this.jFrame = jFrame;
		this.userVO = userVO;
		this.operator = userVO.name;
		this.id = accountBLService.createID("XJFYD");
		this.time = this.id.substring(6, 14);
		this.idNum = Long.parseLong(this.id.substring(15));
		// ---------------------------------------------------------------------------------
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		this.setBounds((screenSize.width - frameWidth) / 2, (screenSize.height - frameHeight) / 3, frameWidth,
				frameHeight);
		// ---------------------------------------------------------------------------------
		// Button
		confirm = new JButton("<html>确定(<u>Y</u>)</html>)");
		this.confirm.addActionListener(this);
		// two Table
		ArrayList<AccountVO> accountTemp = accountBLService.findAll("");
		String[][] account = new String[accountTemp.size()][2];
		for (int i = 0; i < accountTemp.size(); i++) {
			account[i] = new String[] { accountTemp.get(i).id, accountTemp.get(i).amountOfAccount };
		}
		jt1 = new JTable(account, new String[] { "账户名", "余额" }) {
			public boolean isCellEditable(int row, int column) {
				if (column == 0 || column == 1) {// 让column为0, 1那一列不可用
					return false;
				} else
					return true;
			}
		};
		jsp1 = new JScrollPane(jt1);
		String[][] clauses = new String[10][3];
		for (int i = 0; i < accountTemp.size(); i++) {
			clauses[i] = new String[] { "", "", "" };
		}
		jt2 = new JTable(clauses, new String[] { "条目名", "金额", "备注" });
		jsp2 = new JScrollPane(jt2);
		// add to panel1
		this.jp.setLayout(new GridLayout(2, 1));
		jp.add(jsp1);
		jp.add(jsp2);
		// add to jp2
		jp2.setLayout(new FlowLayout(FlowLayout.RIGHT));
		jp2.add(confirm);
		// ------------------------------------------------------------------------------
		this.top = new JLabel(this.id + "    操作员: " + this.operator);
		this.add(top, BorderLayout.NORTH);
		this.add(jp, BorderLayout.CENTER);
		this.add(jp2, BorderLayout.SOUTH);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == confirm) {
			int[] num1 = jt1.getSelectedRows();
			int[] num2 = jt2.getSelectedRows();
			boolean isSuccess;
			if (num1.length > 1)
				JOptionPane.showMessageDialog(null, "账户选择数量唯一!", null, 0);
			else if (num1.length == 0)
				JOptionPane.showMessageDialog(null, "请选择账户!", null, 0);
			else if (num2.length == 0)
				JOptionPane.showMessageDialog(null, "请选择要添加的单据条目信息!", null, 0);
			else {
				account = (String) jt1.getValueAt(num1[0], 0);
				for (int i = 0; i < num2.length; i++) {
					if (((String) jt2.getValueAt(num2[i], 2)).trim().length() == 0)
						list.add(jt2.getValueAt(num2[i], 0) + ";" + jt2.getValueAt(num2[i], 1) + ";" + "无");
					else
						list.add(jt2.getValueAt(num2[i], 0) + ";" + jt2.getValueAt(num2[i], 1) + ";"
								+ jt2.getValueAt(num2[i], 2));
					total = total + Double.parseDouble((String) jt2.getValueAt(num2[i], 1));
				}
				String res = this.judgeAmount(num1, total);
				if (res.equals("")) {
					isSuccess = accountBLService.addCashExpenses(id, operator, account, list, total, time, idNum);
					if (isSuccess) {
						this.jFrame.getStatusBar().setText("添加成功!");
						// 日志
						LogBLService logBLService = new LogBL();
						logBLService.addLog(new LogVO(jFrame.userVO.name, "创建现金费用单"));
						this.dispose();
						// 刷新Table
						ArrayList<AccountVO> data;
						data = new ArrayList<AccountVO>();

						this.jFrame.remove(this.jFrame.table);
						this.jFrame.add(this.jFrame.createTable(data, null, null, null, null), BorderLayout.CENTER);
						this.jFrame.setVisible(true);
					}
				} else {
					JOptionPane.showMessageDialog(null, "账户: " + res + " 余额不足", null, 0);
					total = 0;
				}
			}
		}
	}

	public String judgeAmount(int[] num1, double total) {
		if (Double.parseDouble((String) jt1.getValueAt(num1[0], 1)) < total)
			return (String) jt1.getValueAt(num1[0], 0);
		return "";
	}
}
