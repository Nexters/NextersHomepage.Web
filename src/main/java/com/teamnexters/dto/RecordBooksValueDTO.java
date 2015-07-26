package com.teamnexters.dto;

import org.springframework.stereotype.Component;

@Component
public class RecordBooksValueDTO {

	
	private int bookNo;
	private String userNo;
	private String bookColumnNo;
	private String value;
	
	public void setBookNo(int bookNo){
		this.bookNo=bookNo;
	}
	public int getBookNo(){
		return bookNo;
	}
	public void setUserNo(String userNo){
		this.userNo=userNo;
	}
	public String getUserNo(){
		return userNo;
	}
	public void setBookColumnNo(String bookColumnNo){
		this.bookColumnNo=bookColumnNo;
	}
	public String getBookColumnNo(){
		return bookColumnNo;
	}
	public void setValue(String value){
		this.value=value;
	}
	public String getValue(){
		return value;
	}
	
}
