package com.teamnexters.dao;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserPushTransMessageDAO {

	protected Log log = LogFactory.getLog(MemberDAO.class);
    
    @Autowired
    private SqlSession sqlsession;
    
    public void insertTransMessage(Object params){
    	sqlsession.insert("insertTransMessage",params);
    }
}
