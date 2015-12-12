package ui.courier.importmanage;

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

import businesslogic.courier.ImportBL;
import businesslogicservice.courier.ImportBLService;
import ui.TableModel;
import ui.courier.CourierMain;
import vo.courier.ImportVO;
import vo.logistics.CommodityVO;

@SuppressWarnings("serial")
public class ImportPanel extends JPanel{
	ImportBLService ibl;

	JTable importtable;
	JScrollPane iscroll;
	String[] columnTitle={"单据编号","供应商","仓库","操作员","快递","总额","备注","审批情况"};
	String[][] data;
	JButton addImport;
	JButton addImportR;
	JButton refresh;
	JPanel panel;
	int selectrow;
	
	CourierMain salesmain;
	public ImportPanel(CourierMain sm){
		salesmain=sm;
		ibl=new ImportBL();
		
		addImport=new JButton("制定进货单");
		addImportR=new JButton("制定进货退货单");
		addImportR.setEnabled(false);
		refresh=new JButton("刷新");
		panel=new JPanel();
		panel.add(addImport);
		panel.add(addImportR);
		panel.add(refresh);
		
		this.setLayout(new BorderLayout());
		this.add(panel,BorderLayout.NORTH);
		//进货单表
		importtable=new JTable(new TableModel(data, columnTitle){
			public boolean isCellEditable(int row, int column) {
				return (false);
			}
		});
		importtable.getColumnModel().getColumn(0).setMinWidth(120);
		iscroll=new JScrollPane(importtable);
		this.add(iscroll, BorderLayout.CENTER);
		this.setData();
		this.showTable(data);

		//监听
		ActionListener addImportlistener=new addImportListener();
		addImport.addActionListener(addImportlistener);
		ActionListener addImportRlistener=new addImportRListener();
		addImportR.addActionListener(addImportRlistener);
		ActionListener refreshlistener=new refreshListener();
		refresh.addActionListener(refreshlistener);
		importtable.addMouseListener(new MouseAdapter(){
             public void mouseClicked(MouseEvent e){
				selectrow=importtable.getSelectedRow();
				if(data[selectrow][7].equals("通过")&&data[selectrow][0].substring(0, 3).equals("JHD")){
					addImportR.setEnabled(true);
				}
				else{
					addImportR.setEnabled(false);
				}
			}
		});
		
	}
	
	public void showTable(String[][] data){
		this.remove(iscroll);
		importtable=new JTable(new TableModel(data, columnTitle){
			public boolean isCellEditable(int row, int column) {
				return (false);
			}
		});
		importtable.getColumnModel().getColumn(0).setMinWidth(120);
		iscroll=new JScrollPane(importtable);
		this.add(iscroll, BorderLayout.CENTER);
		this.revalidate();
	}
	
	public void setData(){
		ArrayList<ImportVO> list=ibl.ShowI();
		data=new String[list.size()][8];
		for(int i=0;i<list.size();i++){
			data[i][0]=list.get(i).id;
			data[i][1]=list.get(i).customer;
			data[i][2]=String.valueOf(list.get(i).repertory);
			data[i][3]=list.get(i).user;
			String commodity="";
			if(!list.get(i).importList.isEmpty()){
				for(CommodityVO cvo:list.get(i).importList){
					commodity=commodity+","+cvo.commodityname;
				}
				commodity=commodity.substring(1);
			}
			data[i][4]=commodity;
			data[i][5]=String.valueOf(list.get(i).total);
			data[i][6]=list.get(i).remakes;
			if(list.get(i).approval==1){
				data[i][7]="通过";
			}
			else if(list.get(i).approval==2){
				data[i][7]="未通过";
			}
			else{
				data[i][7]="未审批";
			}
		}
	}
	
	class addImportListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(ibl.getiID().trim().equals(null)){
				JOptionPane.showMessageDialog(null,"超过当日添加量","提示",JOptionPane.WARNING_MESSAGE);
			}
			else{
				new AddImportFrame(ibl.getiID(),ImportPanel.this,null);
			}
		}
	}
	
	class addImportRListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(ibl.getirID().trim().equals(null)){
				JOptionPane.showMessageDialog(null,"超过当日添加量","提示",JOptionPane.WARNING_MESSAGE);
			}
			else{
				new AddImportFrame(ibl.getirID(),ImportPanel.this,ibl.findImport(data[selectrow][0]));
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
