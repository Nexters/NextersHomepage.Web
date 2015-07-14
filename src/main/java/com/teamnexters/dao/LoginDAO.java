package com.teamnexters.dao;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.teamnexters.mapper.MemberMapper;

/**
 * To use spring framework security service 
 * @author limjuhyun
 *
 */
@Component
public class LoginDAO implements MemberMapper {
    protected Log log = LogFactory.getLog(LoginDAO.class);

    private SqlSession SqlSession;
    
    public void setSqlSession(SqlSession sqlSession) {
		SqlSession = sqlSession;
	}

	/**
     * Search User Account Information by using ID and password
     * @param User Account Information
     * @return Member DTO Class
     */
    public Object searchMember(Object params){
    	return SqlSession.selectOne("searchMember", params);
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

