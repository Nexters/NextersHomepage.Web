package com.teamnexters.dto;

import org.springframework.stereotype.Component;

@Component
public class AchieveMemberDTO {

	private int achieveNo;
	private String userNo;
	
	public void setAchieveNo(int achieveNo){
		this.achieveNo=achieveNo;
	}
	public int getAchieveNo(){
		return achieveNo;
	}
	
	public void setUserNo(String userNo){
		this.userNo=userNo;
	}
	public String getUserNo(){
		return userNo;
	}
}
