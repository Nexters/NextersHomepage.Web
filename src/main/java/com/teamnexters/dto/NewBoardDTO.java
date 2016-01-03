package com.teamnexters.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class NewBoardDTO {
	private int no;
	private String writer;
	private String title; 
	private String contetns;
	private Date insdate;
	private int view;
	private String password;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContetns() {
		return contetns;
	}
	public void setContetns(String contetns) {
		this.contetns = contetns;
	}
	public Date getInsdate() {
		return insdate;
	}
	public void setInsdate(Date insdate) {
		this.insdate = insdate;
	}
	public int getView() {
		return view;
	}
	public void setView(int view) {
		this.view = view;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
