package com.teamnexters.dto;

public class MemberDTO {     
	private String userNo;     
	private String userId;     
	private String userPw;     
	private String userNm;     
	private String userCellNum;     
	private String userPic;
	private String userStatus;
	private int userRole;
	private String errCd;
	private String errMsg;
	private String errAct;
	
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
	
	public void setUserPic(String userPic){
		this.userPic=userPic;
	}
	public String getUserPic(){
		return userPic;
	}
	
	public void setUserStatus(String userStatus){
		this.userStatus=userStatus;
	}
	public String getUserStatus(){
		return userStatus;
	}
	public int getUserRole() {
		return userRole;
	}
	public void setUserRole(int userRole) {
		this.userRole = userRole;
	}
	public String getErrCd() {
		return errCd;
	}
	public void setErrCd(String errCd) {
		this.errCd = errCd;
	}

	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public String getErrAct() {
		return errAct;
	}
	public void setErrAct(String errAct) {
		this.errAct = errAct;
	}
} 


