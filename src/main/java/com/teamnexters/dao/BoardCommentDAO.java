package com.teamnexters.dao;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BoardCommentDAO {

protected Log log = LogFactory.getLog(BooksDAO.class);
	
	@Autowired
	private SqlSession sqlsession;
	
	public void addPostComment(Object param){
		sqlsession.insert("addPostComment",param);
	}
	public Object getCommentList(Object param){
		return sqlsession.selectList("getCommentList",param);
	}
	
	public void commentRemove(Object param){
		sqlsession.delete("commentRemove",param);
	}
}

