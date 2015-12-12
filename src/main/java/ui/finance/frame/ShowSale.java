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
import vo.courier.CourierVO;
import vo.logistics.CommodityVO;
import businesslogic.LogBL;
import businesslogic.courier.CourierBL;
import businesslogic.finance.AccountBL;
import businesslogicservice.LogBLService;
import businesslogicservice.finance.AccountBLService;
import businesslogicservice.finance.GetSalesBL;

@SuppressWarnings("serial")
public class ShowSale  extends JDialog implements ActionListener{
	FinanceFrame jFrame;
	CourierVO salesVO;
	int frameHeight = 520;
	int frameWidth = 400;
	
	JTable jt1;
	JTable jt2;
	JScrollPane jsp1;
	JScrollPane jsp2;
	JPanel jp;
	JLabel jl;
	JLabel top;
	JButton red = new JButton("红冲");
	JButton	redAndCopy = new JButton("红冲并复制");		
	public ShowSale(FinanceFrame jFrame, String title, boolean modal, CourierVO salesVO) {
		super(jFrame, title, modal);
		//获取
		this.jFrame = jFrame;
		this.salesVO = salesVO;
		//设置组件
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Toolkit kit = Toolkit.getDefaultToolkit();    	// 定义工具包
	    Dimension screenSize = kit.getScreenSize();   	// 获取屏幕的尺寸			
	    this.setBounds((screenSize.width - frameWidth) / 2, (screenSize.height - frameHeight) / 3, frameWidth, frameHeight);
		//
	    String[][] customer = new String[1][3];
	    customer[0][0] = salesVO.customer;
	    customer[0][1] = salesVO.remakes;
	    jt1 = new JTable(customer,
	    		new String[]{"销售商", "备注"});
		jsp1 = new JScrollPane(jt1);
		//
		ArrayList<CommodityVO> temp = new ArrayList<CommodityVO>(salesVO.salesList);
		String[][] commodity = new String[temp.size()][6];
		for(int i = 0; i < temp.size(); i++){
			commodity[i][0] = temp.get(i).commodityname;
			commodity[i][1] = temp.get(i).model;
			commodity[i][2] = Integer.toString(temp.get(i).number);
			commodity[i][3] = "仓库1";
			commodity[i][4] = Double.toString(temp.get(i).priceIn);
			commodity[i][5] = Double.toString(temp.get(i).priceOut);
		}
		jt2 = new JTable(commodity, new String[]{"快递名", "型号", "数量", "仓库", "价格", "折扣"});
		jsp2 = new JScrollPane(jt2);
		jp = new JPanel();
		jp.setLayout(new GridLayout(2,1));
		jp.add(jsp1);
		jp.add(jsp2);
		jl = new JLabel("折让: " + salesVO.discount + "   " + "代金券: " + salesVO.Voucher + "    " +
		"折让前总价: " + salesVO.pretotal + "    " + "折让后总价: " + salesVO.posttotal);
		//------------------------------------------------------------------------------
		//
		JPanel jp2 = new JPanel(new BorderLayout());
		jp2.add(jp, BorderLayout.CENTER);
		jp2.add(jl, BorderLayout.SOUTH);
		//------------------------------------------------------------------------------
		JPanel jp3 = new JPanel();
		jp3.setLayout(new FlowLayout(FlowLayout.RIGHT));
		if(this.salesVO.id.split("-")[0].equals("XSTHD"))
			redAndCopy.setEnabled(false);
		else
			redAndCopy.setEnabled(true);
		jp3.add(redAndCopy); red.addActionListener(this);
		jp3.add(red); redAndCopy.addActionListener(this);
		//----------------------------------------------------------------------------
		top = new JLabel(salesVO.id + "    " + "操作员: " + salesVO.user);
		this.add(top, BorderLayout.NORTH);
		this.add(jp2, BorderLayout.CENTER);
		this.add(jp3, BorderLayout.SOUTH);
	
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		AccountBLService accountBL = new AccountBL(); 
		if(arg0.getSource() == red){
			int n = JOptionPane.showConfirmDialog(null, "是否确认红冲?", null,
                    JOptionPane.YES_NO_OPTION);
			if(n == 0){
				/*******/
				GetSalesBL getSalesBL = new CourierBL();
				getSalesBL.approvalSales(this.salesVO.id, 2);
				/*****数量取负**/
				ArrayList<CommodityVO> salesList = this.salesVO.salesList;
				for(int i = 0; i < salesList.size(); i++){
					salesList.get(i).number *= (-1); 
				}
				this.salesVO.salesList = salesList;
				this.salesVO.posttotal *= (-1);
				this.salesVO.pretotal *= (-1);
				this.salesVO.discount *= (-1);
				this.salesVO.Voucher *= (-1);
				this.salesVO.user = jFrame.userVO.name;
				accountBL.red(null, this.salesVO);
				//日志
				LogBLService logBLService = new LogBL();
				logBLService.addLog(new LogVO(jFrame.userVO.name, "红冲"));
				this.jFrame.statusBar.setText("操作成功!");
				this.dispose();
			}
		}
		else if(arg0.getSource() == redAndCopy){
			int n = JOptionPane.showConfirmDialog(null, "是否已完成编辑，进行红冲并复制?", null,
                    JOptionPane.YES_NO_OPTION);
			if(n == 0){
				/*******/
				GetSalesBL getSalesBL = new CourierBL();
				getSalesBL.approvalSales(this.salesVO.id, 2);
				/*****红冲**/
				ArrayList<CommodityVO> salesList = this.salesVO.salesList;
				for(int i = 0; i < salesList.size(); i++){
					salesList.get(i).number *= (-1); 
				}
				this.salesVO.salesList = salesList;
				this.salesVO.posttotal *= (-1);
				this.salesVO.pretotal *= (-1);
				this.salesVO.discount *= (-1);
				this.salesVO.Voucher *= (-1);
				this.salesVO.user = jFrame.userVO.name;
				accountBL.red(null, this.salesVO);
				/*****复制**/
				ArrayList<CommodityVO> salesList2 = this.salesVO.salesList;
				double total = 0.0;
				for(int i = 0; i < salesList2.size(); i++){
					salesList2.get(i).number = Integer.parseInt((String)jt2.getValueAt(i, 2)); 
					total = total + salesList2.get(i).number * salesList2.get(i).priceOut;
				}
				this.salesVO.salesList = salesList2;
				this.salesVO.pretotal = total;
				this.salesVO.posttotal = total - this.salesVO.discount - this.salesVO.Voucher;
				this.salesVO.user = jFrame.userVO.name;
				accountBL.copy(null, salesVO);
				//日志
				LogBLService logBLService = new LogBL();
				logBLService.addLog(new LogVO(jFrame.userVO.name, "红冲并复制"));
				this.jFrame.statusBar.setText("操作成功!");
				this.dispose();
			}
		}
		
	}
}
