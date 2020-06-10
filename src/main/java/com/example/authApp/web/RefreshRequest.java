package com.example.authApp.web;
import java.io.Serializable;

public class RefreshRequest implements Serializable {
	
	private static final long serialVersionUID = -6986746375915710855L;
	private String refresh_token;  
    
    public RefreshRequest() {
    	
    }
	public RefreshRequest( String refresh_token) {
		super();
		this.refresh_token=refresh_token;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	
}
