package com.teamnexters.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.teamnexters.dto.MemberDTO;

@Service
public class ClientSenderImpl implements ClientSender {

    @Autowired
    private JmsTemplate jmsTemplate;
    
    @Override
    public void sendInfo(final MemberDTO member) {
       
        jmsTemplate.convertAndSend(member);
    }
}