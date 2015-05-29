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
public class TuserAddr implements Serializable {
	
	private static final long serialVersionUID = -3933483638567976644L;	
	private int addrId;
	private String address;
	private String recipients;
	private String phone;
	private String country;
	private String postalcode;
			

	public TuserAddr() {
		// TODO Auto-generated constructor stub
	}	

	public int getAddrId() {
		return addrId;
	}

	public void setAddrId(int addrId) {
		this.addrId = addrId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRecipients() {
		return recipients;
	}

	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	
	
	
}
