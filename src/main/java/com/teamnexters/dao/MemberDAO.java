package com.teamnexters.dao;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.teamnexters.mapper.MemberMapper;

@Component
public class MemberDAO implements MemberMapper {
    protected Log log = LogFactory.getLog(MemberDAO.class);
     
    @Autowired
    private SqlSession sqlsession;
     
    public Object getMemberList(Object params) {
    	return sqlsession.selectList("getMemberList", params);
    }
    
    public Object searchByUserName(String strUserName){
    	return sqlsession.selectOne("searchByUserName", strUserName);
    }
    
    public Object searchUserTag(String str){
    	return sqlsession.selectList("userTag",str);
    }
    
	public Object getMemberListByGener(String strGener) {
		return sqlsession.selectList("getMemberListByGener",strGener);
	}
	
	public Object getMemberAttrList() {
		return sqlsession.selectList("getMemberInfoAttr");
	}
	
	public Object insertUser(Object tmp){
		return sqlsession.insert("insertUser", tmp);
	}
}

