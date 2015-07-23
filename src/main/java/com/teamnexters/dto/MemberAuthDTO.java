package com.teamnexters.dto;

public class MemberAuthDTO {
	private String auth_key;
	private String auth_user;
	private String auth_valid;
	private String insDate;
	
	public String getAuth_key() {
		return auth_key;
	}
	public void setAuth_key(String auth_key) {
		this.auth_key = auth_key;
	}
	public String getAuth_user() {
		return auth_user;
	}
	public void setAuth_user(String auth_user) {
		this.auth_user = auth_user;
	}
	public String getAuth_valid() {
		return auth_valid;
	}
	public void setAuth_valid(String auth_valid) {
		this.auth_valid = auth_valid;
	}
	public String getAuth_insDate() {
		return insDate;
	}
	public void setAuth_insDate(String insDate) {
		this.insDate = insDate;
	}
	
}
