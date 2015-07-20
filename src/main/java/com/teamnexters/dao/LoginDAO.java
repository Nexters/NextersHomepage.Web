package com.teamnexters.dao;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.teamnexters.mapper.LoginMapper;

/**
 * To use spring framework security service 
 * @author limjuhyun
 *
 */
@Component
public class LoginDAO implements LoginMapper {
    protected Log log = LogFactory.getLog(LoginDAO.class);

    private SqlSession SqlSession;
    
    public void setSqlSession(SqlSession sqlSession) {
		SqlSession = sqlSession;
	}
    
    /**
     * Search User Account Information by using ID
     * @param User ID
     * @return Member DTO Class
     */
    public Object searchByUserName(String strUserName){
    	return SqlSession.selectOne("searchByUserName", strUserName);
    }
    
    
}

