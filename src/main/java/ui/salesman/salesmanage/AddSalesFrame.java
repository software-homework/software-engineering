package ui.salesman.salesmanage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vo.salesman.SalesVO;
import businesslogic.salesman.SalesBL;
import businesslogicservice.salesman.SalesBLService;

@SuppressWarnings("serial")
public class AddSalesFrame extends JDialog{
	SalesPanel sp;
	SalesBLService salesbl;
	JPanel panel1;
	JPanel panel2;
	JPanel panel3;
	JPanel title;
	JLabel id;
	private JLabel getid;
	String username;
	String userlevel;

	public AddSalesFrame(String ID,SalesPanel salespanel,SalesVO svo){
		sp=salespanel;
		salesbl=new SalesBL();
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
			panel1=new SalesP1(AddSalesFrame.this);
		}
		else{
			panel1=new SalesP1(AddSalesFrame.this,svo);
		}
		
		this.add(panel1,BorderLayout.CENTER);
		this.add(title,BorderLayout.NORTH);
		
		this.setVisible(true);
	}
	
	public String getId(){
		return getid.getText();
	}

}
