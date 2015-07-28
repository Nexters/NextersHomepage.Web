package com.teamnexters.dto;

import org.springframework.stereotype.Component;

@Component
public class ProjectDTO {
	private int projectNo;
	private String projectNm;
	private String projectDesc;
	private String projectImg;
	private String projectLink;
	
	public void setProjectNo(int projectNo){
		this.projectNo=projectNo;
	}
	public int getProjectNo(){
		return projectNo;
	}
	public void setProjectNm(String projectNm){
		this.projectNm=projectNm;
	}
	public String getProjectNm(){
		return projectNm;
	}
	public void setProjectDesc(String projectDesc){
		this.projectDesc=projectDesc;
	}
	public String getProjectDesc(){
		return projectDesc;
	}
	public void setProjectImg(String projectImg){
		this.projectImg=projectImg;
	}
	public String getProjectImg(){
		return projectImg;
	}
	public void setProjectLink(String projectLink){
		this.projectLink=projectLink;
	}
	public String getProjectLink(){
		return projectLink;
	}
}
