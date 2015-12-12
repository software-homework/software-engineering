package ui.finance.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import vo.finance.DocumentVO;

@SuppressWarnings("serial")
public class ShowDocument extends JDialog{
	FinanceFrame jFrame;
	DocumentVO documentVO;
	int frameHeight = 520;
	int frameWidth = 400;
	
	JTable jt1;
	JTable jt2;
	JScrollPane jsp1;
	JScrollPane jsp2;
	JPanel jp;
	JLabel jl;
	JLabel top;
		
	public ShowDocument(FinanceFrame jFrame, String title, boolean modal, DocumentVO documentVO) {
		super(jFrame, title, modal);
		//获取
		this.jFrame = jFrame;
		this.documentVO = documentVO;
		//设置组件
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Toolkit kit = Toolkit.getDefaultToolkit();    	// 定义工具包
	    Dimension screenSize = kit.getScreenSize();   	// 获取屏幕的尺寸			
	    this.setBounds((screenSize.width - frameWidth) / 2, (screenSize.height - frameHeight) / 3, frameWidth, frameHeight);
		//
	    String[][] customer = new String[1][2];
	    customer[0][0] = documentVO.customerName;
	    customer[0][1] = documentVO.customerType;
	    jt1 = new JTable(customer,
	    		new String[]{"客户名", "客户类型"});
		jsp1 = new JScrollPane(jt1);
		//
		ArrayList<String> transferList = new ArrayList<String>(documentVO.transferList);
		String[][] transfer = new String[transferList.size()][3];
		for(int i = 0; i < transferList.size(); i++){
			transfer[i][0] = transferList.get(i).split(";")[0];
			transfer[i][1] = transferList.get(i).split(";")[1];
			transfer[i][2] = transferList.get(i).split(";")[2];
		}
		jt2 = new JTable(transfer, new String[]{"银行账户", "转账金额", "备注"});
		jsp2 = new JScrollPane(jt2);
		//add to jp
		jp = new JPanel();
		jp.setLayout(new GridLayout(2,1));
		jp.add(jsp1);
		jp.add(jsp2);
		//add
		jl = new JLabel("总和: " + documentVO.total);
		//------------------------------------------------------------------------------
		top = new JLabel(documentVO.id + "    " + "操作员: " + documentVO.operator);
		this.add(top, BorderLayout.NORTH);
		this.add(jp, BorderLayout.CENTER);
		this.add(jl, BorderLayout.SOUTH);
	
		this.setVisible(true);
	}
}
