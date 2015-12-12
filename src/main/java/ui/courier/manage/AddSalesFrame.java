package ui.courier.manage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import businesslogic.courier.CourierBL;
import businesslogicservice.courier.CourierBLService;
import vo.courier.CourierVO;

@SuppressWarnings("serial")
public class AddSalesFrame extends JDialog{
	CourierPanel sp;
	CourierBLService salesbl;
	JPanel panel1;
	JPanel panel2;
	JPanel panel3;
	JPanel title;
	JLabel id;
	private JLabel getid;
	String username;
	String userlevel;

	public AddSalesFrame(String ID,CourierPanel salespanel,CourierVO svo){
		sp=salespanel;
		salesbl=new CourierBL();
		username=salespanel.salesmain.getUsername();
		userlevel=salespanel.salesmain.getUserlevel();
		//界面定义
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int frameHeight = screenSize.height * 3 / 4;
		int frameWidth = frameHeight * 6 / 7;
		this.setBounds((screenSize.width - frameWidth) / 2, (screenSize.height - frameHeight) / 2, frameWidth, frameHeight);
		this.setResizable(false);
		this.setModal(true);
		this.setTitle("添加单据");
		
		id=new JLabel("编号:");
		getid=new JLabel(ID);
		title=new JPanel(new FlowLayout(FlowLayout.LEFT));
		title.add(id);
		title.add(getid);
		
		if(ID.substring(0, 3).equals("XSD")){
			panel1=new CourierP1(AddSalesFrame.this);
		}
		else{
			panel1=new CourierP1(AddSalesFrame.this,svo);
		}
		
		this.add(panel1,BorderLayout.CENTER);
		this.add(title,BorderLayout.NORTH);
		
		this.setVisible(true);
	}
	
	public String getId(){
		return getid.getText();
	}

}
