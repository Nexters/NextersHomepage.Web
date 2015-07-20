package com.teamnexters.mail;

import javax.mail.MessagingException;

import com.teamnexters.dto.*;

public interface SendMailService {
    public void sendEmail(String email) throws MessagingException;
}
