package com.teamnexters.dao;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AchieveMemberDAO {

protected Log log = LogFactory.getLog(BooksDAO.class);
	
	@Autowired
	private SqlSession sqlsession;
	
	public void achieveMemberAdd(Object param){
		sqlsession.insert("achieveMemberAdd",param);
	}
	
	public Object achieveMemberList(Object param){
		return sqlsession.selectList("achieveMemberList",param);
	}
	
	public void achieveMemberDelete(Object param){
		sqlsession.delete("achieveMemberDelete",param);
	}
	
	public void achieveMemberUpdate(Object param){
		sqlsession.insert("achieveMemberUpdate",param);
	}
}
