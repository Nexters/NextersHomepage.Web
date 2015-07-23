package com.teamnexters.dao;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.teamnexters.dto.MemberAuthDTO;
import com.teamnexters.mapper.MemberAuthMapper;

@Component
public class MemberAuthDAO implements MemberAuthMapper {
    protected Log log = LogFactory.getLog(MemberAuthDAO.class);
     
    @Autowired
    private SqlSession sqlsession;
     
    public Object getMemberAuth(String key){
    	return sqlsession.selectOne("getMemberAuth", key);
    }
    
    public void setMemberAuthValid(Object paramObj){
		sqlsession.insert("setMemberAuthValid",paramObj);
	}
    
	public void insertMemberAuth(MemberAuthDTO memberAuthDto){
		sqlsession.insert("insertMemberAuth",memberAuthDto);
	}
}
