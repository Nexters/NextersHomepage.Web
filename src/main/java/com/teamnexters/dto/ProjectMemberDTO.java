package com.teamnexters.dto;

import org.springframework.stereotype.Component;

@Component
public class ProjectMemberDTO {

	private int projectNo;
	private String userNo;
	
	public void setProjectNo(int projectNo){
		this.projectNo=projectNo;
	}
	public int getProjectNo(){
		return projectNo;
	}
	
	public void setUserNo(String userNo){
		this.userNo=userNo;
	}
	public String getUserNo(){
		return userNo;
	}
}
