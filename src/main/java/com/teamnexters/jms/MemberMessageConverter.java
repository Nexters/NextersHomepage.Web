package com.teamnexters.jms;


import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import com.teamnexters.dto.MemberDTO;


public class MemberMessageConverter implements MessageConverter{
	  
	@Autowired
	private MemberDTO member;
    @Override
    public Object fromMessage(Message message) throws JMSException,
            MessageConversionException {
        if(!(message instanceof MapMessage)){
            throw new MessageConversionException("Messaeg isn't a MapMessage");
        }
        
        MapMessage mapMessage = (MapMessage) message;
        
        member.setUserNm(mapMessage.getString("userNm"));
        member.setUserId(mapMessage.getString("userId"));
        return member;
    }

    @Override
    public Message toMessage(Object object, Session session) throws JMSException,
            MessageConversionException {
        if(!(object instanceof MemberDTO)){
            throw new MessageConversionException("Messaeg isn't a Member");
        }
        
        MemberDTO member = (MemberDTO) object;
        MapMessage message = session.createMapMessage();
        message.setString("userNm", member.getUserNm());
        message.setString("userId", member.getUserId());
        return message;
    } 

}  