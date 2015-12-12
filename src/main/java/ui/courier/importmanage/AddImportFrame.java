package ui.courier.importmanage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import businesslogic.courier.ImportBL;
import businesslogicservice.courier.ImportBLService;
import vo.courier.ImportVO;

@SuppressWarnings("serial")
public class AddImportFrame extends JDialog{
	ImportPanel ip;
	ImportBLService importbl;
	JPanel panel1;
	JPanel panel2;
	JPanel panel3;
	JPanel title;
	JLabel id;
	private JLabel getid;
	String username;
	String userlevel;

	public AddImportFrame(String ID,ImportPanel importpanel,ImportVO ivo){
		ip=importpanel;
		importbl=new ImportBL();
		username=ip.salesmain.getUsername();
		userlevel=ip.salesmain.getUserlevel();
		//界面定义
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int frameHeight = screenSize.height * 2 / 3;
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
		if(ID.substring(0, 3).equals("JHD")){
			panel1=new ImportP1(AddImportFrame.this);
		}
		else{
			panel1=new ImportP1(AddImportFrame.this,ivo);
		}
		
		this.add(panel1,BorderLayout.CENTER);
		this.add(title,BorderLayout.NORTH);
		
		this.setVisible(true);
	}
	public String getID(){
		return getid.getText();
	}
	

}
