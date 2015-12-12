package ui.finance.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import businesslogic.finance.AccountBL;
import businesslogicservice.finance.AccountBLService;
import ui.finance.panel.AccountManagePanel;
import ui.finance.panel.LedgerPanel;
import ui.manager.Log;
import ui.user.LoginFrame;
import ui.user.UserInfo;
import vo.finance.AccountVO;
import vo.finance.DocumentVO;
import vo.finance.LedgerVO;
import vo.salesman.ImportVO;
import vo.salesman.SalesVO;
import vo.stockManage.PresentVO;
import vo.stockManage.StockLossVO;
import vo.stockManage.StockOverflowVO;
import vo.user.UserVO;

@SuppressWarnings("serial")
public class FinanceFrame extends JFrame implements ActionListener, MouseListener {
	/********************************************************
	 * 窗体基本属性
	 ********************************************************/
	JPanel jPanel;
	public UserVO userVO;

	public FinanceFrame(UserVO userVO) {
		this.userVO = userVO;
		//
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		int height = screenSize.height * 3 / 4;
		int width = height * 7 / 4;
		this.setBounds((screenSize.width - width) / 2, (screenSize.height - height) / 2, width, height);

		this.setTitle("财务管理");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 创建菜单栏 状态栏
		this.add(createMenu(), BorderLayout.NORTH);
		this.getContentPane().add(createStatusBar(), BorderLayout.SOUTH);

		// 创建默认JPanel
		jPanel = new AccountManagePanel(this, this.userVO);
		this.add(this.jPanel, BorderLayout.WEST);

		// 创建默认JTable
		ArrayList<AccountVO> data = new ArrayList<AccountVO>();
		this.add(createTable(data, null, null, null, null), BorderLayout.CENTER);

		// 窗口可视
		this.setVisible(true);
	}

	/************************************************************
	 * 菜单栏
	 ************************************************************/
	JMenuBar bar;

	JMenu systemMenu;
	JMenuItem exitMenuItem;
	JMenuItem cancelMenuItem;
	JMenuItem showLog;
	JMenuItem selfMsg;

	JMenu operateMenu;
	JMenuItem accountManageMenuItem;
	JMenu documentMenu;
	JMenu tableMenu;
	JMenuItem initAccountMenuItem;

	JMenuItem receiptMenuItem;
	JMenuItem paymentMennuItem;
	JMenuItem cashExpensesMennuItem;
	JMenuItem detailMennuItem;
	JMenuItem historyMennuItem;
	JMenuItem conditionMennuItem;

	public JMenuBar createMenu() {
		// MenuBar
		this.bar = new JMenuBar();

		// Menu1 系统
		this.systemMenu = new JMenu("系统");
		this.exitMenuItem = new JMenuItem("退出系统");
		this.cancelMenuItem = new JMenuItem("注销用户");
		this.selfMsg = new JMenuItem("修改密码");
		this.showLog = new JMenuItem("查看日志");
		this.systemMenu.add(this.cancelMenuItem);
		this.systemMenu.add(this.exitMenuItem);
		this.systemMenu.add(this.showLog);
		this.systemMenu.add(this.selfMsg);
		// Menu2 操作
		this.operateMenu = new JMenu("操作");
		this.accountManageMenuItem = new JMenuItem("账户管理");
		this.documentMenu = new JMenu("创建单据");
		this.tableMenu = new JMenu("查看表格");
		this.initAccountMenuItem = new JMenuItem("账本管理");
		this.operateMenu.add(this.accountManageMenuItem);
		this.operateMenu.add(this.documentMenu);
		this.operateMenu.add(this.tableMenu);
		this.operateMenu.add(this.initAccountMenuItem);

		// Menu2->单据->
		this.receiptMenuItem = new JMenuItem("收款单");
		this.paymentMennuItem = new JMenuItem("付款单");
		this.cashExpensesMennuItem = new JMenuItem("现金费用单");
		this.documentMenu.add(this.receiptMenuItem);
		this.documentMenu.add(this.paymentMennuItem);
		this.documentMenu.add(this.cashExpensesMennuItem);

		// Menue2->表格
		this.detailMennuItem = new JMenuItem("销售明细表");
		this.historyMennuItem = new JMenuItem("经营历程表");
		this.conditionMennuItem = new JMenuItem("经营情况表");
		this.tableMenu.add(detailMennuItem);
		this.tableMenu.add(historyMennuItem);
		this.tableMenu.add(conditionMennuItem);

		// bar->
		this.bar.add(this.systemMenu);
		this.bar.add(this.operateMenu);

		// 添加监听
		this.exitMenuItem.addActionListener(this);
		this.cancelMenuItem.addActionListener(this);
		this.showLog.addActionListener(this);
		this.accountManageMenuItem.addActionListener(this);
		this.receiptMenuItem.addActionListener(this);
		this.paymentMennuItem.addActionListener(this);
		this.cashExpensesMennuItem.addActionListener(this);
		this.detailMennuItem.addActionListener(this);
		this.historyMennuItem.addActionListener(this);
		this.conditionMennuItem.addActionListener(this);
		this.initAccountMenuItem.addActionListener(this);
		this.selfMsg.addActionListener(this);
		return this.bar;
	}

