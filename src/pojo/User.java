package pojo;

public class User {
	
	public int user_id;
	public String user_name;
	public String password;
	public String mobile;
	public String email;
	public String isDisabled;
		
	
	
	public User(int user_id) {
		super();
		this.user_id=user_id;
	}
	
//	public User(int user_id, String user_name, String password, String mobile, String email) {
//		super();
//		this.user_id = user_id;
//		this.user_name = user_name;
//		this.password = password;
//		this.mobile = mobile;
//		this.email = email;
//	}
	public User(int id, String userName, String password2, String mobile2, String email1, String isDisabled) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.password = password;
		this.mobile = mobile;
		this.email = email;
		this.isDisabled=isDisabled;
	}

	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "Register [user_id=" + user_id + ", user_name=" + user_name + ", password=" + password + ", mobile="
				+ mobile + ", email=" + email + "]";
	}
	
	

}
