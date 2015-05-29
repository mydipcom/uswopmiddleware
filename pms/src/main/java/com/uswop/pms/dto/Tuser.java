package com.uswop.pms.dto;

import java.io.Serializable;


/**
 * User information
 *
 * @ClassName: Userinfo
 * @author: Phills
 * @date: 2014-8-21
 *
 */
public class Tuser implements Serializable {
	
	private static final long serialVersionUID = -3933483638567976644L;
	private String userId;
	private String pwd;
	private String userName;
	private String email;
			

	public Tuser() {
		// TODO Auto-generated constructor stub
	}		
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	

	
}
