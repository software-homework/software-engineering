package ui.logistics;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import businesslogic.LogBL;
import businesslogic.logistics.MessageBL;
import businesslogicservice.LogBLService;
import businesslogicservice.logistics.MessageBLService;
import ui.TableModel;
import vo.LogVO;
import vo.logistics.MessageVO;

@SuppressWarnings("serial")
public class MessageMain extends JDialog{
	public ArrayList<MessageVO> List = new ArrayList<MessageVO>();
	MessageBLService sdb;
    final MessageMain sdm;
    public JButton btnEdit,btnCancel;
    Object[][] data;
    JTable table;
    JPanel panel;
   
	public MessageMain(final LogisticsMain sm){
		sdm = this;
		sdb = new MessageBL();
		
		List = sdb.showUnRead();
		setData(List);
		btnEdit = new JButton("确认");
		btnEdit.setEnabled(false);
		btnCancel = new JButton("取消");
		String[] columnTitle = {"","快递类型","快递名称","快递型号","快递数量","客户","下单时间"};
		table = new JTable(new TableModel(data,columnTitle));
		table.getColumnModel().getColumn(0).setMaxWidth(30);
		panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel.add(btnEdit);
		panel.add(btnCancel);
		this.add(new JScrollPane(table),BorderLayout.CENTER);
		this.add(panel,BorderLayout.SOUTH);
		table.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				boolean b = false;
				for(int i = 0;i < data.length;i ++){
					if(data[i][0] == Boolean.TRUE){
						btnEdit.setEnabled(true);
						b = true;
						break;
					}
				}
				if(b == false){
					btnEdit.setEnabled(false);
				}
			}
		});
		
		this.setSize(800, 300);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int)(width - this.getWidth()) / 2,(int)(height - this.getHeight()) / 2);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("消息列表");
		this.setModal(true);
		
		btnEdit.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if (e.getButton() == MouseEvent.BUTTON1){
					for(int i = 0;i < data.length;i ++){
						if(data[i][0] == Boolean.TRUE){
							MessageVO sdv = new MessageVO();
							sdv = List.get(i);
							sdv.isRead = true;
							sdb.update(sdv);
						}
					}
					JOptionPane.showMessageDialog(sdm, "检查消息成功!");
					LogBLService lbs = new LogBL();
					lbs.addLog(new LogVO(sm.uvo.name,"查看消息"));
					sdm.dispose();
				}
			}
		});
		
		btnCancel.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if (e.getButton() == MouseEvent.BUTTON1){
					sdm.dispose();
				}
			}
		});
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				sdm.dispose();
			}
		});
		
		this.setVisible(true);
	}
	
	public void setData(ArrayList<MessageVO> list){
		data=new Object[list.size()][7];
		for(int i = 0;i < list.size();i ++){
			data[i][0] = Boolean.FALSE;
			data[i][1] = list.get(i).type;
			data[i][2] = list.get(i).name;
			data[i][3] = list.get(i).model;
			data[i][4] = String.valueOf(list.get(i).number);
			data[i][5] = list.get(i).customer;
			data[i][6] = list.get(i).time;
		}
	}
}
