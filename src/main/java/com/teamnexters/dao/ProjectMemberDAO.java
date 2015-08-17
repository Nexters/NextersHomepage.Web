package com.teamnexters.dao;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectMemberDAO {

protected Log log = LogFactory.getLog(BooksDAO.class);
	
	@Autowired
	private SqlSession sqlsession;
	
	public void projectMemberAdd(Object param){
		sqlsession.insert("projectMemberAdd",param);
	}
	
	public Object projectMemberList(Object param){
		return sqlsession.selectList("projectMemberList",param);
	}
	
	public void projectMemberDelete(Object param){
		sqlsession.delete("projectMemberDelete",param);
	}
	
	public void projectMemberUpdate(Object param){
		sqlsession.insert("projectMemberUpdate",param);
	}
}
