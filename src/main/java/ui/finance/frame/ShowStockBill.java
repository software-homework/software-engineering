package ui.finance.frame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JLabel;

import vo.logistics.LogisticsLossVO;
import vo.logistics.LogisticsOverflowVO;

@SuppressWarnings("serial")
public class ShowStockBill extends JDialog{
	
	FinanceFrame jFrame;
	LogisticsLossVO stockLossVO;
	LogisticsOverflowVO stockOverflowVO;
	int frameHeight = 400;
	int frameWidth = 400;
	JLabel[] jl = new JLabel[4];
	JLabel[] jl2 = new JLabel[4];
	
	public ShowStockBill(FinanceFrame jFrame, String title, boolean modal, LogisticsLossVO stockLossVO, LogisticsOverflowVO stockOverflowVO) {
		super(jFrame, title, modal);
		this.jFrame = jFrame;
		this.stockLossVO = stockLossVO;
		this.stockOverflowVO = stockOverflowVO;
		//
		this.setLayout(new GridLayout(4, 2));
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Toolkit kit = Toolkit.getDefaultToolkit();    	// 定义工具包
	    Dimension screenSize = kit.getScreenSize();   	// 获取屏幕的尺寸			
	    this.setBounds((screenSize.width - frameWidth) / 2, (screenSize.height - frameHeight) / 3, frameWidth, frameHeight);
		//
		if(stockLossVO != null){
			String[] s1 = new String[]{"编号：", "快递名：", "快递型号：", "报损数量："};
			for(int i = 0; i < jl.length; i++){
				jl[i] = new JLabel(s1[i]);
				jl[i].setSize(100, 30);
			}
			String[] s2 = new String[]{stockLossVO.id, stockLossVO.name, stockLossVO.model, Integer.toString(stockLossVO.lossNumber)};
			for(int i = 0; i < jl2.length; i++){
				jl2[i] = new JLabel(s2[i]);
				jl2[i].setSize(100, 30);
			}
		}
		else if(stockOverflowVO != null){
			String[] s1 = new String[]{"编号：", "快递名：", "快递型号：", "报溢数量："};
			for(int i = 0; i < jl.length; i++){
				jl[i] = new JLabel(s1[i]);
				jl[i].setSize(100, 30);
			}
			String[] s2 = new String[]{stockOverflowVO.id, stockOverflowVO.name, stockOverflowVO.model, Integer.toString(stockOverflowVO.overNumber)};
			for(int i = 0; i < jl2.length; i++){
				jl2[i] = new JLabel(s2[i]);
				jl2[i].setSize(100, 30);
			}
		}
		
		for(int i = 0; i < 4; i++){
			this.add(jl[i]); this.add(jl2[i]);
		}
		this.setVisible(true);
	}
}
