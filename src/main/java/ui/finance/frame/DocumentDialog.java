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

import vo.LogVO;
import vo.courier.CustomerVO;
import vo.finance.AccountVO;
import businesslogic.LogBL;
import businesslogic.courier.CustomerBL;
import businesslogic.finance.AccountBL;
import businesslogicservice.LogBLService;
import businesslogicservice.finance.AccountBLService;
import businesslogicservice.finance.GetCustomerBL;

@SuppressWarnings("serial")
public class DocumentDialog extends JDialog implements ActionListener{
	FinanceFrame jFrame;
	int frameHeight = 520;
	int frameWidth = 400;
	
	String type;			//表单类型
	String operator;		//操作员
	String customerName;
	String customerType;
	String time;
	long idNum;
	String id; 				//编号
	ArrayList<String> transferList = new ArrayList<String>();
	double total = 0;
	AccountBLService accountBLService = new AccountBL();
	GetCustomerBL getCustomerBL = new CustomerBL();
	
	JButton confirm;
	JLabel top;
    JScrollPane jsp1;
    JTable jt1;
    JScrollPane jsp2;
	JTable jt2;
	JPanel jp = new JPanel();
	JPanel jp2 = new JPanel();
	ArrayList<AccountVO> accountTemp = new ArrayList<AccountVO>();
	public DocumentDialog (FinanceFrame jFrame, String title, boolean modal, String type, String operator){
		super(jFrame, title, modal);
		this.jFrame = jFrame;
		this.type = type;
		this.operator = operator;
		this.id = accountBLService.createID(type);
		this.time = this.id.substring(4, 12);
		this.idNum = Long.parseLong(this.id.substring(13));
		//---------------------------------------------------------------------------------
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Toolkit kit = Toolkit.getDefaultToolkit();    	// 定义工具包
	    Dimension screenSize = kit.getScreenSize();   	// 获取屏幕的尺寸			
	    this.setBounds((screenSize.width - frameWidth) / 2, (screenSize.height - frameHeight) / 3, frameWidth, frameHeight);
	    //---------------------------------------------------------------------------------
	    //Button
		confirm = new JButton("<html>确定(<u>Y</u>)</html>)");
		this.confirm.addActionListener(this);		
		//two Table
		ArrayList<CustomerVO> customerTemp = getCustomerBL.ShowC();
		String[][] customer = new String[customerTemp.size()][2];
		for(int i = 0; i < customerTemp.size(); i++){
			customer[i] = new String[]{customerTemp.get(i).name, customerTemp.get(i).type};
		}
		jt1 = new JTable(customer, new String[]{"客户名", "客户类型"}){
			  public boolean isCellEditable(int row, int column) {
				   	if (column == 0 || column == 1) {//让column为0, 1那一列不可用
				   		return false;
				   	} else
				   		return true;
				  	}
		};
		jsp1 = new JScrollPane(jt1);
		accountTemp = accountBLService.findAll("");
		String[][] account = new String[accountTemp.size()][3];
		for(int i = 0; i < accountTemp.size(); i++){
			account[i] = new String[]{accountTemp.get(i).id, "0", ""};
		}
		jt2 = new JTable(account, new String[]{"银行账户", "转账金额", "备注"}){
			  public boolean isCellEditable(int row, int column) {
				   	if (column == 0) {//让column为0那一列不可用
				   		return false;
				   	} else
				   		return true;
				  	}
		};
		jsp2 = new JScrollPane(jt2);
		//add to panel1
		this.jp.setLayout(new GridLayout(2,1));
		jp.add(jsp1);
		jp.add(jsp2);
		//add to jp2
		jp2.setLayout(new FlowLayout(FlowLayout.RIGHT));
		jp2.add(confirm);
		//------------------------------------------------------------------------------
		this.top = new JLabel(this.id + "    操作员: " + this.operator);
		this.add(top, BorderLayout.NORTH);
		this.add(jp, BorderLayout.CENTER);
		this.add(jp2, BorderLayout.SOUTH);
		this.setVisible(true);
	}

	/***************************************************************
	 * 监听
	 ***************************************************************/
	public void actionPerformed(ActionEvent arg0){
		if(arg0.getSource() == confirm){
			int[] num1 = jt1.getSelectedRows();
			int[] num2 = jt2.getSelectedRows();
			boolean isSuccess;
			
			if(num1.length > 1)
				JOptionPane.showMessageDialog(null, "客户选择数量唯一!", null, 0);
			else if(num1.length == 0)
				JOptionPane.showMessageDialog(null, "请选择客户!", null, 0);
			else if(num2.length == 0)
				JOptionPane.showMessageDialog(null, "请选择账户!", null, 0);
			else{
				customerName = (String) jt1.getValueAt(num1[0], 0);
				customerType = (String) jt1.getValueAt(num1[0], 1);
				for(int i = 0; i < num2.length; i++){
					if(((String)jt2.getValueAt(num2[i], 2)).trim().length() == 0)
						transferList.add(jt2.getValueAt(num2[i], 0) + ";" + jt2.getValueAt(num2[i], 1) + ";" + "无");
					else
						transferList.add(jt2.getValueAt(num2[i], 0) + ";" + jt2.getValueAt(num2[i], 1) + ";" + jt2.getValueAt(num2[i], 2));
					total = Double.parseDouble((String)jt2.getValueAt(num2[i], 1)) + total;
				}
				
				String res = this.judgeAmount(accountTemp, num2, type);
				if(res.equals("")){
					isSuccess = accountBLService.addDocuments(type, id, customerName, customerType, operator, transferList, total, time, idNum);
					if(isSuccess){
						this.jFrame.getStatusBar().setText("添加成功!等待审批!");
						//日志
						LogBLService logBLService = new LogBL();
						if(type.equals("SKD"))
							logBLService.addLog(new LogVO(jFrame.userVO.name, "创建收款单"));
						else if(type.equals("FKD"))
							logBLService.addLog(new LogVO(jFrame.userVO.name, "创建付款单"));
						this.dispose();
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "账户: " + res + " 余额不足", null, 0);
					total = 0;
				}
			}			
		}
	}
	
	public String judgeAmount(ArrayList<AccountVO> list, int[] num2, String type){
		if(type.equals("SKD"))
			return "";
		else{
			for(int i = 0; i < num2.length; i++){
				for(int j = 0; j < list.size(); j++){
					if(list.get(j).id.equals(jt2.getValueAt(num2[i], 0))){
						if(Double.parseDouble(list.get(j).amountOfAccount) < Double.parseDouble((String)jt2.getValueAt(num2[i], 1)))
							return list.get(j).id;
						else
							break;
					}
				}
			}
			return "";
		}
	}
}
