package com.teamnexters.dao;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ProjectDAO {
	 protected Log log = LogFactory.getLog(LoginDAO.class);

	 	@Autowired
	    private SqlSession sqlSession;
	    
	    public void insertProject(Object param){
	    	sqlSession.insert("insertProject",param);
	    }
	    
	    public Object getProjectList(){
	    	return sqlSession.selectList("getProjectList");
	    }
	    
	    public void deleteProject(Object param){
	    	sqlSession.delete("deleteProject",param);
	    }
}
