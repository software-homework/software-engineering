package ui.courier.manage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import businesslogic.courier.CustomerBL;
import businesslogicservice.courier.CustomerBLService;
import ui.TableModel;
import vo.courier.CustomerVO;
import vo.courier.CourierVO;

@SuppressWarnings("serial")
public class CourierP2 extends JPanel {
	CustomerBLService cbl;
	CourierVO svo;
	CourierVO salessvo;
	AddSalesFrame addSalesframe;
	JPanel title;
	JPanel in;
	JPanel check;
	JPanel write;
	JButton next;
	JButton rnext;
	JButton cancel;
	JButton last;
	JButton showpromotion;
	JLabel customer;
	JLabel repertory;
	JLabel user;
	JLabel level;
	JLabel remakes;
	JLabel discount;
	JLabel getuser;
	JLabel getlevel;
	JLabel getcustomer;
	JLabel voucher;
	JTextField getdiscount;
	JTextField getvoucher;
	JTextField getrep;
	JTextField getremakes;
	JComboBox<String> customerbox;
	JTable commoditytable;
	Object[][] data;
	String[] customername;
	Object[][] newdata;

	// 送货
	public CourierP2(Object[][] cdata, AddSalesFrame asf) {
		this.setLayout(new BorderLayout());
		addSalesframe = asf;

		// 销售商列表
		cbl = new CustomerBL();
		ArrayList<CustomerVO> list = new ArrayList<CustomerVO>();
		list = cbl.ShowC();
		int count = 0;
		for (CustomerVO cvo : list) {
			if (cvo.type.equals("销售商")) {
				count++;
			}
		}
		if (count != 0) {
			customername = new String[count];
			count = 0;
			for (CustomerVO cvo : list) {
				if (cvo.type.equals("销售商")) {
					customername[count] = cvo.name;
					count++;
				}
			}
		} else {
			customername = new String[] { "空" };
		}

		// 头面板
		user = new JLabel("操作员:");
		getuser = new JLabel(asf.username);
		getlevel = new JLabel(asf.userlevel);
		title = new JPanel(new FlowLayout(FlowLayout.LEFT));
		title.add(user);
		title.add(getuser);
		title.add(level);
		title.add(getlevel);
		// 输入面板
		customer = new JLabel("销售商:", JLabel.CENTER);
		repertory = new JLabel("仓库:", JLabel.CENTER);
		remakes = new JLabel("备注:", JLabel.CENTER);
		discount = new JLabel("折让金额:", JLabel.CENTER);
		voucher = new JLabel("代金券:", JLabel.CENTER);
		getdiscount = new JTextField("0", 20);
		getdiscount.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				int keyChar = e.getKeyChar();
				if ((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) || (keyChar == KeyEvent.VK_PERIOD)) {
					if (keyChar == KeyEvent.VK_PERIOD && getdiscount.getText().indexOf(".") != -1) {
						e.consume();
					}
				} else {
					e.consume(); // 屏蔽掉非法输入
				}
			}
		});
		getvoucher = new JTextField("0", 20);
		getvoucher.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				int keyChar = e.getKeyChar();
				if ((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) || (keyChar == KeyEvent.VK_PERIOD)) {
					if (keyChar == KeyEvent.VK_PERIOD && getvoucher.getText().indexOf(".") != -1) {
						e.consume();
					}
				} else {
					e.consume(); // 屏蔽掉非法输入
				}
			}
		});
		getremakes = new JTextField(20);
		getrep = new JTextField("1", 20);
		getrep.setEnabled(false);
		customerbox = new JComboBox<String>(customername);

		String[] columnTitle = { "数量(可修改)", "编号", "名称", "型号", "折扣(可修改)", "物流数量" };
		data = cdata;
		commoditytable = new JTable(new TableModel(data, columnTitle) {
			public boolean isCellEditable(int row, int column) {
				return (column == 0 || column == 4);
			}
		});

		write = new JPanel(new GridLayout(5, 2, 0, 25));
		write.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 50));
		write.add(customer);
		write.add(customerbox);
		write.add(repertory);
		write.add(getrep);
		write.add(discount);
		write.add(getdiscount);
		write.add(voucher);
		write.add(getvoucher);
		write.add(remakes);
		write.add(getremakes);
		in = new JPanel(new GridLayout(2, 1));
		in.add(new JScrollPane(commoditytable));
		in.add(write);

		// 确认面板
		showpromotion = new JButton("查看促销策略");
		next = new JButton("下一步");
		cancel = new JButton("取消");
		last = new JButton("上一步");
		check = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		// check.add(showpromotion);
		check.add(last);
		check.add(next);
		check.add(cancel);

		// 添加组件
		this.add(title, BorderLayout.NORTH);
		this.add(in, BorderLayout.CENTER);
		this.add(check, BorderLayout.SOUTH);
		// 监听
		ActionListener nextlistener = new nextListener();
		next.addActionListener(nextlistener);
		ActionListener cancellistener = new cancelListener();
		cancel.addActionListener(cancellistener);
		ActionListener lastlistener = new lastListener();
		last.addActionListener(lastlistener);
		ActionListener showpromotionlistener = new showpromotionListener();
		showpromotion.addActionListener(showpromotionlistener);
	}

	// 销售退货单
	public CourierP2(Object[][] cdata, AddSalesFrame asf, CourierVO svo) {
		this.setLayout(new BorderLayout());
		addSalesframe = asf;
		salessvo = svo;

		// 头面板
		user = new JLabel("操作员:");
		getuser = new JLabel(asf.username);
		getlevel = new JLabel(asf.userlevel);
		title = new JPanel(new FlowLayout(FlowLayout.LEFT));
		title.add(user);
		title.add(getuser);
		title.add(level);
		title.add(getlevel);
		// 输入面板
		customer = new JLabel("销售商:", JLabel.CENTER);
		repertory = new JLabel("仓库:", JLabel.CENTER);
		remakes = new JLabel("备注:", JLabel.CENTER);
		discount = new JLabel("折让金额:", JLabel.CENTER);
		voucher = new JLabel("代金券:", JLabel.CENTER);
		getdiscount = new JTextField(String.valueOf(svo.discount), 20);
		getdiscount.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				int keyChar = e.getKeyChar();
				if ((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) || (keyChar == KeyEvent.VK_PERIOD)) {
					if (keyChar == KeyEvent.VK_PERIOD && getdiscount.getText().indexOf(".") != -1) {
						e.consume();
					}
				} else {
					e.consume(); // 屏蔽掉非法输入
				}
			}
		});
		getvoucher = new JTextField(String.valueOf(svo.Voucher), 20);
		getvoucher.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				int keyChar = e.getKeyChar();
				if ((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) || (keyChar == KeyEvent.VK_PERIOD)) {
					if (keyChar == KeyEvent.VK_PERIOD && getvoucher.getText().indexOf(".") != -1) {
						e.consume();
					}
				} else {
					e.consume(); // 屏蔽掉非法输入
				}
			}
		});
		getremakes = new JTextField(20);
		getrep = new JTextField("1", 20);
		getrep.setEnabled(false);
		getcustomer = new JLabel(svo.customer, JLabel.CENTER);

		String[] columnTitle = { "退货数量", "编号", "名称", "型号", "折扣(可修改)" };
		data = cdata;
		commoditytable = new JTable(new TableModel(data, columnTitle) {
			public boolean isCellEditable(int row, int column) {
				return (column == 4);
			}
		});

		write = new JPanel(new GridLayout(5, 2, 0, 25));
		write.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 50));
		write.add(customer);
		write.add(getcustomer);
		write.add(repertory);
		write.add(getrep);
		write.add(discount);
		write.add(getdiscount);
		write.add(voucher);
		write.add(getvoucher);
		write.add(remakes);
		write.add(getremakes);
		in = new JPanel(new GridLayout(2, 1));
		in.add(new JScrollPane(commoditytable));
		in.add(write);

		// 确认面板
		rnext = new JButton("下一步");
		cancel = new JButton("取消");
		last = new JButton("上一步");
		check = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		check.add(last);
		check.add(rnext);
		check.add(cancel);

		// 添加组件
		this.add(title, BorderLayout.NORTH);
		this.add(in, BorderLayout.CENTER);
		this.add(check, BorderLayout.SOUTH);
		// 监听
		ActionListener rnextlistener = new rnextListener();
		rnext.addActionListener(rnextlistener);
		ActionListener cancellistener = new cancelListener();
		cancel.addActionListener(cancellistener);
		ActionListener lastlistener = new lastListener();
		last.addActionListener(lastlistener);
	}

	private class cancelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			addSalesframe.dispose();
		}
	}

	private class nextListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (customerbox.getSelectedItem().equals("空") || getremakes.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "未填写完整", "提示", JOptionPane.WARNING_MESSAGE);
			} else {
				if ((Double.parseDouble(getdiscount.getText()) <= 4000 && addSalesframe.userlevel.equals("经理"))
						|| (Double.parseDouble(getdiscount.getText()) <= 1000
								&& addSalesframe.userlevel.equals("普通职员"))) {
					try {
						newdata = new Object[data.length][6];
						for (int i = 0; i < data.length; i++) {
							newdata[i][0] = data[i][1];
							newdata[i][1] = data[i][2];
							newdata[i][2] = data[i][3];
							newdata[i][3] = data[i][4];
							newdata[i][4] = data[i][0];
							newdata[i][5] = String.valueOf(Integer.parseInt(data[i][0].toString())
									* Double.parseDouble(data[i][4].toString()));
						}
						svo = new CourierVO();
						svo.customer = (String) customerbox.getSelectedItem();
						svo.remakes = getremakes.getText();
						svo.repertory = Integer.parseInt(getrep.getText());
						svo.user = getuser.getText();
						if (getdiscount.getText().indexOf(".") == -1) {
							getdiscount.setText(getdiscount.getText() + ".");
						}
						if (getvoucher.getText().indexOf(".") == -1) {
							getvoucher.setText(getvoucher.getText() + ".");
						}
						svo.discount = Double.parseDouble("0" + getdiscount.getText() + "0");
						svo.Voucher = Double.parseDouble("0" + getvoucher.getText() + "0");

						addSalesframe.panel3 = new CourierP3(newdata, svo, addSalesframe);
						addSalesframe.panel2.setVisible(false);
						addSalesframe.panel2.setEnabled(false);
						addSalesframe.add(addSalesframe.panel3, BorderLayout.CENTER);

					} catch (NumberFormatException event) {
						JOptionPane.showMessageDialog(null, "有非法字符！", "提示", JOptionPane.WARNING_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "折让权限不够", "提示", JOptionPane.WARNING_MESSAGE);
				}
			}

		}
	}

	private class rnextListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (getremakes.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "未填写完整", "提示", JOptionPane.WARNING_MESSAGE);
			} else {
				if ((Double.parseDouble(getdiscount.getText()) <= 4000 && addSalesframe.userlevel.equals("经理"))
						|| (Double.parseDouble(getdiscount.getText()) <= 1000
								&& addSalesframe.userlevel.equals("普通职员"))) {
					try {
						newdata = new Object[data.length][6];
						for (int i = 0; i < data.length; i++) {
							newdata[i][0] = data[i][1];
							newdata[i][1] = data[i][2];
							newdata[i][2] = data[i][3];
							newdata[i][3] = data[i][4];
							newdata[i][4] = data[i][0];
							newdata[i][5] = String.valueOf(Integer.parseInt(data[i][0].toString())
									* Double.parseDouble(data[i][4].toString()));
						}
						svo = new CourierVO();
						svo.customer = salessvo.customer;
						svo.remakes = getremakes.getText();
						svo.repertory = Integer.parseInt(getrep.getText());
						svo.user = getuser.getText();
						svo.discount = Double.parseDouble("0" + getdiscount.getText() + "0");
						svo.Voucher = Double.parseDouble("0" + getvoucher.getText() + "0");

						addSalesframe.panel3 = new CourierP3(newdata, svo, addSalesframe);
						addSalesframe.panel2.setVisible(false);
						addSalesframe.panel2.setEnabled(false);
						addSalesframe.add(addSalesframe.panel3, BorderLayout.CENTER);
					} catch (NumberFormatException event) {
						JOptionPane.showMessageDialog(null, "有非法字符！", "提示", JOptionPane.WARNING_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "折让权限不够", "提示", JOptionPane.WARNING_MESSAGE);

				}

			}

		}
	}

	private class lastListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			addSalesframe.panel2.setVisible(false);
			addSalesframe.panel2.setEnabled(false);
			addSalesframe.panel1.setVisible(true);
			addSalesframe.panel1.setEnabled(true);
		}
	}

	private class showpromotionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

		}
	}

}
