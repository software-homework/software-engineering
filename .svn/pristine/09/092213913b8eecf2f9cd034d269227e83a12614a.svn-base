package po.user;

import java.io.Serializable;

import vo.user.UserVO;

@SuppressWarnings("serial")
public class UserPO implements Serializable
{
	private String name;
	private String password;
	private String type;
	private String permission;
	
	public UserPO(String type, String name,String password, String permission){
		this.name=name;
		this.password=password;
		this.type=type;
		this.setPermission(permission);
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setPassword(String password){
		this.password=password;
	}
	
	public void setType(String type){
		this.type=type;
	}
	
	public String getName(){
		return name;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getType(){
		return type;
	}
	
	public String getPermission() {
		return permission;
	}
	
	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	
	public UserVO ToVO()
	{
		return new UserVO(type, name, password, permission);
	}
}
