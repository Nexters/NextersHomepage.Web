package com.teamnexters.dao;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AchieveDAO {

	 protected Log log = LogFactory.getLog(LoginDAO.class);

	 	@Autowired
	    private SqlSession sqlSession;
	    
	    public void insertAchieve(Object param){
	    	sqlSession.insert("insertAchieve",param);
	    }
	    
	    public Object getAchieveList(){
	    	return sqlSession.selectList("getAchieveList");
	    }
	    
	    public void deleteAchieve(Object param){
	    	sqlSession.delete("deleteAchieve",param);
	    }
	    
	    public void updateAchieve(Object param){
	    	sqlSession.update("updateAchieve",param);
	    }
}
