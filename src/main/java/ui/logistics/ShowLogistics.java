package ui.logistics;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import vo.logistics.LogisticsVO;

@SuppressWarnings("serial")
public class ShowLogistics extends JDialog {
	final ShowLogistics ss;
	public JMenuBar mb;
	public JMenu menu;
	public JTable table;
	public JScrollPane scrollTable;
	public ArrayList<LogisticsVO> stockList = new ArrayList<LogisticsVO>();
	
	public ShowLogistics(final LogisticsMain sm){
		ss = this;
		
		this.setSize(900, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int)(width - this.getWidth()) / 2,(int)(height - this.getHeight()) / 2);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("物流查看列表");
		this.setModal(true);
		
		mb = new JMenuBar();
		menu = new JMenu("设置时间段");
		menu.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if (e.getButton() == MouseEvent.BUTTON1){
					new InputTime(ShowLogistics.this,sm);
				}
			}
		});
		mb.add(menu);
		this.setJMenuBar(mb);
		
		table = new JTable();
    	scrollTable=new JScrollPane(table);
    	this.add(scrollTable);
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		scrollTable.setVisible(true);
		table.setModel(new ShowLogisticsTable(this));
		
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				ss.dispose();
			}
		});
		
		this.setVisible(true);
	}
	
	public int getRowCount(){
		return ((scrollTable.getHeight()) / table.getRowHeight());
	}
	
	public void updateTable(){
		table.setModel(new ShowLogisticsTable(this));
//		commodityList.clear();
		scrollTable.revalidate();
	}

}
