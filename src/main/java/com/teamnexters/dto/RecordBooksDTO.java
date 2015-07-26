package com.teamnexters.dto;

import org.springframework.stereotype.Component;

@Component
public class RecordBooksDTO {

	private int bookNo;
	private String bookNm;
	
	public void setBookNo(int bookNo){
		this.bookNo=bookNo;
	}
	public int getBookNo(){
		return bookNo;
	}
	public void setBookNm(String bookNm){
		this.bookNm=bookNm;
	}
	public String getBookNm(){
		return bookNm;
	}
}
