package ui.logistics;

import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.*;

import vo.LogVO;
import vo.logistics.CategoryVO;
import businesslogic.LogBL;
import businesslogic.logistics.CategoryBL;
import businesslogicservice.LogBLService;
import businesslogicservice.logistics.CategoryBLService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings({ "serial", "unused" })
public class AddCategory extends JDialog{
	public JLabel label;
	public TextField name;
	public JButton btnAdd, btnCancel;
	CategoryBLService cbs;
	
	public AddCategory(final LogisticsMain sm){
		final AddCategory ac = this;
		cbs = new CategoryBL();
		this.setSize(400, 180);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int)(width - this.getWidth()) / 2,(int)(height - this.getHeight()) / 2);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		this.setTitle("添加新分类");
		this.setModal(true);
		
		final JLabel label = new JLabel("分类名称：");
		label.setLocation(30, 30);
		label.setSize(100, 30);
		this.add(label);
		
		final TextField name = new TextField("");
		name.setLocation(135, 30);
		name.setSize(200, 25);
		this.add(name);
		
		final JButton btnAdd = new JButton("添加");
		btnAdd.setLocation(70, 90);
		btnAdd.setSize(100, 30);
		this.add(btnAdd);
		
		final JButton btnCancel = new JButton("取消");
		btnCancel.setLocation(230, 90);
		btnCancel.setSize(100, 30);
		this.add(btnCancel);
		
		btnAdd.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if (e.getButton() == MouseEvent.BUTTON1){
					if (name.getText().isEmpty()){
						JOptionPane.showMessageDialog(ac, "信息必须填写完整!");
						return;
					}
						CategoryVO cv1 = new CategoryVO(name.getText());
						if(cbs.addCategory(cv1,sm.cv)){
							sm.setText("添加成功!");
							LogBLService lbs = new LogBL();
							lbs.addLog(new LogVO(sm.uvo.name,"添加快递分类"));
							sm.update();
							ac.dispose();
						}
						else{
							JOptionPane.showMessageDialog(ac, "添加分类失败!");
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
