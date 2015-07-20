package com.teamnexters.mail;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.teamnexters.dto.*;

@Component
public class EmailSender implements SendMailService {
	@Autowired
	private JavaMailSender mailSender;
    
    
 
    @Override
    public void sendEmail(String email) throws MessagingException {
    	MimeMessage message = mailSender.createMimeMessage();
        message.setSubject("[공지] 회원 가입 안내");
        message.setText("회원 가입을 축하합니다.");
        
        message.setRecipient(RecipientType.TO, new InternetAddress(email));
        mailSender.send(message);
    } // sendEmail

}
