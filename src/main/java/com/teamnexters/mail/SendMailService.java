package com.teamnexters.mail;

import javax.mail.MessagingException;

import com.teamnexters.dto.MemberDTO;

public interface SendMailService {
    

	public void sendEmail(String subject, String content, MemberDTO memDto)
			throws MessagingException;
	public void sendContact(String name,String subject,String content,String email) throws MessagingException;
}