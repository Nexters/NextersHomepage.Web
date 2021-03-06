package com.teamnexters.dto;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class BoardInfoDTO {

	private int postNo;
	private int boardNo;
	private String userNo;
	private String userName;
	private String postTitle;
	private String postContent;
	private String postDate;
	private int postHits;
	private MultipartFile uploadFile;
	private String file;
	private int commentCount;
	
	public void setPostNo(int postNo){
		this.postNo=postNo;
	}
	public int getPostNo(){
		return postNo;
	}
	public void setBoardNo(int boardNo){
		this.boardNo=boardNo;
	}
	public int getBoardNo(){
		return boardNo;
	}
	public void setUserNo(String userNo){
		this.userNo=userNo;
	}
	public String getUserNo(){
		return userNo;
	}
	public void setUserName(String userName){
		this.userName=userName;
	}
	public String getUserName(){
		return userName;
	}
	public void setPostTitle(String postTitle){
		this.postTitle=postTitle;
	}
	public String getPostTitle(){
		return postTitle;
	}
	public void setPostContent(String postContent){
		this.postContent=postContent;
	}
	public String getPostContent(){
		return postContent;
	}
	public void setPostDate(String postDate){
		this.postDate=postDate;
	}
	public String getPostDate(){
		return postDate;
	}
	public void setPostHits(int postHits){
		this.postHits=postHits;
	}
	public int getPostHits(){
		return postHits;
	}
	public void setUploadFile(MultipartFile uploadFile){
		this.uploadFile=uploadFile;
	}
	public MultipartFile getUploadFile(){
		return uploadFile;
	}
	public void setFile(String file){
		this.file=file;
	}
	public String getFile(){
		return file;
	}
	public void setCommentCount(int commentCount){
		this.commentCount=commentCount;
	}
	public int getCommentCount(){
		return commentCount;
	}
	
	
}
