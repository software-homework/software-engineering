package data.user;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import dataservice.user.UserDataService;
import io.Input;
import io.Output;
import po.user.UserPO;

public class UserData extends UnicastRemoteObject implements UserDataService {
	private static final long serialVersionUID = -8868676826752697887L;
	private String userPath = "user.txt";
	private ArrayList<Object> userList = new ArrayList<Object>();

	public UserData() throws RemoteException {

	}

	public boolean find(String type, String name, String password) {
		Input input = new Input();
		userList = input.input(userPath);

		if (type.equals("管理员")) {

			boolean isExist = false;
			for (int i = 0; i < userList.size(); i++) {
				if (((UserPO) userList.get(i)).getType().equals(type))
					isExist = true;
			}

			if (isExist) {
				for (int i = 0; i < userList.size(); i++) {
					if (((UserPO) userList.get(i)).getName().equals(name)
							&& ((UserPO) userList.get(i)).getType().equals(type)
							&& ((UserPO) userList.get(i)).getPassword().equals(password))
						return true;
				}
				return false;
			} else {
				if (type.equals("管理员") && name.equals("admin") && password.equals("admin"))
					return true;
				return false;
			}

		} else {
			for (int i = 0; i < userList.size(); i++) {
				if (((UserPO) userList.get(i)).getName().equals(name)
						&& ((UserPO) userList.get(i)).getType().equals(type)
						&& ((UserPO) userList.get(i)).getPassword().equals(password))
					return true;
			}
			return false;
		}
	}

	public ArrayList<String> getUsername(String type) {
		Input input = new Input();
		userList = input.input(userPath);
		ArrayList<String> username = new ArrayList<String>();
		for (int i = 0; i < userList.size(); i++) {
			if (((UserPO) userList.get(i)).getType().equals(type)) {
				username.add(((UserPO) userList.get(i)).getName());
			}
		}
		return username;
	}

	@SuppressWarnings("unused")
	public boolean addUser(UserPO userPO) {
		Input input = new Input();
		userList = input.input(userPath);

		for (int i = 0; i < userList.size(); i++) {
			if (((UserPO) userList.get(i)).getName().equals(userPO.getName())
					&& ((UserPO) userList.get(i)).getType().equals(userPO.getType()))
				return false;
		}

		userList.add(userPO);
		Output output = new Output(userList, userPath);
		return true;
	}

	@SuppressWarnings("unused")
	public boolean deleteUser(UserPO userPO) {
		Input input = new Input();
		userList = input.input(userPath);

		for (int i = 0; i < userList.size(); i++) {
			if (((UserPO) userList.get(i)).getName().equals(userPO.getName())
					&& ((UserPO) userList.get(i)).getType().equals(userPO.getType())) {
				userList.remove(i);
				Output output = new Output(userList, userPath);
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unused")
	public boolean updateUser(UserPO old_userPO, UserPO new_userPO) {
		Input input = new Input();
		userList = input.input(userPath);

		for (int i = 0; i < userList.size(); i++) {
			if (((UserPO) userList.get(i)).getName().equals(old_userPO.getName())
					&& ((UserPO) userList.get(i)).getType().equals(old_userPO.getType())) {
				userList.set(i, new_userPO);
				Output output = new Output(userList, userPath);
				return true;
			}
		}
		return false;
	}

	public ArrayList<Object> findUser() {
		Input input = new Input();
		userList = input.input(userPath);
		return userList;
	}

	@SuppressWarnings("unused")
	public boolean updateAdmin(String key) {
		Input input = new Input();
		userList = input.input(userPath);

		for (int i = 0; i < userList.size(); i++) {
			if (((UserPO) userList.get(i)).getType().equals("管理员")) {
				UserPO temp = (UserPO) userList.get(i);
				temp.setPassword(key);
				userList.set(i, temp);
				Output output = new Output(userList, userPath);
				return true;
			}
		}

		UserPO user = new UserPO("管理员", "admin", key);
		userList.add(user);
		Output output = new Output(userList, userPath);
		return true;
	}
}
