package ui.logistics;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import businesslogic.logistics.PresentBL;
import businesslogicservice.logistics.PresentBLService;
import vo.logistics.PresentVO;

@SuppressWarnings("serial")
public class CheckPresent extends JDialog{
	final CheckPresent cp;
	public JTable table;
	public JScrollPane scrollTable;
	public ArrayList<PresentVO> list = new ArrayList<PresentVO>();
	public PresentBLService pbs;
	
	public CheckPresent(final LogisticsMain sm){
		cp = this;
		pbs = new PresentBL();
		list = pbs.getPresent();
		
		this.setSize(900, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int)(width - this.getWidth()) / 2,(int)(height - this.getHeight()) / 2);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("物流报溢单列表");
		this.setModal(true);
		
		table = new JTable();
    	scrollTable=new JScrollPane(table);
    	this.add(scrollTable);
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		scrollTable.setVisible(true);
		table.setModel(new PresentTable(this));
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				cp.dispose();
			}
		});
		
		this.setVisible(true);
	}
	
	public int getRowCount(){
		return ((scrollTable.getHeight()) / table.getRowHeight());
	}
}
