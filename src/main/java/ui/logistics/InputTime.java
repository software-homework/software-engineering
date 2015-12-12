package ui.logistics;

import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import vo.LogVO;
import businesslogic.LogBL;
import businesslogic.logistics.CommodityBL;
import businesslogicservice.LogBLService;
import businesslogicservice.logistics.CommodityBLService;

@SuppressWarnings("serial")
public class InputTime extends JDialog {
	final InputTime it;
	public JLabel label1,label2;
	public TextField start,end;
	public JButton btnFind, btnCancel;
	CommodityBLService cs ;
	
	public InputTime(final ShowLogistics ss,final LogisticsMain sm){
		it = this;
		cs = new CommodityBL();
		
		this.setSize(380, 250);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int)(width - this.getWidth()) / 2,(int)(height - this.getHeight()) / 2);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		this.setTitle("库存查看");
		this.setModal(true);
		
		label1 = new JLabel("起始日期：");
		label1.setLocation(30, 30);
		label1.setSize(100, 30);
		this.add(label1);
		
		label2 = new JLabel("结束日期：");
		label2.setLocation(30, 90);
		label2.setSize(100, 30);
		this.add(label2);
		
		start = new TextField("yyyyMMdd");
		start.setLocation(135, 30);
		start.setSize(200, 25);
		this.add(start);
		
		end = new TextField("yyyyMMdd");
		end.setLocation(135, 90);
		end.setSize(200, 25);
		this.add(end);
		
		btnFind = new JButton("查找");
		btnFind.setLocation(50, 150);
		btnFind.setSize(100, 30);
		this.add(btnFind);
		btnFind.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if (e.getButton() == MouseEvent.BUTTON1){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					String time = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
					int i = time.indexOf("/");
					String str = time.substring(i + 1);
					int j = str.indexOf("/");
					int year = Integer.parseInt(time.substring(0, i));
					int month = Integer.parseInt(str.substring(0, j));
					int day = Integer.parseInt(str.substring(j + 1));
					if (start.getText().isEmpty() || end.getText().isEmpty()){
						JOptionPane.showMessageDialog(it, "输入内容不能为空！");
						return;
					}
					if(Integer.parseInt(start.getText().substring(0, 4)) > year){
						JOptionPane.showMessageDialog(it, "开始日期输入年份！");
						return;
					}
					else if(Integer.parseInt(start.getText().substring(0, 4)) == year && Integer.parseInt(start.getText().substring(4, 6)) > month){
						JOptionPane.showMessageDialog(it, "开始日期输入月份错误！");
						return;
					}
					else if(Integer.parseInt(start.getText().substring(0, 4)) == year && Integer.parseInt(start.getText().substring(4, 6)) == month && Integer.parseInt(start.getText().substring(6)) > day){
						JOptionPane.showMessageDialog(it, "开始日期输入天数错误！");
						return;
					}
					
					if(Integer.parseInt(end.getText().substring(0, 4)) > year){
						JOptionPane.showMessageDialog(it, "结束日期输入年份错误！");
						return;
					}
					else if(Integer.parseInt(end.getText().substring(0, 4)) == year && Integer.parseInt(end.getText().substring(4, 6)) > month){
						JOptionPane.showMessageDialog(it, "开始日期输入月份错误！");
						return;
					}
					else if(Integer.parseInt(end.getText().substring(0, 4)) == year && Integer.parseInt(end.getText().substring(4, 6)) == month && Integer.parseInt(end.getText().substring(6)) > day){
						JOptionPane.showMessageDialog(it, "开始日期输入天数错误！");
						return;
					}
					
					if(Integer.parseInt(start.getText().substring(4, 6)) < 1 || Integer.parseInt(start.getText().substring(4, 6)) > 12){
						JOptionPane.showMessageDialog(it, "开始日期输入月份错误！");
						return;
					}
					if(Integer.parseInt(start.getText().substring(6)) < 1 || Integer.parseInt(start.getText().substring(6)) > 31){
						JOptionPane.showMessageDialog(it, "开始日期输入天数错误！");
						return;
					}
					if(Integer.parseInt(end.getText().substring(4, 6)) < 1 || Integer.parseInt(end.getText().substring(4, 6)) > 12){
						JOptionPane.showMessageDialog(it, "结束日期输入月份错误！");
						return;
					}
					if(Integer.parseInt(end.getText().substring(6)) < 1 || Integer.parseInt(end.getText().substring(6)) > 31){
						JOptionPane.showMessageDialog(it, "结束日期输入天数错误！");
						return;
					}
					
					try {
						if(sdf.parse(start.getText()).compareTo(sdf.parse(end.getText())) > 0){
							JOptionPane.showMessageDialog(it, "开始日期在结束日期之后！");
							return;
						}
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					if(cs.merge(start.getText(), end.getText()).size() == 0){
						JOptionPane.showMessageDialog(it, "此时间段内没有出入库记录！");
					}
					else{
						ss.stockList = cs.merge(start.getText(), end.getText());
						JOptionPane.showMessageDialog(it, "查看成功！");
						LogBLService lbs = new LogBL();
						lbs.addLog(new LogVO(sm.uvo.name,"库存查看"));
						ss.updateTable();
						it.dispose();
					}
				}
			}
		});

		btnCancel = new JButton("取消");
		btnCancel.setLocation(230, 150);
		btnCancel.setSize(100, 30);
		this.add(btnCancel);
		btnCancel.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				it.dispose();
			}
		});
				
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				it.dispose();
			}
		});
		
		this.setVisible(true);
	}
	
}