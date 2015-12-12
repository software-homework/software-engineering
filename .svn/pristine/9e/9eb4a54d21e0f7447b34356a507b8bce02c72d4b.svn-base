package ui.finance.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import vo.stockManage.PresentVO;

@SuppressWarnings("serial")
public class ShowPresent extends JDialog{
	FinanceFrame jFrame;
	PresentVO presentVO;
	int frameHeight = 520;
	int frameWidth = 400;
	
	JTable jt1;
	JScrollPane jsp1;
	JPanel jp;
	JLabel top;
		
	public ShowPresent(FinanceFrame jFrame, String title, boolean modal, PresentVO presentVO) {
		super(jFrame, title, modal);
		//获取
		this.jFrame = jFrame;
		this.presentVO = presentVO;
		//设置组件
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Toolkit kit = Toolkit.getDefaultToolkit();    	// 定义工具包
	    Dimension screenSize = kit.getScreenSize();   	// 获取屏幕的尺寸			
	    this.setBounds((screenSize.width - frameWidth) / 2, (screenSize.height - frameHeight) / 3, frameWidth, frameHeight);
		//
		ArrayList<String> commodityList = new ArrayList<String>(presentVO.presentList);
		String[][] commodity = new String[commodityList.size()][3];
		for(int i = 0; i < commodityList.size(); i++){
			commodity[i][0] = commodityList.get(i).split(";")[0];
			commodity[i][1] = commodityList.get(i).split(";")[1];
			commodity[i][2] = commodityList.get(i).split(";")[2];
		}
		jt1 = new JTable(commodity, new String[]{"商品名", "型号", "赠送数量"});
		jsp1 = new JScrollPane(jt1);
		//add to jp
		jp = new JPanel();
		jp.setLayout(new GridLayout(1,1));
		jp.add(jsp1);
		//------------------------------------------------------------------------------
		top = new JLabel(presentVO.id);
		this.add(top, BorderLayout.NORTH);
		this.add(jp, BorderLayout.CENTER);
	
		this.setVisible(true);
	}
}
