package ui.salesman.importmanage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import businesslogic.salesman.GetCommodity;
import businesslogic.stockManage.CommodityBL;
import ui.TableModel;
import vo.salesman.ImportVO;
import vo.stockManage.CommodityVO;

@SuppressWarnings("serial")
public class ImportP1 extends JPanel{
	GetCommodity gc=new CommodityBL();
	ArrayList<CommodityVO> cList=new ArrayList<CommodityVO>();
	JPanel check;
	JButton next;
	JButton rnext;
	JButton cancel;
	JTable commoditytable;
	Object[][] data;
	Object[][] newdata;
	AddImportFrame addImportframe;
	ImportVO importvo;
	
	//进货单
	public ImportP1(AddImportFrame aif){
		addImportframe=aif;
		this.setLayout(new BorderLayout());
		
		
		//表格
		cList=gc.getCommodity();
		setData(cList);
		String[] columnTitle={"","编号","名称","型号","默认进价","库存数量"};
		commoditytable = new JTable(new TableModel(data, columnTitle));
		commoditytable.getColumnModel().getColumn(0).setMaxWidth(30);//设置第一列宽度
		//确认面板
		check=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		next=new JButton("下一步");
		next.setEnabled(false);
		cancel=new JButton("取消");
		check.add(next);
		check.add(cancel);
		
		//添加组件
		this.add(new JScrollPane(commoditytable),BorderLayout.CENTER);
		this.add(check, BorderLayout.SOUTH);
		
		//监听
		ActionListener nextlistener=new nextListener();
		next.addActionListener(nextlistener);
		ActionListener cancellistener=new cancelListener();
		cancel.addActionListener(cancellistener);
		commoditytable.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			boolean j=false;
			for(int i = 0; i < data.length; i++) {
				if(data[i][0] == Boolean.TRUE) {
						next.setEnabled(true);
						j=true;
						break;
				}
			}
			if(j==false){
				next.setEnabled(false);
			}
		}
	});
	}
	
	//进货退货单
	public ImportP1(AddImportFrame aif,ImportVO ivo){
		addImportframe=aif;
		importvo=new ImportVO();
		importvo=ivo;
		this.setLayout(new BorderLayout());
		
		//表格
		setData(ivo.importList);
		String[] columnTitle={"","编号","名称","型号","默认进价","此单进货数量"};
		commoditytable = new JTable(new TableModel(data, columnTitle));
		commoditytable.getColumnModel().getColumn(0).setMaxWidth(30);//设置第一列宽度
		//确认面板
		check=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		rnext=new JButton("下一步");
		rnext.setEnabled(false);
		cancel=new JButton("取消");
		check.add(rnext);
		check.add(cancel);
		
		//添加组件
		this.add(new JScrollPane(commoditytable),BorderLayout.CENTER);
		this.add(check, BorderLayout.SOUTH);
		
		//监听
		ActionListener rnextlistener=new rnextListener();
		rnext.addActionListener(rnextlistener);
		ActionListener cancellistener=new cancelListener();
		cancel.addActionListener(cancellistener);
		commoditytable.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			boolean j=false;
			for(int i = 0; i < data.length; i++) {
				if(data[i][0] == Boolean.TRUE) {
						rnext.setEnabled(true);
						j=true;
						break;
				}
			}
			if(j==false){
				rnext.setEnabled(false);
			}
		}
	});
	}
	
	
	public void setData(ArrayList<CommodityVO> list){
		data=new Object[list.size()][6];
		for(int i=0;i<list.size();i++){
			data[i][0]=Boolean.FALSE;
			data[i][1]=list.get(i).id;
			data[i][2]=list.get(i).commodityname;
			data[i][3]=list.get(i).model;
			data[i][4]=String.valueOf(list.get(i).priceIn);
			data[i][5]=String.valueOf(list.get(i).number);
		}
	}
	
	private class nextListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int count=0;
			for(int i = 0; i < data.length; i++){
				if(data[i][0]==Boolean.TRUE){
					count++;
				}
			}
			newdata=new Object[count][6];
			count=0;
			for(int i = 0; i < data.length; i++){
				if(data[i][0]==Boolean.TRUE){
					newdata[count][0]="0";
					newdata[count][1]=data[i][1];
					newdata[count][2]=data[i][2];
					newdata[count][3]=data[i][3];
					newdata[count][4]=data[i][4];
					newdata[count][5]=data[i][5];
					count++;
				}
			}
			addImportframe.panel2=new ImportP2(newdata,addImportframe);
			addImportframe.panel1.setVisible(false);
			addImportframe.panel1.setEnabled(false);
			addImportframe.add(addImportframe.panel2,BorderLayout.CENTER);
		}
	}
	
	private class rnextListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int count=0;
			for(int i = 0; i < data.length; i++){
				if(data[i][0]==Boolean.TRUE){
					count++;
				}
			}
			newdata=new Object[count][6];
			count=0;
			for(int i = 0; i < data.length; i++){
				if(data[i][0]==Boolean.TRUE){
					newdata[count][0]=data[i][5];
					newdata[count][1]=data[i][1];
					newdata[count][2]=data[i][2];
					newdata[count][3]=data[i][3];
					newdata[count][4]=data[i][4];
					newdata[count][5]=data[i][5];
					count++;
				}
			}
			addImportframe.panel2=new ImportP2(newdata,addImportframe,importvo);
			addImportframe.panel1.setVisible(false);
			addImportframe.panel1.setEnabled(false);
			addImportframe.add(addImportframe.panel2,BorderLayout.CENTER);
		}
	}
	private class cancelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			addImportframe.dispose();
		}
	}

}
