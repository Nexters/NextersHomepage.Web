package com.teamnexters.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender implements SendMailService {
	@Autowired
	private JavaMailSender mailSender;
    
    
 
    @Override
    public void sendEmail(String email) throws MessagingException {
    	/*MimeMessage message = mailSender.createMimeMessage();
        message.setSubject("[공지] 회원 가입 안내");
        message.setText("회원 가입을 축하합니다.");
        
        message.setRecipient(RecipientType.TO, new InternetAddress(email));
        mailSender.send(message);
        */
    	
    	String host="smtp.gmail.com";
    	String subject="[공지] 회원 가입 안내!!!!";
    	String fromName="김성일";
    	String from="";
    	String mailTo=email;
    	String password="";
    	String content="회원 가입을 축하합니다.";
    	
    	try{
    		Properties props=new Properties();
    		props.put("mail.smtp.starttls.enable", "true");
    		props.put("mail.transport.protocol", "smtp");
    		props.put("mail.smtp.host", host);
    		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    		props.put("mail.smtp.port", "465");
    		props.put("mail.smtp.user", from);
    		props.put("mail.smtp.auth", "true");
    		
    		Session mailSession=Session.getInstance(props,new javax.mail.Authenticator(){
    			protected PasswordAuthentication getPasswordAuthentication(){
    				return new PasswordAuthentication(from,password);
    			}
    		});
    		Message message=new MimeMessage(mailSession);
    		message.setFrom(new InternetAddress(from,MimeUtility.encodeText(fromName,"UTF-8","B")));
    		
    		InternetAddress[] address={new InternetAddress(mailTo)};
    		message.setRecipients(Message.RecipientType.TO, address);
    		message.setSubject(subject);
    		message.setSentDate(new java.util.Date());
    		message.setContent(content,"text/html;charset=utf-8");
    		
    		Transport.send(message);
    	} catch(MessagingException e){
    		e.printStackTrace();
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    } // sendEmail

}
