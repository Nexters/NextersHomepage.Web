package com.teamnexters.dto;

import org.springframework.stereotype.Component;

@Component
public class BoardInfoDTO {

	private int postNo;
	private int boardNo;
	private String userNo;
	private String postTitle;
	private String postContent;
	private String postDate;
	private int postHits;
	
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
	
}
