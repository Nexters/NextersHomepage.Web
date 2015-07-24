package com.teamnexters.dto;

public class UserPushMessageDTO {
	private int messageno;
	private String userno;
	private String senderboard;
	private int senderno;
	private String sendernm;
	private String message;
	private String readYN;
	private String transDate;
	private String readDate;
	
	public int getMessageno() {
		return messageno;
	}
	public void setMessageno(int messageno) {
		this.messageno = messageno;
	}
	public String getUserno() {
		return userno;
	}
	public void setUserno(String userno) {
		this.userno = userno;
	}
	public String getSenderboard() {
		return senderboard;
	}
	public void setSenderboard(String senderboard) {
		this.senderboard = senderboard;
	}
	public int getSenderno() {
		return senderno;
	}
	public void setSenderno(int senderno) {
		this.senderno = senderno;
	}
	public String getSendernm() {
		return sendernm;
	}
	public void setSendernm(String sendernm) {
		this.sendernm = sendernm;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getReadYN() {
		return readYN;
	}
	public void setReadYN(String readYN) {
		this.readYN = readYN;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getReadDate() {
		return readDate;
	}
	public void setReadDate(String readDate) {
		this.readDate = readDate;
	}
	
	
}
