package com.teamnexters.dto;

import java.util.ArrayList;

public class BooksDTO {
	private int mngno;
	private int booksno;
	private String title;
	private int amount;
	private ArrayList<BooksValDTO> val;
	public ArrayList<BooksValDTO> getVal() {
		return val;
	}
	public void setVal(ArrayList<BooksValDTO> val) {
		this.val = val;
	}
	public int getMngno() {
		return mngno;
	}
	public void setMngno(int mngno) {
		this.mngno = mngno;
	}
	public int getBooksno() {
		return booksno;
	}
	public void setBooksno(int booksno) {
		this.booksno = booksno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}
