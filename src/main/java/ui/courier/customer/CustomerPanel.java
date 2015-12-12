package ui.courier.customer;
/**
 * 
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import businesslogic.LogBL;
import businesslogic.courier.CustomerBL;
import businesslogicservice.LogBLService;
import businesslogicservice.courier.CustomerBLService;
import ui.TableModel;
import ui.courier.CourierMain;
import vo.LogVO;
import vo.courier.CustomerVO;

@SuppressWarnings("serial")
public class CustomerPanel extends JPanel{
	LogBLService logbl;
	CustomerVO cvo;
	CustomerBLService cbl;
	JTable customertable;
	JScrollPane cscroll;
	String[] columnTitle={"编号","分类","级别","姓名","电话","地址","邮编","电子邮箱","应收额度","应收","应付","默认业务员"};
	String[][] data;
	JButton addC;
	JButton deleteC;
	JButton updateC;
	JButton customerSearch;
	JLabel label;
	TextField cText;
	JPanel panel;
	CourierMain salesmain;
	public CustomerPanel(ArrayList<CustomerVO> list,CourierMain sm){
		logbl=new LogBL();
		salesmain=sm;
		cbl=new CustomerBL();
		addC=new JButton("添加");
		deleteC=new JButton("删除");
		deleteC.setEnabled(false);
		updateC=new JButton("更新");
		updateC.setEnabled(false);
		label=new JLabel("搜索");
		cText=new TextField(20);
		customerSearch=new JButton("…");
		customerSearch.setPreferredSize(new Dimension(20, 20));
		//按钮面板
		panel=new JPanel();
		panel.add(addC);
		panel.add(deleteC);
		panel.add(updateC);
		panel.add(label);
		panel.add(cText);
		panel.add(customerSearch);
		
		this.setLayout(new BorderLayout());
		this.add(panel,BorderLayout.NORTH);
		customertable=new JTable(data,columnTitle);
		cscroll=new JScrollPane(customertable);
		this.add(cscroll, BorderLayout.CENTER);
		setData(list);
		showTable(data);
		//监听
		ActionListener addClistener=new addCListener();
		addC.addActionListener(addClistener);
		ActionListener deleteClistener=new deleteCListener();
		deleteC.addActionListener(deleteClistener);
		ActionListener updateClistener=new updateCListener();
		updateC.addActionListener(updateClistener);
		ActionListener searchlistener=new searchListener();
		customerSearch.addActionListener(searchlistener);
		
	}
	public void showTable(String[][] data){
		this.remove(cscroll);
		customertable=new JTable(new TableModel(data,columnTitle));
		cscroll=new JScrollPane(customertable);
		customertable.setAutoCreateRowSorter(true);
		this.add(cscroll, BorderLayout.CENTER);
		this.revalidate();
		customertable.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				deleteC.setEnabled(true);
				updateC.setEnabled(true);
			}
		});
		this.deleteC.setEnabled(false);
		this.updateC.setEnabled(false);	
	}

	public void setData(ArrayList<CustomerVO> list){
		data=new String[list.size()][12];
		for(int i=0;i<list.size();i++){
			data[i][0]=String.valueOf(list.get(i).id);
			data[i][1]=list.get(i).type;
			data[i][2]=list.get(i).level;
			data[i][3]=list.get(i).name;
			data[i][4]=list.get(i).phone;
			data[i][5]=list.get(i).address;
			data[i][6]=String.valueOf(list.get(i).postcode);
			data[i][7]=list.get(i).email;
			data[i][8]=String.valueOf(list.get(i).limit);
			data[i][9]=String.valueOf(list.get(i).proceed);
			data[i][10]=String.valueOf(list.get(i).pay);
			data[i][11]=list.get(i).user;
		}
		
	}
	class addCListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			new AddCustomerFrame(CustomerPanel.this);
		}
	}
	class deleteCListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(JOptionPane.showConfirmDialog(null, "确定要删除吗？", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION){
				int row=customertable.getSelectedRow();
				cvo=new CustomerVO();
				cvo.id=Integer.parseInt(data[row][0]);
				cvo.type=data[row][1];
				cvo.level=data[row][2];
				cvo.name=data[row][3];
				cvo.phone=data[row][4];
				cvo.address=data[row][5];
				cvo.postcode=Integer.parseInt(data[row][6]);
				cvo.email=data[row][7];
				cvo.limit=Double.parseDouble(data[row][8]);
				cvo.proceed=Double.parseDouble(data[row][9]);
				cvo.pay=Double.parseDouble(data[row][10]);
				cvo.user=data[row][11];
				boolean j=cbl.DeleteC(cvo);
				if(j){
					setData(cbl.ShowC());
					showTable(data);
					salesmain.setStatusBar("删除成功");
					logbl.addLog(new LogVO(salesmain.getUsername(),"删除客户"));
				}
				else{
					JOptionPane.showMessageDialog(null,"删除失败","提示",JOptionPane.WARNING_MESSAGE);
					salesmain.setStatusBar("删除失败");
				}
			}
			
		}
	}
	class updateCListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			int row=customertable.getSelectedRow();
			cvo=new CustomerVO();
			cvo.id=Integer.parseInt(data[row][0]);
			cvo.type=data[row][1];
			cvo.level=data[row][2];
			cvo.name=data[row][3];
			cvo.phone=data[row][4];
			cvo.address=data[row][5];
			cvo.postcode=Integer.parseInt(data[row][6]);
			cvo.email=data[row][7];
			cvo.limit=Double.parseDouble(data[row][8]);
			cvo.proceed=Double.parseDouble(data[row][9]);
			cvo.pay=Double.parseDouble(data[row][10]);
			cvo.user=data[row][11];
			new UpdateCustomerFrame(cvo,CustomerPanel.this);
		}
	}
	class searchListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(!cText.getText().trim().equals("")){
				CustomerVO newCVO=new CustomerVO(cText.getText());
			    setData(cbl.FinC(newCVO));
			    showTable(data);
			}
			else{
				setData(cbl.ShowC());
				showTable(data);		
			}
			salesmain.setStatusBar("搜索结果");
			
		}
	}


}
