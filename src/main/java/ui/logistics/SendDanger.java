package ui.logistics;

import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import vo.LogVO;
import vo.logistics.CommodityVO;
import businesslogic.LogBL;
import businesslogic.logistics.CommodityBL;
import businesslogicservice.LogBLService;
import businesslogicservice.logistics.CommodityBLService;

@SuppressWarnings("serial")
public class SendDanger extends JDialog{
	final SendDanger sd;
	public JLabel label;
	public TextField number;
	public JButton btnEdit, btnCancel;
	CommodityBLService cs;
	
	public SendDanger(final LogisticsMain sm){
		sd = this;
		cs = new CommodityBL();
		if (sm.commodity == null){
			return;
		}
		final CommodityVO cv = sm.commodity;
		
		this.setSize(400, 180);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int)(width - this.getWidth()) / 2,(int)(height - this.getHeight()) / 2);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		this.setTitle("库存报警");
		this.setModal(true);
		
		label = new JLabel("警戒值：");
		label.setLocation(30, 30);
		label.setSize(100, 30);
		this.add(label);
		
		number = new TextField();
		number.setLocation(135, 30);
		number.setSize(200, 25);
		this.add(number);
		number.addKeyListener(new KeyAdapter(){  
	           public void keyTyped(KeyEvent e) {  
	               int keyChar = e.getKeyChar();                 
	               if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){  
	                      
	               }else{  
	                   e.consume(); //屏蔽掉非法输入  
	               }  
	           }  
	    });  
		
		btnEdit = new JButton("确认");
		btnEdit.setLocation(70, 90);
		btnEdit.setSize(100, 30);
		this.add(btnEdit);
		
		btnCancel = new JButton("取消");
		btnCancel.setLocation(230, 90);
		btnCancel.setSize(100, 30);
		this.add(btnCancel);
		
		btnEdit.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if (e.getButton() == MouseEvent.BUTTON1){
					
					if(sm.cv == null){
						JOptionPane.showMessageDialog(sd, "错误！");
					}
					else{
						if(cs.sendDanger(cv, Integer.parseInt(number.getText()))){
							JOptionPane.showMessageDialog(sd, "设置成功！");
						    LogBLService lbs = new LogBL();
							lbs.addLog(new LogVO(sm.uvo.name,"设置库存警戒值"));
						    sd.dispose();
						}
						else{
							JOptionPane.showMessageDialog(sd, "设置失败！");
						}
					}
				}
			}
		});
		
		btnCancel.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if (e.getButton() == MouseEvent.BUTTON1){
					sd.dispose();
				}
			}
		});
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				sd.dispose();
			}
		});
		
		this.setVisible(true);
	}
	
}
