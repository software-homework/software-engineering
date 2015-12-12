package ui.stockManage;

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

import businesslogic.LogBL;
import businesslogic.stockManage.CommodityBL;
import businesslogicservice.LogBLService;
import businesslogicservice.stockManage.CommodityBLService;
import vo.LogVO;
import vo.stockManage.CategoryVO;
import vo.stockManage.CommodityVO;

@SuppressWarnings("serial")
public class UpdateCommodity extends JDialog{
	public JLabel label1,label2,label3,label4;
	public TextField name, model, priceIn, priceOut;
	public JButton btnAdd, btnCancel;
	CommodityBLService cs;
	
	public UpdateCommodity(final StockMain sm){
		final UpdateCommodity uc = this;
		cs = new CommodityBL();
		if (sm.commodity == null){
			return;
		}
		final CommodityVO cv = sm.commodity;
		
		this.setSize(400, 350);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int)(width - this.getWidth()) / 2,(int)(height - this.getHeight()) / 2);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		this.setTitle("更新商品");
		this.setModal(true);
		
		label1 = new JLabel("商品名称：");
		label1.setLocation(30, 30);
		label1.setSize(100, 30);
		this.add(label1);
		
		label2 = new JLabel("商品型号：");
		label2.setLocation(30, 90);
		label2.setSize(100, 30);
		this.add(label2);
		
		label3 = new JLabel("默认进价：");
		label3.setLocation(30, 150);
		label3.setSize(100, 30);
		this.add(label3);
		
		label4 = new JLabel("默认售价：");
		label4.setLocation(30, 210);
		label4.setSize(100, 30);
		this.add(label4);
		
		name = new TextField(cv.commodityname);
		name.setLocation(135, 30);
		name.setSize(200, 25);
		this.add(name);
		
		model = new TextField(cv.model);
		model.setLocation(135, 90);
		model.setSize(200, 25);
		this.add(model);
		
		priceIn = new TextField("" + cv.priceIn);
		priceIn.setLocation(135, 150);
		priceIn.setSize(200, 25);
		this.add(priceIn);
		
		priceOut = new TextField("" + cv.priceOut);
		priceOut.setLocation(135, 210);
		priceOut.setSize(200, 25);
		this.add(priceOut);
		
		btnAdd = new JButton("确认");
		btnAdd.setLocation(70, 270);
		btnAdd.setSize(100, 30);
		this.add(btnAdd);
		
		btnCancel = new JButton("取消");
		btnCancel.setLocation(230, 270);
		btnCancel.setSize(100, 30);
		this.add(btnCancel);
		
		btnAdd.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if (e.getButton() == MouseEvent.BUTTON1){
					if(sm.cv == null){
						JOptionPane.showMessageDialog(uc, "错误！");
					}
					else{
						CategoryVO father = sm.cv;
						if(father == null){
							JOptionPane.showMessageDialog(uc, "错误！");
						}
						else{
							CommodityVO newCommodity = new CommodityVO(name.getText(), model.getText(), Double.parseDouble(priceIn.getText()), Double.parseDouble(priceOut.getText()));
							newCommodity.isDelete = cv.isDelete;
							newCommodity.lastPriceIn = cv.lastPriceIn;
							newCommodity.lastPriceOut = cv.lastPriceOut;
							newCommodity.number = cv.number;
							newCommodity.dangerNumber = cv.dangerNumber;
							if(cs.updateCommodity(cv, newCommodity, sm.cv)){
//								JOptionPane.showMessageDialog(uc, "修改成功！");
								sm.setText("修改成功！");
								LogBLService lbs = new LogBL();
								lbs.addLog(new LogVO(sm.uvo.name,"修改商品"));
								sm.update();
								uc.dispose();
							}
							else{
								JOptionPane.showMessageDialog(uc, "修改失败！");
							}
						}
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
