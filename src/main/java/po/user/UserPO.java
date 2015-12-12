package po.user;

import java.io.Serializable;

import vo.user.UserVO;

@SuppressWarnings("serial")
public class UserPO implements Serializable {
	private String name;
	private String password;
	private String type;

	public UserPO(String type, String name, String password) {
		this.name = name;
		this.password = password;
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getType() {
		return type;
	}

	public UserVO ToVO() {
		return new UserVO(type, name, password);
	}
}
