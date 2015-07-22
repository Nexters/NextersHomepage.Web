package com.teamnexters.mail;

import javax.mail.MessagingException;

public interface SendMailService {
    

	public void sendEmail(String subject, String content, String email)
			throws MessagingException;
	public void sendContact(String name,String content,String email) throws MessagingException;
}
