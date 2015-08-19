package com.teamnexters.dto;

import org.springframework.stereotype.Component;

@Component
public class BoardCommentDTO {

	private int commentNo;
	private int postNo;
	private String userNo;
	private String userNm;
	private String postComment;
	private String commentDate;
	
	public void setCommentNo(int commentNo){
		this.commentNo=commentNo;
	}
	public int getCommentNo(){
		return commentNo;
	}
	
	public void setPostNo(int postNo){
		this.postNo=postNo;
	}
	public int getPostNo(){
		return postNo;
	}
	
	public void setUserNo(String userNo){
		this.userNo=userNo;
	}
	public String getUserNo(){
		return userNo;
	}
	
	public void setUserNm(String userNm){
		this.userNm=userNm;
	}
	public String getUserNm(){
		return userNm;
	}
	
	public void setPostComment(String postComment){
		this.postComment=postComment;
	}
	public String getPostComment(){
		return postComment;
	}
	
	public void setCommentDate(String commentDate){
		this.commentDate=commentDate;
	}
	public String getCommentDate(){
		return commentDate;
	}
}
