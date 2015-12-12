package ui.stockManage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import businesslogic.stockManage.CommodityBL;
import businesslogicservice.stockManage.CommodityBLService;
import ui.TableModel;
import vo.stockManage.CommodityVO;

@SuppressWarnings("serial")
public class SendPresent extends JDialog {
	final SendPresent sp;
	public JButton btnNext,btnCancel;
	CommodityBLService cbs;
	ArrayList<CommodityVO> commodityList = new ArrayList<CommodityVO>();
	Object[][] data;
	Object[][] newData;
	JTable table;
	JPanel panel;
	
	public SendPresent(final StockMain sm){
		sp = this;
		cbs = new CommodityBL();
		
		commodityList = cbs.getCommodity();
		setData(commodityList);
		btnNext = new JButton("下一步");
		btnNext.setEnabled(false);
		btnCancel = new JButton("取消");
		String[] columnTitle = {"","编号","名称","型号","库存数量","默认进价","默认售价","最近进价","最近售价"};
		table = new JTable(new TableModel(data,columnTitle));
		table.getColumnModel().getColumn(0).setMaxWidth(30);
		panel=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel.add(btnNext);
		panel.add(btnCancel);
		this.add(new JScrollPane(table),BorderLayout.CENTER);
		this.add(panel,BorderLayout.SOUTH);
		table.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				boolean b = false;
				for(int i = 0;i < data.length;i ++){
					if(data[i][0] == Boolean.TRUE){
						btnNext.setEnabled(true);
						b = true;
						break;
					}
				}
				if(b == false){
					btnNext.setEnabled(false);
				}
			}
		});
		
		this.setSize(800, 300);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int)(width - this.getWidth()) / 2,(int)(height - this.getHeight()) / 2);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("库存赠送");
		this.setModal(true);
		
		btnNext.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if (e.getButton() == MouseEvent.BUTTON1){
					int count=0;
					for(int i = 0; i < data.length; i++){
						if(data[i][0] == Boolean.TRUE){
							count ++;
						}
					}
					newData=new Object[count][9];
					count=0;
					for(int i = 0; i < data.length; i ++){
						if(data[i][0] == Boolean.TRUE){
							newData[count][0] = "1";
							newData[count][1] = data[i][1];
							newData[count][2] = data[i][2];
							newData[count][3] = data[i][3];
							newData[count][4] = data[i][4];
							newData[count][5] = data[i][5];
							newData[count][6] = data[i][6];
							newData[count][7] = data[i][7];
							newData[count][8] = data[i][8];
							count++;
						}
					}
					sp.dispose();
				    new SendPresentNum(SendPresent.this,sm);
				}
			}
		});
		
		btnCancel.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if (e.getButton() == MouseEvent.BUTTON1){
					sp.dispose();
				}
			}
		});
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				sp.dispose();
			}
		});
		
		this.setVisible(true);
	}
	
	public void setData(ArrayList<CommodityVO> list){
		data=new Object[list.size()][9];
		for(int i = 0;i < list.size();i ++){
			data[i][0] = Boolean.FALSE;
			data[i][1] = list.get(i).id;
			data[i][2] = list.get(i).commodityname;
			data[i][3] = list.get(i).model;
			data[i][4] = String.valueOf(list.get(i).number);
			data[i][5] = String.valueOf(list.get(i).priceIn);
			data[i][6] = String.valueOf(list.get(i).priceOut);
			data[i][7] = String.valueOf(list.get(i).lastPriceIn);
			data[i][8] = String.valueOf(list.get(i).lastPriceOut);
		}
	}
}
