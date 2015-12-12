package ui.logistics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import ui.user.LoginFrame;
import vo.LogVO;
import vo.logistics.CategoryVO;
import vo.logistics.CommodityVO;
import vo.user.UserVO;
import businesslogic.LogBL;
import businesslogic.logistics.CategoryBL;
import businesslogic.logistics.CommodityBL;
import businesslogicservice.LogBLService;
import businesslogicservice.logistics.CategoryBLService;
import businesslogicservice.logistics.CommodityBLService;

@SuppressWarnings({ "serial", "unused" })
public class LogisticsMain extends JFrame {
	static LogisticsMain sm;
	private CommodityBLService commoditybl;
	private CategoryBLService categorybl;
	public JMenuBar mb;
	public JMenu menu1,menu2;
	public JMenuItem item1,item2,item3,item4,item5,item6,item7,item8,item9,item10;
	public JTree tree;
	public JTable table;
	public JScrollPane scrollTable;
	public JLabel label;
	public JToolBar toolBar;
	public boolean b1;
	public boolean b2;
	public CategoryVO cv = null;
	public CategoryVO father = null;
	public CommodityVO commodity;
	public UserVO uvo;
	
	public LogisticsMain(UserVO user){
		sm = this;
		b1 = false;
		b2 = false;
		cv = null;
		father = null;
		commodity = new CommodityVO();
		commoditybl = new CommodityBL();
		categorybl = new CategoryBL();
		uvo = user;
		
		this.setSize(900, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int)(width - this.getWidth()) / 2,(int)(height - this.getHeight()) / 2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setTitle("逆风快递系统-物流人员");
		
		label = new JLabel("状态栏");
		toolBar = new JToolBar();
		toolBar.add(label);
		toolBar.setFloatable(false);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		tree = new JTree();
		tree.setModel(new LogisticsTree());
		this.getContentPane().add(tree,BorderLayout.WEST);
		tree.setVisible(true);
		tree.addTreeSelectionListener(new TreeSelectionListener(){
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				// TODO Auto-generated method stub
				CategoryVO o = (CategoryVO)e.getPath().getLastPathComponent();
				cv = (CategoryVO) o;
				if(!(o.id).equals("-1")){
					father = categorybl.getFather(o);
				}
				else{
					father = null;
				}
				updateTable();
				b2 = true;
        		b1 = true;
			}
			
		});
		
