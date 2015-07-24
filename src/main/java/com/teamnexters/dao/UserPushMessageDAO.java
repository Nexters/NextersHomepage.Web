package com.teamnexters.dao;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserPushMessageDAO {
	protected Log log = LogFactory.getLog(MemberDAO.class);

	@Autowired
	private SqlSession sqlsession;
	
	public void insertMessage(Object params){
		sqlsession.insert("insertMessage",params);
	}

}
