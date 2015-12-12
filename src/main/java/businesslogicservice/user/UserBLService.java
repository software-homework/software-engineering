package businesslogicservice.user;

import java.util.ArrayList;

import vo.user.UserVO;

public interface UserBLService 
{
	public boolean login(UserVO userVO);
	public boolean addUser(UserVO userVO);
	public boolean deleteUser(UserVO userVO);
	public boolean updateUser(UserVO old_userVO, UserVO new_userVO);
	public ArrayList<UserVO> findUser();
	public boolean updateAdmin(String key);
}
