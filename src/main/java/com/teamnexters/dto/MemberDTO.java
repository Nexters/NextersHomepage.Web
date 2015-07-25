package com.teamnexters.dto;


import org.springframework.stereotype.Component;

@Component
public class MemberDTO {     
	private String userNo;     
	private String userId;     
	private String userPw;     
	private String userNm;     
	private String userCellNum;     
	private int userStatus;
	private int userRole;
	private String activityYN;
	
	public void setUserNo(String userNo){
		this.userNo=userNo;
	}
	public String getUserNo(){
		return userNo;
	}
	
	public void setUserId(String userId){
		this.userId=userId;
	}
	public String getUserId(){
		return userId;
	}
	
	public void setUserPw(String userPw){
		this.userPw=userPw;
	}
	
	public String getUserPw() {
		return userPw;
	}
	public void setUserNm(String userNm){
		this.userNm=userNm;
	}
	public String getUserNm(){
		return userNm;
	}
	
	public void setUserCellNum(String userCellNum){
		this.userCellNum=userCellNum;
	}
	public String getUserCellNum(){
		return userCellNum;
	}
	
	public void setUserStatus(int userStatus){
		this.userStatus=userStatus;
	}
	public int getUserStatus(){
		return userStatus;
	}
	public int getUserRole() {
		return userRole;
	}
	public void setUserRole(int userRole) {
		this.userRole = userRole;
	}
	public void setActivityYN(String activityYN){
		this.activityYN=activityYN;
	}
	public String getActivityYN(){
		return activityYN;
	}
} 


