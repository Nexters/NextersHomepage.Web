package com.teamnexters.dao;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BoardDAO {

	protected Log log = LogFactory.getLog(BooksDAO.class);
	
	@Autowired
	private SqlSession sqlsession;
	public void boardAdd(Object param){
		sqlsession.insert("boardAdd",param);
	}
	
	public Object boardList(){
		return sqlsession.selectList("boardList");
	}
	
	public void boardDelete(Object param){
		sqlsession.delete("boardDelete",param);
	}
	
	public Object getUploadPath(Object param){
		return sqlsession.selectOne("getUploadPath",param);
	}
}
