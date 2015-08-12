package com.teamnexters.dao;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BoardInfoDAO {

protected Log log = LogFactory.getLog(BooksDAO.class);
	
	@Autowired
	private SqlSession sqlsession;
	
	public void postInsert(Object param){
		sqlsession.insert("postInsert",param);
	}
	
	public Object postList(Object param){
		return sqlsession.selectList("postList",param);
	}
	public Object getPost(Object param){
		return sqlsession.selectOne("getPost",param);
	}
	public void increasePostHits(Object param){
		sqlsession.update("increasePostHits",param);
	}
}
