package com.teamnexters.dao;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.teamnexters.mapper.MemberInfoMapper;

@Component
public class MemberInfoDAO implements MemberInfoMapper {
    protected Log log = LogFactory.getLog(MemberInfoDAO.class);
     
    @Autowired
    private SqlSession sqlsession;
     
    public Object getMemberInfoAttr() {
    	return sqlsession.selectList("getMemberInfoAttr");
    }
    
    public Object getMemberInfoValue(String strUserId) {
    	return sqlsession.selectList("getMemberInfoValue", strUserId);
    }
    
}

