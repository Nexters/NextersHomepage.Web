package com.teamnexters.dao;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthMailDAO {

	 protected Log log = LogFactory.getLog(LoginDAO.class);

	 @Autowired
	 private SqlSession sqlSession;
	 	
	 public void deleteAuth(Object param){
		 sqlSession.delete("deleteAuth",param);
	 }
	 public void addAuth(Object param){
		 sqlSession.insert("addAuth",param);
	 }
	 public Object getAuthMailUser(Object param){
		 return sqlSession.selectOne("getAuthMailUser",param);
	 }
	 
}
