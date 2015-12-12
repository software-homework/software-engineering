package ui.courier.manage;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import businesslogic.courier.CourierBL;
import businesslogicservice.courier.CourierBLService;
import ui.TableModel;
import ui.courier.SalesMain;
import vo.courier.CourierVO;
import vo.logistics.CommodityVO;

@SuppressWarnings("serial")
public class CourierPanel extends JPanel{
	CourierBLService sbl;
	
	JTable salestable;
	JScrollPane sscroll;
	JButton addSales;
	JButton addSalesR;
	JButton refresh;
	String[] columnTitle={"单据编号","供应商","仓库","操作员","快递","折让前总额","折让后总额","备注","审批情况"};
	String[][] data;
	JPanel panel;
	int selectrow;
	
	SalesMain salesmain;
	public CourierPanel(SalesMain sm){
		sbl=new CourierBL();
		salesmain=sm;
		
		addSales=new JButton("制定销售单");
		addSalesR=new JButton("制定销售退货单");
		addSalesR.setEnabled(false);
		refresh=new JButton("刷新");
		panel=new JPanel();
		panel.add(addSales);
		panel.add(addSalesR);
		panel.add(refresh);
		this.setLayout(new BorderLayout());
		this.add(panel,BorderLayout.NORTH);
		
		//销售表单
		salestable=new JTable(new TableModel(data, columnTitle){
			public boolean isCellEditable(int row, int column) {
				return (false);
			}
		});
		salestable.getColumnModel().getColumn(0).setMinWidth(120);
		sscroll=new JScrollPane(salestable);
		this.add(sscroll, BorderLayout.CENTER);
		this.setData();
		this.showTable(data);
		
		//监听
		ActionListener addSaleslistener=new addSalesListener();
		addSales.addActionListener(addSaleslistener);
		ActionListener addSalesRlistener=new addSalesRListener();
		addSalesR.addActionListener(addSalesRlistener);
		ActionListener refreshlistener=new refreshListener();
		refresh.addActionListener(refreshlistener);
		salestable.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
            	selectrow=salestable.getSelectedRow();
            	if(data[selectrow][0].substring(0, 3).equals("XSD")&&data[selectrow][8].equals("通过")){
            		addSalesR.setEnabled(true);
            	}
            	else{
            		addSalesR.setEnabled(false);
            	}
            }
		});
		
		salestable.setVisible(true);
	}
	
	public void showTable(String[][] data){
		this.remove(sscroll);
		salestable=new JTable(new TableModel(data, columnTitle){
			public boolean isCellEditable(int row, int column) {
				return (false);
			}
		});
		salestable.getColumnModel().getColumn(0).setMinWidth(120);
		sscroll=new JScrollPane(salestable);
		this.add(sscroll, BorderLayout.CENTER);
		this.revalidate();
	}
	public void setData(){
		ArrayList<CourierVO> list=sbl.ShowS();
		data=new String[list.size()][9];
		for(int i=0;i<list.size();i++){
			data[i][0]=list.get(i).id;
			data[i][1]=list.get(i).customer;
			data[i][2]=String.valueOf(list.get(i).repertory);
			data[i][3]=list.get(i).user;
			String commodity="";
			if(!list.get(i).salesList.isEmpty()){
				for(CommodityVO cvo:list.get(i).salesList){
					commodity=commodity+","+cvo.commodityname;
				}
				commodity=commodity.substring(1);
			}
			data[i][4]=commodity;
			data[i][5]=String.valueOf(list.get(i).pretotal);
			data[i][6]=String.valueOf(list.get(i).posttotal);
			data[i][7]=list.get(i).remakes;
			if(list.get(i).approval==1){
				data[i][8]="通过";
			}
			else if(list.get(i).approval==2){
				data[i][8]="未通过";
			}
			else{
				data[i][8]="未审批";
			}
		}
	}
	
	class addSalesListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(sbl.getsID().trim().equals(null)){
				JOptionPane.showMessageDialog(null,"超过当日添加量","提示",JOptionPane.WARNING_MESSAGE);
			}
			else{
				new AddSalesFrame(sbl.getsID(),CourierPanel.this,null);
			}
		}
	}
	
	class addSalesRListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(sbl.getsrID().trim().equals(null)){
				JOptionPane.showMessageDialog(null,"超过当日添加量","提示",JOptionPane.WARNING_MESSAGE);
			}
			else{
				new AddSalesFrame(sbl.getsrID(),CourierPanel.this,sbl.findSales(data[selectrow][0]));
			}
		}
	}
	
	class refreshListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			setData();
			showTable(data);
			salesmain.setStatusBar("刷新成功");
		}
	}
		
	

}
