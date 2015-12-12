package ui.stockManage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import businesslogic.LogBL;
import businesslogic.stockManage.PresentBL;
import businesslogicservice.LogBLService;
import businesslogicservice.stockManage.PresentBLService;
import ui.TableModel;
import vo.LogVO;
import vo.stockManage.PresentVO;

@SuppressWarnings("serial")
public class SendPresentNum extends JDialog{
	final SendPresentNum spn;
	Object[][] data;
	JTable table;
	JPanel panel;
	JButton btnEdit,btnCancel;
	
	public SendPresentNum(final SendPresent sp,final StockMain sm){
		spn = this;
		
		this.setSize(800, 300);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int)(width - this.getWidth()) / 2,(int)(height - this.getHeight()) / 2);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("库存赠送");
		this.setModal(true);
		
		btnEdit = new JButton("确认");
		btnCancel = new JButton("取消");
		panel=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel.add(btnEdit);
		panel.add(btnCancel);
		this.add(panel, BorderLayout.SOUTH);
		
		String[] columnTitle = {"赠送数量","编号","名称","型号","库存数量","默认进价","默认售价","最近进价","最近售价"};
		data = sp.newData;
		table = new JTable(new TableModel(data,columnTitle){
			public boolean isCellEditable(int row, int column) {
				return (column == 0);
			}
		});
		this.add(new JScrollPane(table),BorderLayout.CENTER);
		
		btnEdit.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if (e.getButton() == MouseEvent.BUTTON1){
					PresentBLService pbs = new PresentBL();
					for(int i = 0;i < data.length;i ++){
						if(data[i][0].toString().charAt(0) == '-'){
							JOptionPane.showMessageDialog(spn,"输入错误！");
							return;
						}
					}
					ArrayList<String> list = new ArrayList<String>();
					for(int j = 0;j < data.length;j ++){
						String s = (String)data[j][2] + ";" + (String)data[j][3] + ";" + (String)data[j][0];
						list.add(s);
					}
					
					PresentVO pv = new PresentVO(3,list);
					if(pbs.add(pv)){
						JOptionPane.showMessageDialog(spn, "制定赠送单成功！请等待总经理审批");
						LogBLService lbs = new LogBL();
						lbs.addLog(new LogVO(sm.uvo.name,"制定库存赠送单"));
						spn.dispose();
					}
					else{
						JOptionPane.showMessageDialog(spn, "库存数量不足！");
					}
				}
			}
		});
		
		btnCancel.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if (e.getButton() == MouseEvent.BUTTON1){
					spn.dispose();
				}
			}
		});
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				spn.dispose();
			}
		});
		
		this.setVisible(true);
		
	}
}