		tree.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(e.getButton() == MouseEvent.BUTTON3){
					if(b1){
						JPopupMenu jpm = new JPopupMenu();
						if(cv != null){
							if(!(cv.id).equals("-1")){
								if(cv.categoryList.size() == 0){
									if(cv.commodityList.size() == 0){
										JMenuItem jmi2 = new JMenuItem("添加分类");
										jmi2.addMouseListener(new MouseAdapter(){
											public void mouseReleased(MouseEvent e){
												new AddCategory(LogisticsMain.this);
											}
										});
										jpm.add(jmi2);
										
										JMenuItem jmi1 = new JMenuItem("添加商品");
										jmi1.addMouseListener(new MouseAdapter(){
											public void mouseReleased(MouseEvent e){
												new AddCommodity(LogisticsMain.this);
											}
										});
										jpm.add(jmi1);
										
									    deleteCategory(jpm);
									    
									    JMenuItem jmi10 = new JMenuItem("修改分类");
										jmi10.addMouseListener(new MouseAdapter(){
											public void mouseReleased(MouseEvent e){
												new UpdateCategory(LogisticsMain.this);
											}
										});
										jpm.add(jmi10);
										
									}
									else{
										JMenuItem jmi3 = new JMenuItem("添加商品");
										jmi3.addMouseListener(new MouseAdapter(){
											public void mouseReleased(MouseEvent e){
												new AddCommodity(LogisticsMain.this);
											}
										});
										jpm.add(jmi3);
										
										JMenuItem jmi12 = new JMenuItem("修改分类");
										jmi12.addMouseListener(new MouseAdapter(){
											public void mouseReleased(MouseEvent e){
													new UpdateCategory(LogisticsMain.this);
											}
										});
										jpm.add(jmi12);
									}
								}
								else{
									JMenuItem jmi4 = new JMenuItem("添加分类");
									jmi4.addMouseListener(new MouseAdapter(){
										public void mouseReleased(MouseEvent e){
											new AddCategory(LogisticsMain.this);
										}
									});
									jpm.add(jmi4);
									
									JMenuItem jmi13 = new JMenuItem("修改分类");
									jmi13.addMouseListener(new MouseAdapter(){
										public void mouseReleased(MouseEvent e){
												new UpdateCategory(LogisticsMain.this);
										}
									});
									jpm.add(jmi13);
								}
								
							}
							else{
								JMenuItem jmi5 = new JMenuItem("添加分类");
								jmi5.addMouseListener(new MouseAdapter(){
									public void mouseReleased(MouseEvent e){
										new AddCategory(LogisticsMain.this);
									}
								});
								jpm.add(jmi5);
							}
						}
						jpm.show(e.getComponent(),e.getX(),e.getY());
					}
				}
				else if(e.getButton() == MouseEvent.BUTTON1){
					if (b1 && !b2){
						tree.clearSelection();
						cv = null;
						father = null;
						b1 = false;
						updateTable();
					}
					b2 = false;
				}
			}
		});

		table = new JTable();
    	scrollTable=new JScrollPane(table);
		this.add(scrollTable,BorderLayout.CENTER);
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		scrollTable.setVisible(true);
		table.setModel(new CommodityTable(this));
		
		table.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if (e.getButton() == MouseEvent.BUTTON3){
					if(table.getSelectedRowCount() > 0){
						JPopupMenu jpm = new JPopupMenu();
						deleteCommodity(jpm);
						
						JMenuItem jmi = new JMenuItem("修改商品");
						jmi.addMouseListener(new MouseAdapter(){
							public void mouseReleased(MouseEvent e){
								new UpdateCommodity(LogisticsMain.this);
							}
						});
						jpm.add(jmi);
						
						stockOverflow(jpm);
						
						stockLoss(jpm);
						
						sendDanger(jpm);
						
						jpm.show(e.getComponent(), e.getX(), e.getY());
					}
				}
			}
			
	        public void mouseReleased(MouseEvent e){
				if (e.getButton() == MouseEvent.BUTTON1){
					commodity = commoditybl.getCommodity((String)table.getValueAt(table.getSelectedRow(), 0));
				}
			}
		});
		
		addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent e){
				int width = getWidth();
				int height = getHeight();
				tree.setSize(width / 3 - 2, height);
				scrollTable.setLocation(width / 3, 0);
				scrollTable.setSize(width - width / 3, height);
				revalidate();
			}
		});
		
		mb = new JMenuBar();
		menu1 = new JMenu("系统");
		menu2 = new JMenu("操作");
		item1 = new JMenuItem("查找商品");
		item2 = new JMenuItem("库存查看");
		item3 = new JMenuItem("库存盘点");
		item4 = new JMenuItem("库存赠送");
		item5 = new JMenuItem("查看消息");
		item6 = new JMenuItem("注销");
		item7 = new JMenuItem("退出系统");
		item8 = new JMenuItem("查看库存报溢单");
		item9 = new JMenuItem("查看库存报损单");
		item10 = new JMenuItem("查看库存赠送单");
		
		item1.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				new FindCommodity(LogisticsMain.this);
			}
		});
		
		item2.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				new ShowLogistics(LogisticsMain.this);
			}
		});
		
		item3.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				commoditybl.checkStock();
				new CheckLogistics(LogisticsMain.this);
				LogBLService lbs = new LogBL();
				lbs.addLog(new LogVO(uvo.name,"库存盘点"));
			}
		});
		
		item4.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				new SendPresent(LogisticsMain.this);
			}
		});
		
		item5.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				new MessageMain(LogisticsMain.this);
			}
		});
		
		item6.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				//注销操作
				sm.dispose();
				new LoginFrame();
			}
		});
		
		item7.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				System.exit(0);
			}
		});
		
		item8.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				new CheckLogisticsOverflow(LogisticsMain.this);
				LogBLService lbs = new LogBL();
				lbs.addLog(new LogVO(uvo.name,"查看库存报溢单"));
			}
		});
		
		item9.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				new CheckLogisticsLoss(LogisticsMain.this);
				LogBLService lbs = new LogBL();
				lbs.addLog(new LogVO(uvo.name,"查看库存报损单"));
			}
		});
		
		item10.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				new CheckPresent(LogisticsMain.this);
				LogBLService lbs = new LogBL();
				lbs.addLog(new LogVO(uvo.name,"查看库存赠送单"));
			}
		});
		
		menu2.add(item1);
		menu2.addSeparator();
		menu2.add(item2);
		menu2.add(item3);
		menu2.add(item4);
		menu2.add(item5);
		menu2.add(item8);
		menu2.add(item9);
		menu2.add(item10);
		menu1.add(item6);
		menu1.add(item7);
		mb.add(menu1);
		mb.add(menu2);
		this.setJMenuBar(mb);
		
		this.setVisible(true);
		new MessageMain(LogisticsMain.this);
	}
	
	public void deleteCommodity(JPopupMenu jpm){
		JMenuItem jmi = new JMenuItem("删除商品");
		jmi.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				if(table.getSelectedRowCount() > 0){
					if(JOptionPane.showConfirmDialog(sm, "你确定要删除商品？") == JOptionPane.OK_OPTION){
						if(commoditybl.deleteCommodity(commoditybl.getCommodity((String)table.getValueAt(table.getSelectedRow(), 0)), cv)){
//							JOptionPane.showMessageDialog(sm, "删除成功！");
							sm.setText("删除成功！");
							LogBLService lbs = new LogBL();
							lbs.addLog(new LogVO(uvo.name,"删除商品"));
							update();
						}
						else{
							JOptionPane.showMessageDialog(sm, "删除失败！");
						}
					}
				}
			}
		});
		jpm.add(jmi);	
	}
	
	public void deleteCategory(JPopupMenu jpm){
		JMenuItem jmi = new JMenuItem("删除分类");
		jmi.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				if(cv != null){
					if(cv.categoryList.size() == 0 && cv.commodityList.size() == 0){
						if(JOptionPane.showConfirmDialog(sm, "你确定要删除这个商品分类？") == JOptionPane.OK_OPTION){
							if(categorybl.deleteCategory(cv, father)){
//								JOptionPane.showMessageDialog(sm, "删除成功！");
								cv = null;
								sm.setText("删除成功！");
								LogBLService lbs = new LogBL();
								lbs.addLog(new LogVO(uvo.name,"删除商品分类"));
								update();
							}
						    
							else{
								JOptionPane.showMessageDialog(sm, "删除失败！");
							}
						}
					}
					else{
						JOptionPane.showMessageDialog(sm, "无法删除！");
					}
//					update();
				}
			}
		});
		jpm.add(jmi);
	}
	
	public void stockOverflow(JPopupMenu jpm){
		JMenuItem jmi = new JMenuItem("库存报溢");
		jmi.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				new LogisticsOverflow(LogisticsMain.this);
			}
		});
		jpm.add(jmi);
	}
	
	public void stockLoss(JPopupMenu jpm){
		JMenuItem jmi = new JMenuItem("库存报损");
		jmi.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				new LogisticsLoss(LogisticsMain.this);
			}
		});
		jpm.add(jmi);
	}
	
	public void sendDanger(JPopupMenu jpm){
		JMenuItem jmi = new JMenuItem("设置警戒值");
		jmi.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				new SendDanger(LogisticsMain.this);
			}
		});
		jpm.add(jmi);
	}
	
	public int getRowCount(){
		return ((scrollTable.getHeight()) / table.getRowHeight());
	}

	public void updateTable(){
		table.setModel(new CommodityTable(this));
		commodity = new CommodityVO();
		scrollTable.revalidate();
	}
	
	public void updateTree(){
		if (cv != null){
			selectCategory(cv);
		}
		else if (father != null){
			selectCategory(father);
		}
		else{
			CloseTree();
		}
	}
	
	public void update(){
		updateTree();
		updateTable();
	}
	
	public int expendTree(CategoryVO category){
		if (category == null || category.id.equals(categorybl.getRoot().id)){
			tree.expandRow(0);
			return 0;
		}
		
		CategoryVO father = categorybl.getFather(category);
		if (father == null){
			return 0;
		}
		
		int index = 0;
		for (int i = 0; i < father.categoryList.size(); i ++){
			if (father.categoryList.get(i).id.equals(category.id)){
				index = i;
				break;
			}
		}
		int row = expendTree(father) + index + 1;
		tree.expandRow(row);
		return row;
	}
	
	public void CloseTree(){
		tree.setModel(new LogisticsTree());
		tree.revalidate();
	}
	
	public void selectCategory(CategoryVO category){
		CloseTree();
		tree.setSelectionRow(expendTree(category));
	}
	
	public void selectCommodity(CommodityVO commodity, CategoryVO father){
		selectCategory(father);
		int index = 0;
		for (int i = 0; i < father.commodityList.size(); i ++){
			if (father.commodityList.get(i).id.equals(commodity.id)){
				index = i;
				break;
			}
		}
		table.setRowSelectionInterval(index, index);
	}
	
	public void setText(String str){
		label.setText(str);
	}
}
