package com.teamnexters.dao;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

@Component
public class ProjectDAO {
	 protected Log log = LogFactory.getLog(LoginDAO.class);

	    private SqlSession SqlSession;
}
