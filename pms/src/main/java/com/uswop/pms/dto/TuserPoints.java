package com.uswop.pms.dto;

import java.io.Serializable;


/**
 * User points information
 *
 * @ClassName: Userinfo
 * @author: Phills
 * @date: 2014-8-21
 *
 */
public class TuserPoints implements Serializable {
	
	private static final long serialVersionUID = 2031620924601998060L;
	
	private String userId;
	private int points;
			

	public TuserPoints() {
		// TODO Auto-generated constructor stub
	}
	
	public TuserPoints(String userId,int points) {
		this.userId=userId;
		this.points=points;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}	

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
}
