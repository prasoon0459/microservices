package com.example.authApp.web;
import java.io.Serializable;

public class RegisterRequest implements Serializable {
	
	private static final long serialVersionUID = -6986746375915710855L;
	private String username;
    private String password;
    private String name;
    private long phone;
    
    
    public RegisterRequest() {
	}
    
	public RegisterRequest(String username, String password, String name, long phone) {
		super();
		this.username = username;
		this.password = password;
		this.name=name;
		this.phone=phone;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}
	
}
