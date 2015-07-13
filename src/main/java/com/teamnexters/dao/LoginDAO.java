package com.teamnexters.dao;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.teamnexters.mapper.MemberMapper;

@Component
public class LoginDAO implements MemberMapper {
    protected Log log = LogFactory.getLog(LoginDAO.class);

    private SqlSession sqlsession;
    
    public void setSqlSession(SqlSession sqlSession) {
        this.sqlsession = sqlSession;
    }
    
    public Object searchMember(Object params){
    	return sqlsession.selectOne("searchMember", params);
    }
    
    public Object searchByUserName(String strUserName){
    	System.out.println("strUserName::"+strUserName);
    	System.out.println("sqlSession::"+sqlsession);
    	return sqlsession.selectOne("searchByUserName", strUserName);
    }
}