	/**************************************************************
	 * 状态栏
	 ***************************************************************/
	JLabel statusBar;

	public JToolBar createStatusBar() {
		this.statusBar = new JLabel("欢迎登入逆风快递系统!");
		JToolBar toolBar = new JToolBar();
		toolBar.add(statusBar);
		toolBar.setFloatable(false);

		return toolBar;
	}

	public JLabel getStatusBar() {
		return this.statusBar;
	}

	/***************************************************************
	 * 创建Table
	 ***************************************************************/
	JTable table;
	JScrollPane sp;

	public JScrollPane createTable(ArrayList<AccountVO> data1, String[][] data2, String[][] data3, String[][] data4,
			String[][] data5) {
		String[][] data = null;
		String[] columnNames = null;
		if (data1 != null) {
			columnNames = new String[] { "账户名称", "账户金额" };
			data = new String[data1.size()][2];

			for (int i = 0; i < data1.size(); i++) {
				data[i] = new String[] { data1.get(i).id, data1.get(i).amountOfAccount };
			}
		} else if (data2 != null) {
			// 销售明细
			columnNames = new String[] { "时间", "商品名", "型号", "数量", "单价", "总额" };
			data = data2;
		} else if (data3 != null) {
			// 经营历程
			columnNames = new String[] { "编号" };
			data = data3;
		} else if (data4 != null) {
			// 经营情况
			data = data4;
			columnNames = new String[] { "销售收入", "商品类收入", "折让", "折让后总收入", "销售成本", "商品类支出", "总支出", "利润" };
		} else if (data5 != null) {
			columnNames = new String[] { "建账时间", "账本类型" };
			data = data5;
		}

		this.table = new JTable(data, columnNames) {
			public boolean isCellEditable(int row, int column) {
				if (column == 0) {// 让column为0那一列不可用
					return false;
				} else
					return true;
			}
		};
		this.table.addMouseListener(this);
		this.table.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		this.table.setAutoCreateRowSorter(true);

		sp = new JScrollPane(this.table);
		return this.sp;
	}

	public JScrollPane getScrollPanel() {
		return this.sp;
	}

	public JTable getTable() {
		return this.table;
	}

