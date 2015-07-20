package com.teamnexters.mail;

import javax.mail.MessagingException;

public interface SendMailService {
    public void sendEmail(String email) throws MessagingException;
}
