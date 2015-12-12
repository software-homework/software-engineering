package ui.stockManage;

import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import vo.LogVO;
import vo.stockManage.CommodityVO;
import vo.stockManage.StockOverflowVO;
import businesslogic.LogBL;
import businesslogic.stockManage.StockOverflowBL;
import businesslogicservice.LogBLService;
import businesslogicservice.stockManage.StockOverflowBLService;

@SuppressWarnings("serial")
public class StockOverflow extends JDialog{
	final StockOverflow so;
	public JLabel label;
	public TextField num;
	public JButton btnEdit, btnCancel;
	StockOverflowBLService sofbs;
	
	public StockOverflow(final StockMain sm){
		so = this;
		sofbs = new StockOverflowBL();
		if (sm.commodity == null){
			return;
		}
		final CommodityVO cv = sm.commodity;
		
		this.setSize(400, 180);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int)(width - this.getWidth()) / 2,(int)(height - this.getHeight()) / 2);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		this.setTitle("库存报溢");
		this.setModal(true);
		
		label = new JLabel("报溢数量：");
		label.setLocation(30, 30);
		label.setSize(100, 30);
		this.add(label);
		
		num = new TextField();
		num.setLocation(135, 30);
		num.setSize(200, 25);
		this.add(num);
		
		btnEdit = new JButton("确认");
		btnEdit.setLocation(70, 90);
		btnEdit.setSize(100, 30);
		this.add(btnEdit);
		btnEdit.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if (e.getButton() == MouseEvent.BUTTON1){
					if(num.getText().isEmpty()){
						JOptionPane.showMessageDialog(so, "输入内容不能为空！");
						return;
					}
	
					StockOverflowVO sofv = new StockOverflowVO(3,cv.commodityname,cv.model,Integer.parseInt(num.getText()));
					sofbs.add(sofv);
					JOptionPane.showMessageDialog(so, "制定报溢单成功！请等待总经理审批");
					LogBLService lbs = new LogBL();
					lbs.addLog(new LogVO(sm.uvo.name,"制定库存报溢单"));
					so.dispose();
				}
			}
		});
		
		btnCancel = new JButton("取消");
		btnCancel.setLocation(230, 90);
		btnCancel.setSize(100, 30);
		this.add(btnCancel);
		btnCancel.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if (e.getButton() == MouseEvent.BUTTON1){
					so.dispose();
				}
			}
		});
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				so.dispose();
			}
		});
		
		this.setVisible(true);
	}
	
}
