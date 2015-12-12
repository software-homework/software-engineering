package ui.courier.customer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import vo.LogVO;
import vo.courier.CustomerVO;
import businesslogic.LogBL;
import businesslogic.courier.CustomerBL;
import businesslogic.courier.GetUsername;
import businesslogic.user.UserBL;
import businesslogicservice.LogBLService;
import businesslogicservice.courier.CustomerBLService;

@SuppressWarnings("serial")
public class UpdateCustomerFrame extends JDialog{
	LogBLService logbl;
	CustomerBLService customerbl;
	GetUsername getusername;
	CustomerVO newCVO;
	JPanel panel;
	JPanel titlepanel;
	JPanel checkpanel;
	String[] levelList={"五级","一级普通用户","五级VIP客户"};
	String[] userList;
	JLabel name;
	JLabel getname;
	JLabel id;
	JLabel getid;
	JLabel level;
	JLabel type;
	JLabel phone;
	JLabel address;
	JLabel postcode;
	JLabel email;
	JLabel limit;
	JLabel user;
	JLabel gettype;
	
	String username;
	String userlevel;
	
	JComboBox<String> userbox;
	JComboBox<String> levelbox;
	TextField getphone;
	TextField getaddress;
	TextField getpostcode;
	TextField getemail;
	TextField getlimit;
	
	JButton comfirm;
	JButton cancel;
	
	CustomerPanel customerPanel;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public UpdateCustomerFrame(CustomerVO cvo,CustomerPanel cp){
		logbl=new LogBL();
		customerPanel =cp;
		username=cp.salesmain.getUsername();
		userlevel=cp.salesmain.getUserlevel();
		getusername=new UserBL();
		//界面定义
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int frameHeight = screenSize.height * 2 / 3;
		int frameWidth = frameHeight * 4 / 7;
		this.setBounds((screenSize.width - frameWidth) / 2, (screenSize.height - frameHeight) / 2, frameWidth, frameHeight);
		this.setResizable(true);
		this.setResizable(false);
		this.setModal(true);
	    this.setTitle("更改客户");
	    
	    customerbl=new CustomerBL();
	    newCVO=cvo;
		name=new JLabel("姓名：", JLabel.CENTER);
		id=new JLabel("编号：", JLabel.CENTER);
		level=new JLabel("级别：", JLabel.CENTER);
		type=new JLabel("类型：", JLabel.CENTER);
		phone=new JLabel("电话：", JLabel.CENTER);
		address=new JLabel("地址：", JLabel.CENTER);
		postcode=new JLabel("邮编：", JLabel.CENTER);
		email=new JLabel("邮箱：", JLabel.CENTER);
		limit=new JLabel("应收额度：", JLabel.CENTER);
		user=new JLabel("默认操作员：", JLabel.CENTER);
		getid=new JLabel(String.valueOf(cvo.id),JLabel.CENTER);
		getname=new JLabel(cvo.name, JLabel.LEFT);
		getphone=new TextField(cvo.phone,20);
		getaddress=new TextField(cvo.address,20);
		getpostcode=new TextField(String.valueOf(cvo.postcode),20);
		getpostcode.addKeyListener(new KeyAdapter(){  
	           public void keyTyped(KeyEvent e) {  
	               int keyChar = e.getKeyChar();                 
	               if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){  
	                      
	               }else{  
	                   e.consume(); //屏蔽掉非法输入  
	               }  
	           }  
	       });  
		getemail=new TextField(cvo.email,20);
		getlimit=new TextField(String.valueOf(cvo.limit),20);
		if(userlevel.equals("普通职员")){
			getlimit.setEnabled(false);
		}
		getlimit.addKeyListener(new KeyAdapter(){  
	           public void keyTyped(KeyEvent e) {  
	               int keyChar = e.getKeyChar();
	               if((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) || (keyChar==KeyEvent.VK_PERIOD)){  
	                      if(keyChar==KeyEvent.VK_PERIOD&&getlimit.getText().indexOf(".")!=-1){
	                    	  e.consume();
	                      }
	               }else{  
	                   e.consume(); //屏蔽掉非法输入  
	               }  
	           }  
	       });  
		userList=getusername.getUsername("快递接销售人员");
		userbox=new JComboBox(userList);
		levelbox=new JComboBox(levelList);
		gettype=new JLabel(cvo.type);
		comfirm=new JButton("确定");
		cancel=new JButton("取消");
		
		//编号面板
		titlepanel=new JPanel();
		titlepanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		titlepanel.add(id);
		titlepanel.add(getid);
		//输入面板
		panel=new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
		panel.setLayout(new GridLayout(9,2,0,20));
		panel.add(name);
		panel.add(getname);
		panel.add(type);
		panel.add(gettype);
		panel.add(level);
		panel.add(levelbox);
		panel.add(phone);
		panel.add(getphone);
		panel.add(postcode);
		panel.add(getpostcode);
		panel.add(email);
		panel.add(getemail);
		panel.add(address);
		panel.add(getaddress);
	    panel.add(limit);
		panel.add(getlimit);
		panel.add(user);
		panel.add(userbox);
		//确认面板
		checkpanel=new JPanel();
		checkpanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		checkpanel.add(comfirm);
		checkpanel.add(cancel);
		
		//监听
		ActionListener comfirmlistener=new comfirmListener();
		comfirm.addActionListener(comfirmlistener);
		ActionListener cancellistener=new cancelListener();
		cancel.addActionListener(cancellistener);

				
		this.add(panel,BorderLayout.CENTER);
		this.add(titlepanel,BorderLayout.NORTH);
		this.add(checkpanel,BorderLayout.SOUTH);
		this.setVisible(true);
	}
	
	class comfirmListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(getaddress.getText().isEmpty()||getemail.getText().isEmpty()||getlimit.getText().isEmpty()||getphone.getText().isEmpty()){
				JOptionPane.showMessageDialog(null,"未填写完整","提示",JOptionPane.WARNING_MESSAGE);
			}
			else{
				newCVO.address=getaddress.getText();
				newCVO.email=getemail.getText();
				newCVO.level=(String)levelbox.getSelectedItem();
				if(getlimit.getText().indexOf(".")==-1){
					getlimit.setText(getlimit.getText()+".");
				}
				newCVO.limit=Double.parseDouble("0"+getlimit.getText()+"0");
				newCVO.phone=getphone.getText();
				newCVO.postcode=Integer.parseInt(getpostcode.getText());
				newCVO.type=gettype.getText();
				newCVO.user=(String)userbox.getSelectedItem();
				customerbl.UpdateC(newCVO);
				customerPanel.setData(customerPanel.cbl.ShowC());
				customerPanel.showTable(customerPanel.data);
				customerPanel.salesmain.setStatusBar("更新成功");
				logbl.addLog(new LogVO(username,"更新客户"));
				dispose();
			}
		}
	}
	class cancelListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(JOptionPane.showConfirmDialog(null, "确定要取消吗？", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION){
				dispose();
			}
			
		}
	}

}
