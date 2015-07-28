package com.teamnexters.dao;

import java.util.ArrayList;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecordBooksValueDAO {
	protected Log log = LogFactory.getLog(MemberAuthDAO.class);

	@Autowired
	private SqlSession sqlsession;
	
	public void insertBooksValue(Object param){
		sqlsession.insert("insertBooksValue",param);
	}
	public Object getBooksValueList(){
		return sqlsession.selectList("getBooksValueList");
	}
	public void updateBooksValue(Object param){
		sqlsession.update("updateBooksValue",param);
	}
	public boolean bookValueExist(Object param){
		return sqlsession.selectOne("bookValueExist",param);
	}
	public void insertNewBooksValue(Object param){
		sqlsession.insert("insertNewBooksValue",param);
	}
	public void deleteNewBooksValue(Object param){
		sqlsession.delete("deleteNewBooksValue",param);
	}
	public Object getAttendenceCountList(Object param){
		return sqlsession.selectList("getAttendenceCountList",param);
	}
}
