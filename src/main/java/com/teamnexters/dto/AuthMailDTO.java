package com.teamnexters.dto;

import org.springframework.stereotype.Component;

@Component
public class AuthMailDTO {

	private String userNo;
	private String hashedCode;
	
	public void setUserNo(String userNo){
		this.userNo=userNo;
	}
	public String getUserNo(){
		return userNo;
	}
	
	public void setHashedCode(String hashedCode){
		this.hashedCode=hashedCode;
	}
	public String getHashedCode(){
		return hashedCode;
	}
}
