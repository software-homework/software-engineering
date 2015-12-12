package dataservice.user;

import java.util.ArrayList;

import po.user.UserPO;

public interface UserDataService {
	public boolean addUser(UserPO userPO);

	public boolean deleteUser(UserPO userPO);

	public boolean updateUser(UserPO old_userPO, UserPO new_userPO);

	public ArrayList<Object> findUser();

	public boolean updateAdmin(String key);

	public boolean find(String type, String name, String password);

	public ArrayList<String> getUsername(String type);
}
