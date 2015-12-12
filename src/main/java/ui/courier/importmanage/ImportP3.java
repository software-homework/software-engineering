package ui.courier.importmanage;

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

import businesslogic.LogBL;
import businesslogic.courier.ImportBL;
import businesslogicservice.LogBLService;
import businesslogicservice.courier.ImportBLService;
import ui.TableModel;
import vo.LogVO;
import vo.courier.ImportVO;
import vo.logistics.CommodityVO;

@SuppressWarnings("serial")
public class ImportP3 extends JPanel{
	LogBLService logbl;
	ImportBLService ibl;
	ImportVO ivo;
	AddImportFrame addImportframe;
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
	JLabel total;
	JLabel getuser;
	JLabel getrep;
	JLabel getremakes;
	JLabel getcustomer;
	JLabel gettotal;
	JTable commoditytable;
	Object[][] data;
	double t;
	ArrayList<CommodityVO> clist;
	CommodityVO cvo;
	
	public ImportP3(Object[][] cdata,ImportVO importvo,AddImportFrame aif){
		logbl=new LogBL();
		ibl=new ImportBL();
		ivo=importvo;
		addImportframe=aif;
		data=cdata;
		//头面板
		user=new JLabel("操作员:");
		getuser=new JLabel(aif.username);
		title=new JPanel(new FlowLayout(FlowLayout.LEFT));
		title.add(user);
		title.add(getuser);
		
		//显示面板
		customer=new JLabel("供应商:", JLabel.RIGHT);
		repertory=new JLabel("仓库:", JLabel.RIGHT);
		remakes=new JLabel("备注:", JLabel.RIGHT);
		total=new JLabel("总金额:",JLabel.RIGHT);
		getcustomer=new JLabel(ivo.customer, JLabel.CENTER);
		getrep=new JLabel(String.valueOf(ivo.repertory), JLabel.CENTER);
		getremakes=new JLabel(ivo.remakes, JLabel.CENTER);
		t=0;
		for(int i=0;i<data.length;i++){
			t=t+Double.parseDouble(data[i][5].toString());
		}
		gettotal=new JLabel(String.valueOf(t),JLabel.CENTER);
		
		String[] columnTitle={"编号","名称","型号","进价","数量","金额"};
		commoditytable=new JTable(new TableModel(data,columnTitle){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		
		show=new JPanel(new GridLayout(4,2,0,35));
		show.add(customer);
		show.add(getcustomer);
		show.add(repertory);
		show.add(getrep);
		show.add(total);
		show.add(gettotal);
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
			ivo.total=Double.parseDouble(gettotal.getText());
			ivo.id=addImportframe.getID();
			clist=new ArrayList<CommodityVO>();
			for(int i=0;i<data.length;i++){
				 cvo=new CommodityVO();
				 cvo.commodityname=(String)data[i][1];
			     cvo.id=(String)data[i][0];
			     cvo.model=(String)data[i][2];
			     cvo.number=Integer.parseInt(data[i][4].toString());
			     cvo.priceIn=Double.parseDouble(data[i][3].toString());
			     cvo.lastPriceIn=Double.parseDouble(data[i][5].toString());
			     cvo.lastPriceOut=0;
			     cvo.priceOut=0;
			     clist.add(cvo);
			}
			ivo.importList=clist;
			ibl.CheckI(ivo);
			if(ivo.id.substring(0, 3).equals("JHD")){
				logbl.addLog(new LogVO(addImportframe.username,"添加进货单"));
			}else{
				logbl.addLog(new LogVO(addImportframe.username,"添加进货退货单"));
			}
			addImportframe.ip.setData();
			addImportframe.ip.showTable(addImportframe.ip.data);
			addImportframe.ip.salesmain.setStatusBar("添加成功");
			addImportframe.dispose();
		}
	}
	private class lastListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			addImportframe.panel3.setVisible(false);
			addImportframe.panel3.setEnabled(false);
			addImportframe.panel2.setVisible(true);
			addImportframe.panel2.setEnabled(true);
		}
	}
	private class cancelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(JOptionPane.showConfirmDialog(null, "确定要取消添加吗？", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION){
				addImportframe.dispose();
			}
		}
	}

}
