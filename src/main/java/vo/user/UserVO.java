package vo.user;

public class UserVO {
	public String name;
	public String password;
	public String type;

	public UserVO(String type, String name, String password) {
		this.name = name;
		this.password = password;
		this.type = type;
	}
}
