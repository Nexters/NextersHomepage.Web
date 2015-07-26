package com.teamnexters.dto;

import org.springframework.stereotype.Component;

@Component
public class RecordBooksColumnDTO {

	private String bookColumnNo;
	private int bookNo;
	private String bookAttr;
	
	public void setBookColumnNo(String bookColumnNo){
		this.bookColumnNo=bookColumnNo;
	}
	public String getBookColumnNo(){
		return bookColumnNo;
	}
	public void setBookNo(int bookNo){
		this.bookNo=bookNo;
	}
	public int getBookNo(){
		return bookNo;
	}
	public void setBookAttr(String bookAttr){
		this.bookAttr=bookAttr;
	}
	public String getBookAttr(){
		return bookAttr;
	}
	
	
}
