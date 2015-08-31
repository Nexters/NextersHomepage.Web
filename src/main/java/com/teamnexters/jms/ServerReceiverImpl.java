package com.teamnexters.jms;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.teamnexters.dto.MemberDTO;

@Service
public class ServerReceiverImpl implements ServerReceiver {
    @Autowired
    private JmsTemplate jmsTemplate;
       
    public MemberDTO receive(){
        return (MemberDTO) jmsTemplate.receiveAndConvert();
    }
}