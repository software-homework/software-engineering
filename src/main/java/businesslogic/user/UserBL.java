package businesslogic.user;

import java.util.ArrayList;

import businesslogic.courier.GetUsername;
import businesslogicservice.user.UserBLService;
import data.user.UserData;
import dataservice.user.UserDataService;
import po.user.UserPO;
import vo.user.UserVO;

public class UserBL implements UserBLService, GetUsername {
	UserDataService uds = null;

	public UserBL() {
		try {
			uds = new UserData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean login(UserVO user) {
		return uds.find(user.type, user.name, user.password);
	}

	public String[] getUsername(String type) {
		ArrayList<String> userlist = new ArrayList<String>();
		userlist = uds.getUsername(type);
		int size = userlist.size();
		String[] username = (String[]) userlist.toArray(new String[size]);
		return username;
	}

	public boolean addUser(UserVO userVO) {
		UserPO userPO = new UserPO(userVO.type, userVO.name, userVO.password);
		return uds.addUser(userPO);
	}

	public boolean deleteUser(UserVO userVO) {
		UserPO userPO = new UserPO(userVO.type, userVO.name, userVO.password);
		return uds.deleteUser(userPO);
	}

	public boolean updateUser(UserVO old_userVO, UserVO new_userVO) {
		return uds.updateUser(new UserPO(old_userVO.type, old_userVO.name, old_userVO.password),
				new UserPO(new_userVO.type, new_userVO.name, new_userVO.password));
	}

	public ArrayList<UserVO> findUser() {
		ArrayList<Object> temp = uds.findUser();
		ArrayList<UserVO> res = new ArrayList<UserVO>();
		for (int i = 0; i < temp.size(); i++) {
			res.add(((UserPO) temp.get(i)).ToVO());
		}
		return res;
	}

	public boolean updateAdmin(String key) {
		return uds.updateAdmin(key);
	}
}
