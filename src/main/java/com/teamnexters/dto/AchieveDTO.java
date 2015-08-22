package com.teamnexters.dto;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class AchieveDTO {

	private int achieveNo;
	private String achieveNm;
	private String achieveDesc;
	private String achieveImg;
	private String originAchieveImg;
	private String achieveLink;
	private MultipartFile uploadFile;
	
	public void setAchieveNo(int achieveNo){
		this.achieveNo=achieveNo;
	}
	public int getAchieveNo(){
		return achieveNo;
	}
	public void setAchieveNm(String achieveNm){
		this.achieveNm=achieveNm;
	}
	public String getAchieveNm(){
		return achieveNm;
	}
	public void setAchieveDesc(String achieveDesc){
		this.achieveDesc=achieveDesc;
	}
	public String getAchieveDesc(){
		return achieveDesc;
	}
	public void setAchieveImg(String achieveImg){
		this.achieveImg=achieveImg;
	}
	public String getAchieveImg(){
		return achieveImg;
	}
	public void setAchieveLink(String achieveLink){
		this.achieveLink=achieveLink;
	}
	public String getAchieveLink(){
		return achieveLink;
	}
	public void setUploadFile(MultipartFile uploadFile){
		this.uploadFile=uploadFile;
	}
	public MultipartFile getUploadFile(){
		return uploadFile;
	}
	public void setOriginAchieveImg(String orignAchieveImg){
		this.originAchieveImg=originAchieveImg;
	}
	public String getOriginAchieveImg(){
		return originAchieveImg;
	}
}
