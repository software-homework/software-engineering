package ui.user;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import vo.LogVO;
import vo.user.UserVO;
import businesslogic.LogBL;
import businesslogic.user.UserBL;
import businesslogicservice.LogBLService;
import businesslogicservice.user.UserBLService;

@SuppressWarnings("serial")
public class AdminFrame extends JFrame implements ActionListener{

	UserVO userVO;
	
	JMenuBar bar;
	JMenu system;JMenuItem cancel;JMenuItem exit;
	JMenu admin;JMenuItem updateKey;
	/*******/
	JButton[] user = new JButton[4];
	String[] info = new String[]{"显示用户", "添加用户", "更新信息", "删除用户"};
	/*******/
	JTable table;
	JScrollPane js;
	public AdminFrame(UserVO userVO) {
		this.userVO = userVO;
		this.setTitle("管理员");
	    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(600, 400);
		this.setLocationRelativeTo(null);
		/*******/
		bar = new JMenuBar();
		system = new JMenu("系统");
		system.add(cancel = new JMenuItem("注销用户"));
		system.add(exit = new JMenuItem("退出系统"));
		admin = new JMenu("管理员");
		admin.add(updateKey = new JMenuItem("修改密码"));
		bar.add(system);
		bar.add(admin);
		this.add(bar,BorderLayout.NORTH);
		/*******/
		JPanel userMode = new JPanel();
		userMode.setPreferredSize(new Dimension(100, 0));
		for(int i = 0; i < 4; i++){
			user[i] = new JButton(info[i]);
			userMode.add(user[i]);
		}
		this.add(userMode, BorderLayout.EAST);
		/*******/
		UserBLService userBL = new UserBL();
		ArrayList<UserVO> data0 = userBL.findUser();
		String[] column = new String[]{"类型", "用户名", "密码", "权限"};
		String[][] data = new String[data0.size()][4];
		for(int i = 0; i < data0.size(); i++){
			data[i][0] = data0.get(i).type;
			data[i][1] = data0.get(i).name;
			data[i][2] = data0.get(i).password;
			data[i][3] = data0.get(i).permission;
		}
		this.table = new JTable(data, column);
		this.js = new JScrollPane(this.table);
		this.add(js, BorderLayout.WEST);

		/*************************************************
		 *添加监听
		 *************************************************/
		exit.addActionListener(this);
		cancel.addActionListener(this);
		updateKey.addActionListener(this);
		for(int i = 0; i < 4; i++)
			user[i].addActionListener(this);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == exit)
			System.exit(0);
		else if(e.getSource() == cancel){
			this.dispose();
			new LoginFrame();
		}
		else if(e.getSource() == updateKey){
			new DialogUpdateKey(this.userVO, this);
			//日志
			LogBLService logBLService = new LogBL();
			logBLService.addLog(new LogVO(this.userVO.name, "修改管理员密码"));
		}
		else if(e.getSource() == user[0]){
			this.remove(this.js);
			
			UserBLService userBL = new UserBL();
			ArrayList<UserVO> data0 = userBL.findUser();
			String[] column = new String[]{"类型", "用户名","密码" ,"权限"};
			String[][] data = new String[data0.size()][4];
			for(int i = 0; i < data0.size(); i++){
				data[i][0] = data0.get(i).type;
				data[i][1] = data0.get(i).name;
				data[i][2] = data0.get(i).password;
				data[i][3] = data0.get(i).permission;
			}
			this.table = new JTable(data, column);
			this.js = new JScrollPane(table);
			this.add(this.js, BorderLayout.WEST);
			this.setVisible(true);
		}
		else if(e.getSource() == user[1]){
			new DialogAdd(this, this.userVO);
			//日志
			LogBLService logBLService = new LogBL();
			logBLService.addLog(new LogVO(this.userVO.name, "添加新用户"));
			/********刷新**/
			this.remove(this.js);

			UserBLService userBL = new UserBL();
			ArrayList<UserVO> data0 = userBL.findUser();
			String[] column = new String[]{"类型", "用户名","密码" ,"权限"};
			String[][] data = new String[data0.size()][4];
			for(int i = 0; i < data0.size(); i++){
				data[i][0] = data0.get(i).type;
				data[i][1] = data0.get(i).name;
				data[i][2] = data0.get(i).password;
				data[i][3] = data0.get(i).permission;
			}
			this.table = new JTable(data, column);
			this.js = new JScrollPane(table);
			this.add(this.js, BorderLayout.WEST);
			this.setVisible(true);
		}
		else if(e.getSource() == user[2]){
			UserBLService userBL = new UserBL();
			ArrayList<UserVO> old = new ArrayList<UserVO>();
			old = userBL.findUser();
			
			ArrayList<UserVO> newUser = new ArrayList<UserVO>();
			for(int i = 0; i < table.getRowCount(); i++){
				if(((String)this.table.getValueAt(i, 0)).equals("总经理") &&
						!(((String)this.table.getValueAt(i, 3)).equals("经理"))){
					JOptionPane.showMessageDialog(null, "总经理应为最高权限!", null, 0);
					break;
				}
				else if(!(((String)this.table.getValueAt(i, 0)).equals("总经理") ||
						((String)this.table.getValueAt(i, 0)).equals("财务人员") ||
						((String)this.table.getValueAt(i, 0)).equals("管理员") ||
						((String)this.table.getValueAt(i, 0)).equals("库存管理人员") ||
						((String)this.table.getValueAt(i, 0)).equals("进货销售人员"))){
					JOptionPane.showMessageDialog(null, "存在非法用户类型!", null, 0);
					break;
				}
				else if(!(((String)this.table.getValueAt(i, 3)).equals("经理") ||
						((String)this.table.getValueAt(i, 3)).equals("空") ||
						((String)this.table.getValueAt(i, 3)).equals("普通职员"))){
					JOptionPane.showMessageDialog(null, "存在非法用户权限!", null, 0);
					break;
				}
				else{
					newUser.add(new UserVO((String)this.table.getValueAt(i, 0), 
						(String)this.table.getValueAt(i, 1), 
						(String)this.table.getValueAt(i, 2), 
						(String)this.table.getValueAt(i, 3)));
					if(i == table.getRowCount() - 1){
						for(int j = 0 ; j < table.getRowCount(); j++)
							userBL.updateUser(old.get(j), newUser.get(j));
						//日志
						LogBLService logBLService = new LogBL();
						logBLService.addLog(new LogVO(this.userVO.name, "更新用户信息"));
						JOptionPane.showMessageDialog(null, "更新成功!", null, 1);
						/********刷新**/
						this.remove(this.js);
						ArrayList<UserVO> data0 = userBL.findUser();
						String[] column = new String[]{"类型", "用户名","密码" ,"权限"};
						String[][] data = new String[data0.size()][4];
						for(int k = 0; k < data0.size(); k++){
							data[k][0] = data0.get(k).type;
							data[k][1] = data0.get(k).name;
							data[k][2] = data0.get(k).password;
							data[k][3] = data0.get(k).permission;
						}
						this.table = new JTable(data, column);
						this.js = new JScrollPane(table);
						this.add(this.js, BorderLayout.WEST);
						this.setVisible(true);
					}
				}
			}
			
		}
		else if(e.getSource() == user[3]){
			UserBLService userBL = new UserBL();
			int[] n = this.table.getSelectedRows();
			if(n.length == 0)
				JOptionPane.showMessageDialog(null, "请选择删除的用户!", null, 0);
			else{
				UserVO user;
				for(int i = 0; i < n.length; i++){
					user = new UserVO((String)this.table.getValueAt(n[i], 0), 
							(String)this.table.getValueAt(n[i], 1), 
							(String)this.table.getValueAt(n[i], 2), 
							(String)this.table.getValueAt(n[i], 3));
					userBL.deleteUser(user);
				}
				//日志
				LogBLService logBLService = new LogBL();
				logBLService.addLog(new LogVO(this.userVO.name, "删除用户"));
				/********刷新**/
				this.remove(this.js);
				ArrayList<UserVO> data0 = userBL.findUser();
				String[] column = new String[]{"类型", "用户名", "密码", "权限"};
				String[][] data = new String[data0.size()][4];
				for(int i = 0; i < data0.size(); i++){
					data[i][0] = data0.get(i).type;
					data[i][1] = data0.get(i).name;
					data[i][2] = data0.get(i).password;
					data[i][3] = data0.get(i).permission;
				}
				this.table = new JTable(data, column);
				this.js = new JScrollPane(table);
				this.add(this.js, BorderLayout.WEST);
				this.setVisible(true);
			}
		}
	}

}
