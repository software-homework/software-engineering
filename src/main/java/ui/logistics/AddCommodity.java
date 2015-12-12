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
import vo.logistics.CategoryVO;
import vo.logistics.CommodityVO;
import businesslogic.LogBL;
import businesslogic.logistics.CommodityBL;
import businesslogicservice.LogBLService;
import businesslogicservice.logistics.CommodityBLService;

@SuppressWarnings("serial")
public class AddCommodity extends JDialog{
	public JLabel label1,label2,label3,label4;
	public TextField name, model, priceIn, priceOut;
	public JButton btnAdd, btnCancel;
	CommodityBLService cbs;
	
	public AddCommodity(final LogisticsMain sm){
		final AddCommodity ac = this;
		cbs = new CommodityBL();
		this.setSize(400, 350);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int)(width - this.getWidth()) / 2,(int)(height - this.getHeight()) / 2);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		this.setTitle("添加新快递");
		this.setModal(true);
		
		label1 = new JLabel("快递名称：");
		label1.setLocation(30, 30);
		label1.setSize(100, 30);
		this.add(label1);
		
		label2 = new JLabel("快递型号：");
		label2.setLocation(30, 90);
		label2.setSize(100, 30);
		this.add(label2);
		
		label3 = new JLabel("默认价格：");
		label3.setLocation(30, 150);
		label3.setSize(100, 30);
		this.add(label3);
		
		label4 = new JLabel("默认折扣：");
		label4.setLocation(30, 210);
		label4.setSize(100, 30);
		this.add(label4);
		
		name = new TextField();
		name.setLocation(135, 30);
		name.setSize(200, 25);
		this.add(name);
		
		model = new TextField();
		model.setLocation(135, 90);
		model.setSize(200, 25);
		this.add(model);
		
		priceIn = new TextField();
		priceIn.setLocation(135, 150);
		priceIn.setSize(200, 25);
		this.add(priceIn);
		priceIn.addKeyListener(new KeyAdapter(){  
	           public void keyTyped(KeyEvent e) {  
	               int keyChar = e.getKeyChar();
	               if((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) || (keyChar==KeyEvent.VK_PERIOD)){  
	                      if(keyChar==KeyEvent.VK_PERIOD && priceIn.getText().indexOf(".")!=-1){
	                    	  e.consume();
	                      }
	               }else{  
	                   e.consume(); //屏蔽掉非法输入  
	               }  
	           }  
	    });  
		
		priceOut = new TextField();
		priceOut.setLocation(135, 210);
		priceOut.setSize(200, 25);
		this.add(priceOut);
		priceOut.addKeyListener(new KeyAdapter(){  
	           public void keyTyped(KeyEvent e) {  
	               int keyChar = e.getKeyChar();
	               if((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) || (keyChar==KeyEvent.VK_PERIOD)){  
	                      if(keyChar==KeyEvent.VK_PERIOD && priceOut.getText().indexOf(".")!=-1){
	                    	  e.consume();
	                      }
	               }else{  
	                   e.consume(); //屏蔽掉非法输入  
	               }  
	           } 
	    });  
		
		btnAdd = new JButton("添加");
		btnAdd.setLocation(70, 270);
		btnAdd.setSize(100, 30);
		this.add(btnAdd);
		
		btnCancel = new JButton("取消");
		btnCancel.setLocation(230, 270);
		btnCancel.setSize(100, 30);
		this.add(btnCancel);
		
		btnAdd.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(e.getButton() == MouseEvent.BUTTON1){
					if (name.getText().isEmpty()){
						JOptionPane.showMessageDialog(ac, "信息必须填写完整!");
						return;
					}
					if (model.getText().isEmpty()){
						JOptionPane.showMessageDialog(ac, "信息必须填写完整!");
						return;
					}
					if (priceIn.getText().isEmpty()){
						JOptionPane.showMessageDialog(ac, "信息必须填写完整!");
						return;
					}
					if (priceOut.getText().isEmpty()){
						JOptionPane.showMessageDialog(ac, "信息必须填写完整!");
						return;
					}
					
					if(sm.cv.id == "-1"){
						JOptionPane.showMessageDialog(ac, "错误!");
					}
					else{
						CategoryVO father = sm.cv;
						if (father == null){
							JOptionPane.showMessageDialog(ac, "错误！");
						}
						else{
							CommodityVO cv = new CommodityVO(name.getText(), model.getText(), Double.parseDouble("0" + priceIn.getText()), Double.parseDouble("0" + priceOut.getText()));
							if(cbs.addCommodity(cv, father)){
//								JOptionPane.showMessageDialog(ac, "添加成功！");
								sm.setText("添加成功!");
								LogBLService lbs = new LogBL();
								lbs.addLog(new LogVO(sm.uvo.name,"添加快递"));
								
								sm.update();
								ac.dispose();
							}
							else{
								JOptionPane.showMessageDialog(ac, "添加失败！");
							}
						}
					}
				}
				
			}
		});
		
		btnCancel.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if (e.getButton() == MouseEvent.BUTTON1){
					ac.dispose();
				}
			}
		});
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				ac.dispose();
			}
		});
		
		this.setVisible(true);
	}
	
}