	/****************************************************************
	 * 监听
	 ****************************************************************/
	@SuppressWarnings("unused")
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == this.exitMenuItem) {
			int n = JOptionPane.showConfirmDialog(null, "是否确认当前操作?", null, JOptionPane.YES_NO_OPTION);
			if (n == 0)
				System.exit(0);
		} else if (arg0.getSource() == this.cancelMenuItem) {
			int n = JOptionPane.showConfirmDialog(null, "是否确认当前操作?", null, JOptionPane.YES_NO_OPTION);
			if (n == 0) {
				this.dispose();
				LoginFrame loginFrame = new LoginFrame();
			}
		} else if (arg0.getSource() == this.showLog)
			new Log();
		else if (arg0.getSource() == this.selfMsg)
			new UserInfo(this.userVO, this);
		else if (arg0.getSource() == this.accountManageMenuItem) // 切换Panel,刷新Table
		{
			this.remove(this.jPanel);
			this.jPanel = new AccountManagePanel(this, this.userVO);
			this.add(this.jPanel, BorderLayout.WEST);

			ArrayList<AccountVO> data;
			data = new ArrayList<AccountVO>();

			this.remove(this.sp);
			this.add(this.createTable(data, null, null, null, null), BorderLayout.CENTER);
			this.setVisible(true);
		} else if (arg0.getSource() == this.initAccountMenuItem) // 切换Panel
		{
			this.remove(this.jPanel);
			this.jPanel = new LedgerPanel(this);
			this.add(this.jPanel, BorderLayout.WEST);
			this.statusBar.setText("期初建账时间为每年年初!");

			AccountBLService accountBLService = new AccountBL();
			ArrayList<LedgerVO> ledger = accountBLService.showLedger();
			String[][] data = new String[ledger.size()][2];
			for (int i = 0; i < ledger.size(); i++) {
				data[i][0] = ledger.get(i).time.substring(0, 4) + "/" + ledger.get(i).time.substring(4, 6) + "/"
						+ ledger.get(i).time.substring(6, 8);
				data[i][1] = ledger.get(i).type;
			}
			// 刷新Table
			this.remove(this.getScrollPanel());
			this.add(this.createTable(null, null, null, null, data), BorderLayout.CENTER);
			this.setVisible(true);
		} else if (arg0.getSource() == this.receiptMenuItem) {
			DocumentDialog documentDialog = new DocumentDialog(this, "收款单", true, "SKD", this.userVO.name);
		} else if (arg0.getSource() == this.paymentMennuItem) {
			DocumentDialog documentDialog = new DocumentDialog(this, "付款单", true, "FKD", this.userVO.name);
		} else if (arg0.getSource() == this.cashExpensesMennuItem) {
			CashExpensesDialog cashExpensesDialog = new CashExpensesDialog(this, "现金费用单", true, this.userVO);
		} else if (arg0.getSource() == this.detailMennuItem) {
			new FilterDialog(this, "筛选", true, new String[] { "时间(yyyyMMdd):", "商品名:", "客户:", "业务员:", "仓库:" });
		} else if (arg0.getSource() == this.historyMennuItem) {
			new FilterDialog2(this, "筛选", true, new String[] { "时间(yyyyMMdd):", "单据类型:", "客户:", "业务员:", "仓库:" });
		} else if (arg0.getSource() == this.conditionMennuItem) {
			new FilterDialog3(this, "筛选", true);
		}
	}

	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getClickCount() == 2) {
			AccountBLService abl = new AccountBL();
			int n = this.table.getSelectedRow();
			String id = (String) this.table.getValueAt(n, 0);
			if (id.split("-")[0].equals("SKD")) {
				DocumentVO documentVO = abl.findOneDocument(id);
				new ShowDocument(this, "收款单", true, documentVO);
			} else if (id.split("-")[0].equals("XJFYD")) {
				new ShowCashExpenses(this, "现金费用单", true, abl.findOneCashExpenses(id));
			} else if (id.split("-")[0].equals("FKD")) {
				DocumentVO documentVO = abl.findOneDocument(id);
				new ShowDocument(this, "付款单", true, documentVO);
			} else if (id.split("-")[0].equals("JHD")) {
				ImportVO importVO = abl.findOneImport(id);
				new ShowImport(this, "进货单", true, importVO);
			} else if (id.split("-")[0].equals("JHTHD")) {
				ImportVO importVO = abl.findOneImport(id);
				new ShowImport(this, "进货退货单", true, importVO);
			} else if (id.split("-")[0].equals("XSD")) {
				SalesVO salesVO = abl.findOneSales(id);
				new ShowSale(this, "销售单", true, salesVO);
			} else if (id.split("-")[0].equals("XSTHD")) {
				SalesVO salesVO = abl.findOneSales(id);
				new ShowSale(this, "销售退货单", true, salesVO);
			} else if (id.split("-")[0].equals("BYD")) {
				StockOverflowVO stockOverflowVO = abl.findOneStockOverflow(id);
				new ShowStockBill(this, "报溢单", true, null, stockOverflowVO);
			} else if (id.split("-")[0].equals("BSD")) {
				StockLossVO stockLossVO = abl.findOneStockLoss(id);
				new ShowStockBill(this, "报损单", true, stockLossVO, null);
			} else if (id.split("-")[0].equals("ZSD")) {
				PresentVO presentVO = abl.findOnePresent(id);
				new ShowPresent(this, "赠送单", true, presentVO);
			} else if (this.table.getColumnName(0).equals("建账时间")) {
				AccountBLService accountBL = new AccountBL();
				LedgerVO ledgerVO = accountBL.findOneLedger(
						id.split("/")[0].toString() + id.split("/")[1].toString() + id.split("/")[2].toString());
				new LedgerDialog(this, "账本", true, ledgerVO);
			}
		}
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}
}
