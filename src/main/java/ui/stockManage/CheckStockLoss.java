package ui.stockManage;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import vo.stockManage.StockLossVO;
import businesslogic.stockManage.StockLossBL;
import businesslogicservice.stockManage.StockLossBLService;

@SuppressWarnings("serial")
public class CheckStockLoss extends JDialog{
	final CheckStockLoss csl;
	public JTable table;
	public JScrollPane scrollTable;
	public ArrayList<StockLossVO> list = new ArrayList<StockLossVO>();
	public StockLossBLService sls;
	
	public CheckStockLoss(final StockMain sm){
		csl = this;
		sls = new StockLossBL();
	    list = sls.getStockLoss();
		
		this.setSize(900, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int)(width - this.getWidth()) / 2,(int)(height - this.getHeight()) / 2);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("库存报溢单列表");
		this.setModal(true);
		
		table = new JTable();
    	scrollTable=new JScrollPane(table);
    	this.add(scrollTable);
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		scrollTable.setVisible(true);
		table.setModel(new StockLossTable(this));
		
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				csl.dispose();
			}
		});
		
		this.setVisible(true);
	}
	
	public int getRowCount(){
		return ((scrollTable.getHeight()) / table.getRowHeight());
	}
	
}
