package ui.logistics;

import java.awt.TextField;
import java.awt.Toolkit;
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
import businesslogic.LogBL;
import businesslogic.logistics.CategoryBL;
import businesslogicservice.LogBLService;
import businesslogicservice.logistics.CategoryBLService;
@SuppressWarnings("serial")
public class UpdateCategory extends JDialog{
	public JLabel label;
	public TextField name;
	public JButton btnEdit, btnCancel;
	CategoryBLService cs;
	
	public UpdateCategory(final LogisticsMain sm){
		final UpdateCategory uc = this;
		cs = new CategoryBL();
		
		this.setSize(400, 180);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int)(width - this.getWidth()) / 2,(int)(height - this.getHeight()) / 2);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		this.setTitle("更新分类");
		this.setModal(true);
		
		label = new JLabel("分类名称：");
		label.setLocation(30, 30);
		label.setSize(100, 30);
		this.add(label);
		
		name = new TextField(sm.cv.categoryname);
		name.setLocation(135, 30);
		name.setSize(200, 25);
		this.add(name);
		
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
					CategoryVO newCategory = new CategoryVO(name.getText());
					if(cs.updateCategory(sm.cv, newCategory, sm.father)){
//						JOptionPane.showMessageDialog(uc, "修改成功！");
						sm.setText("修改成功！");
						LogBLService lbs = new LogBL();
						lbs.addLog(new LogVO(sm.uvo.name,"修改快递分类"));
						sm.update();
						uc.dispose();
					}
					else{
						JOptionPane.showMessageDialog(uc, "修改失败！");
					}
				}
			}
		});
		
		btnCancel.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if (e.getButton() == MouseEvent.BUTTON1){
					uc.dispose();
				}
			}
		});
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				uc.dispose();
			}
		});
		
		this.setVisible(true);
	}
	
}
