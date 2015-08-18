package com.teamnexters.dto;

/**
 * @author Juhyun
 *
 */
public class BooksValDTO {
	private int mngno;
	private int booksno;
	private String userno;
	private int attval;
	
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
	public String getUserno() {
		return userno;
	}
	public void setUserno(String userno) {
		this.userno = userno;
	}
	public int getAttval() {
		return attval;
	}
	public void setAttval(int attval) {
		this.attval = attval;
	}
}
