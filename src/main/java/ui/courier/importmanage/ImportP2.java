package ui.courier.importmanage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import vo.courier.ImportVO;

@SuppressWarnings("serial")
public class ImportP2 extends JPanel{
	CustomerBLService cbl;
	ImportVO ivo;
	AddImportFrame addImportframe;
	JPanel title;
	JPanel in;
	JPanel check;
	JPanel write;
	JButton next;
	JButton rnext;
	JButton cancel;
	JButton last;
	JLabel customer;
	JLabel repertory;
	JLabel user;
	JLabel remakes;
	JLabel getuser;
	JLabel getcustomer;
	JTextField getrep;
	JTextField getremakes;
	JComboBox<String> customerbox;
	JTable commoditytable;
	Object[][] data;
	String[] customername;
	Object[][] newdata;
	//快递接单
	public ImportP2(Object[][] cdata,AddImportFrame aif){
		this.setLayout(new BorderLayout());
		addImportframe=aif;
		
		//供应商列表
		cbl=new CustomerBL();		
		ArrayList<CustomerVO> list=new ArrayList<CustomerVO>();
		list=cbl.ShowC();
		int count=0;
		for(CustomerVO cvo:list){
			if(cvo.type.equals("供应商")){
				count++;
			}
		}
		if(count!=0){
			customername=new String[count];
		    count=0;
		    for(CustomerVO cvo:list){
			    if(cvo.type.equals("供应商")){
				customername[count]=cvo.name;
				count++;
			  }
		   }
		}
		else{
			customername=new String[]{"空"};
		}
		
		
		//头面板
		user=new JLabel("操作员:");
		getuser=new JLabel(addImportframe.username);
		title=new JPanel(new FlowLayout(FlowLayout.LEFT));
		title.add(user);
		title.add(getuser);
		
		//输入面板
		customer=new JLabel("供应商:", JLabel.CENTER);
		repertory=new JLabel("仓库:", JLabel.CENTER);
		remakes=new JLabel("备注:", JLabel.CENTER);
		getremakes=new JTextField(20);
		getrep=new JTextField("1",20);
		getrep.setEnabled(false);
		customerbox=new JComboBox<String>(customername);
		
		String[] columnTitle={"数量(可修改)","编号","名称","型号","价格(可修改)","物流数量"};
		data=cdata;
		commoditytable = new JTable(new TableModel(data, columnTitle){
			public boolean isCellEditable(int row, int column) {
				return (column == 0||column == 4);
			}
		});
		
		write=new JPanel(new GridLayout(3,2,0,25));
		write.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 45));
		write.add(customer);
		write.add(customerbox);
		write.add(repertory);
		write.add(getrep);
		write.add(remakes);
		write.add(getremakes);
		in=new JPanel(new GridLayout(2,1));
		in.add(new JScrollPane(commoditytable));
		in.add(write);
	
		//确认面板
		next=new JButton("下一步");
		cancel=new JButton("取消");
		last=new JButton("上一步");
		check=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		check.add(last);
		check.add(next);
		check.add(cancel);
		
		//添加组件
		this.add(title, BorderLayout.NORTH);
		this.add(in, BorderLayout.CENTER);
		this.add(check, BorderLayout.SOUTH);
		//监听
		ActionListener nextlistener=new nextListener();
		next.addActionListener(nextlistener);
		ActionListener cancellistener=new cancelListener();
		cancel.addActionListener(cancellistener);
		ActionListener lastlistener=new lastListener();
		last.addActionListener(lastlistener);
	}
	//快递接退货单
	public ImportP2(Object[][] cdata,AddImportFrame aif,ImportVO ivo){
		this.setLayout(new BorderLayout());
		addImportframe=aif;
		
		//头面板
		user=new JLabel("操作员:");
		getuser=new JLabel(addImportframe.username);
		title=new JPanel(new FlowLayout(FlowLayout.LEFT));
		title.add(user);
		title.add(getuser);
		
		//输入面板
		customer=new JLabel("供应商:", JLabel.CENTER);
		repertory=new JLabel("仓库:", JLabel.CENTER);
		remakes=new JLabel("备注:", JLabel.CENTER);
		getremakes=new JTextField(20);
		getrep=new JTextField("1",20);
		getrep.setEnabled(false);
        getcustomer=new JLabel(ivo.customer,JLabel.CENTER);
		
		String[] columnTitle={"退货数量","编号","名称","型号","价格(可修改)","快递接数量"};
		data=cdata;
		commoditytable = new JTable(new TableModel(data, columnTitle){
			public boolean isCellEditable(int row, int column) {
				return (column == 4);
			}
		});
		
		write=new JPanel(new GridLayout(3,2,0,25));
		write.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 45));
		write.add(customer);
		write.add(getcustomer);
		write.add(repertory);
		write.add(getrep);
		write.add(remakes);
		write.add(getremakes);
		in=new JPanel(new GridLayout(2,1));
		in.add(new JScrollPane(commoditytable));
		in.add(write);
	
		//确认面板
		rnext=new JButton("下一步");
		cancel=new JButton("取消");
		last=new JButton("上一步");
		check=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		check.add(last);
		check.add(rnext);
		check.add(cancel);
		
		//添加组件
		this.add(title, BorderLayout.NORTH);
		this.add(in, BorderLayout.CENTER);
		this.add(check, BorderLayout.SOUTH);
		//监听
		ActionListener rnextlistener=new rnextListener();
		rnext.addActionListener(rnextlistener);
		ActionListener cancellistener=new cancelListener();
		cancel.addActionListener(cancellistener);
		ActionListener lastlistener=new lastListener();
		last.addActionListener(lastlistener);
	}
	
	private class cancelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			addImportframe.dispose();
		}
	}
	private class nextListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(customerbox.getSelectedItem().equals("空")||getremakes.getText().isEmpty()){
				JOptionPane.showMessageDialog(null,"未填写完整","提示",JOptionPane.WARNING_MESSAGE);
			}
			else{
				try{
					newdata=new Object[data.length][6];
					for(int i=0;i<data.length;i++){
						newdata[i][0]=data[i][1];
						newdata[i][1]=data[i][2];
						newdata[i][2]=data[i][3];
						newdata[i][3]=data[i][4];
						newdata[i][4]=data[i][0];
						newdata[i][5]=String.valueOf(Integer.parseInt(data[i][0].toString())*Double.parseDouble(data[i][4].toString()));
					}
					ivo=new ImportVO();
					ivo.customer=(String)customerbox.getSelectedItem();
					ivo.remakes=getremakes.getText();
					ivo.repertory=Integer.parseInt(getrep.getText());
					ivo.user=getuser.getText();
					
					addImportframe.panel3=new ImportP3(newdata,ivo,addImportframe);
					addImportframe.panel2.setVisible(false);
					addImportframe.panel2.setEnabled(false);
					addImportframe.add(addImportframe.panel3,BorderLayout.CENTER);
				}catch(NumberFormatException event){
					JOptionPane.showMessageDialog(null,"有非法字符！","提示",JOptionPane.WARNING_MESSAGE);
				}
			}
			
		}
	}
	
	private class rnextListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(getremakes.getText().isEmpty()){
				JOptionPane.showMessageDialog(null,"未填写完整","提示",JOptionPane.WARNING_MESSAGE);
			}
			else{
				try{
					newdata=new Object[data.length][6];
					for(int i=0;i<data.length;i++){
						newdata[i][0]=data[i][1];
						newdata[i][1]=data[i][2];
						newdata[i][2]=data[i][3];
						newdata[i][3]=data[i][4];
						newdata[i][4]=data[i][0];
						newdata[i][5]=String.valueOf(Integer.parseInt(data[i][0].toString())*Double.parseDouble(data[i][4].toString()));
					}
					ivo=new ImportVO();
					ivo.customer=getcustomer.getText();
					ivo.remakes=getremakes.getText();
					ivo.repertory=Integer.parseInt(getrep.getText());
					ivo.user=getuser.getText();
					
					addImportframe.panel3=new ImportP3(newdata,ivo,addImportframe);
					addImportframe.panel2.setVisible(false);
					addImportframe.panel2.setEnabled(false);
					addImportframe.add(addImportframe.panel3,BorderLayout.CENTER);	
				}catch(NumberFormatException event){
					JOptionPane.showMessageDialog(null,"有非法字符！","提示",JOptionPane.WARNING_MESSAGE);
			}
			
		}
	}
	}
	private class lastListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			addImportframe.panel2.setVisible(false);
			addImportframe.panel2.setEnabled(false);
			addImportframe.panel1.setVisible(true);
			addImportframe.panel1.setEnabled(true);
		}
	}

	}
