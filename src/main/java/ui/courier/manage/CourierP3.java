package ui.courier.manage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ui.TableModel;
import vo.LogVO;
import vo.courier.CourierVO;
import vo.logistics.CommodityVO;
import businesslogic.LogBL;
import businesslogic.courier.CourierBL;
import businesslogicservice.LogBLService;
import businesslogicservice.courier.CourierBLService;

@SuppressWarnings("serial")
public class CourierP3 extends JPanel{
	LogBLService logbl;
	CourierBLService sbl;
	CourierVO svo;
	AddSalesFrame addSalesframe;
	JPanel title;
	JPanel showp;
	JPanel check;
	JPanel show;
	JButton comfirm;
	JButton cancel;
	JButton last;
	JLabel customer;
	JLabel repertory;
	JLabel user;
	JLabel remakes;
	JLabel pretotal;
	JLabel posttotal;
	JLabel discount;
	JLabel voucher;
	JLabel getdiscount;
	JLabel getvoucher;
	JLabel getuser;
	JLabel getrep;
	JLabel getremakes;
	JLabel getcustomer;
	JLabel getpretotal;
	JLabel getposttotal;
	JTable commoditytable;
	Object[][] data;
	double t;
	ArrayList<CommodityVO> clist;
	CommodityVO cvo;
	
	public CourierP3(Object[][] cdata,CourierVO salesvo,AddSalesFrame asf){
		logbl=new LogBL();
		sbl=new CourierBL();
		svo=salesvo;
		addSalesframe=asf;
		data=cdata;
		//头面板
		user=new JLabel("操作员:");
		getuser=new JLabel(asf.username);
		title=new JPanel(new FlowLayout(FlowLayout.LEFT));
		title.add(user);
		title.add(getuser);
		
		//显示面板
		customer=new JLabel("供应商:", JLabel.RIGHT);
		repertory=new JLabel("仓库:", JLabel.RIGHT);
		remakes=new JLabel("备注:", JLabel.RIGHT);
		pretotal=new JLabel("折让前总金额:",JLabel.RIGHT);
		discount=new JLabel("折让金额:",JLabel.RIGHT);
		voucher=new JLabel("代金券:",JLabel.RIGHT);
		posttotal=new JLabel("折让后总金额:",JLabel.RIGHT);
		getdiscount=new JLabel(String.valueOf(svo.discount),JLabel.CENTER);
		getvoucher=new JLabel(String.valueOf(svo.Voucher),JLabel.CENTER);
		getcustomer=new JLabel(svo.customer, JLabel.CENTER);
		getrep=new JLabel(String.valueOf(svo.repertory), JLabel.CENTER);
		getremakes=new JLabel(svo.remakes, JLabel.CENTER);
		t=0;
		for(int i=0;i<data.length;i++){
			t=t+Double.parseDouble(data[i][5].toString());
		}
		getpretotal=new JLabel(String.valueOf(t),JLabel.CENTER);
		getposttotal=new JLabel(String.valueOf(t-svo.discount-svo.Voucher),JLabel.CENTER);
		String[] columnTitle={"编号","名称","型号","折扣","数量","金额"};
		commoditytable=new JTable(new TableModel(data,columnTitle){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		
		show=new JPanel(new GridLayout(7,2,0,5));
		show.add(customer);
		show.add(getcustomer);
		show.add(repertory);
		show.add(getrep);
		show.add(pretotal);
		show.add(getpretotal);
		show.add(discount);
		show.add(getdiscount);
		show.add(voucher);
		show.add(getvoucher);
		show.add(posttotal);
		show.add(getposttotal);
		show.add(remakes);
		show.add(getremakes);
		
		showp=new JPanel(new GridLayout(2,1));
		showp.add(new JScrollPane(commoditytable));
		showp.add(show);
		
		//确认面板
		comfirm=new JButton("确认");
		cancel=new JButton("取消");
		last=new JButton("上一步");
		check=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		check.add(last);
		check.add(comfirm);
		check.add(cancel);
		
		//添加组件
		this.setLayout(new BorderLayout());
		this.add(title, BorderLayout.NORTH);
		this.add(showp, BorderLayout.CENTER);
		this.add(check, BorderLayout.SOUTH);
		
		//监听
		ActionListener comfirmlistener=new comfirmListener();
		comfirm.addActionListener(comfirmlistener);
		ActionListener cancellistener=new cancelListener();
		cancel.addActionListener(cancellistener);
		ActionListener lastlistener=new lastListener();
		last.addActionListener(lastlistener);
	}
	
	private class comfirmListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(Double.parseDouble(getposttotal.getText())<0){
				JOptionPane.showMessageDialog(null,"折让超过原金额","提示",JOptionPane.WARNING_MESSAGE);
			}
			else{
				svo.pretotal=Double.parseDouble(getpretotal.getText());
				svo.posttotal=Double.parseDouble(getposttotal.getText());
				svo.id=addSalesframe.getId();
				clist=new ArrayList<CommodityVO>();
				for(int i=0;i<data.length;i++){
					 cvo=new CommodityVO();
					 cvo.commodityname=(String)data[i][1];
				     cvo.id=(String)data[i][0];
				     cvo.model=(String)data[i][2];
				     cvo.number=Integer.parseInt(data[i][4].toString());
				     cvo.priceIn=0;
				     cvo.lastPriceIn=0;
				     cvo.lastPriceOut=Double.parseDouble(data[i][5].toString());
				     cvo.priceOut=Double.parseDouble(data[i][3].toString());
				     clist.add(cvo);
				}
				svo.salesList=clist;
				sbl.CheckS(svo);
				addSalesframe.sp.setData();
				addSalesframe.sp.showTable(addSalesframe.sp.data);
				addSalesframe.sp.salesmain.setStatusBar("添加成功");
				if(svo.id.substring(0, 3).equals("XSD")){
					logbl.addLog(new LogVO(addSalesframe.username,"添加送货"));
				}else{
					logbl.addLog(new LogVO(addSalesframe.username,"添加销售退货单"));
				}
				addSalesframe.dispose();
			}
		}
	}
	private class lastListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			addSalesframe.panel3.setVisible(false);
			addSalesframe.panel3.setEnabled(false);
			addSalesframe.panel2.setVisible(true);
			addSalesframe.panel2.setEnabled(true);
		}
	}
	private class cancelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(JOptionPane.showConfirmDialog(null, "确定要取消添加吗？", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION){
				addSalesframe.dispose();
			}
			
		}
	}


}
