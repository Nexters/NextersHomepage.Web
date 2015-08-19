package com.teamnexters.dao;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MngDAO {

	protected Log log = LogFactory.getLog(BooksDAO.class);
	
	@Autowired
	private SqlSession sqlsession;
	
	public Object getManageList(){
		return sqlsession.selectList("getManageList");
	}
	
	public Object getManageCnt(String strMngDate){
		return sqlsession.selectOne("getManageCnt", strMngDate);
	}
	
	public int insertManager(Object objParam){
		return sqlsession.insert("insertManager", objParam);
	}
	
	public Object getAttendanceListByMngNo(int intMngNo) {
		return sqlsession.selectList("getAttendanceListByMngNo", intMngNo);
	}
	
	public int deleteAttendanceByMngNoAndUserNo(Object objParam) {
		return sqlsession.delete("deleteAttendance", objParam);
	}
	
	public int insertAttendance(Object objParam){
		return sqlsession.insert("insertAttendance", objParam);
	}
	
	public int insertBooks(Object objParam){
		return sqlsession.insert("insertBooks", objParam);
	}
	
	public Object getBooksList(int mngno){
		return sqlsession.selectList("getBooksList", mngno);
	}
	
	public int insertBooksVal(Object objParam) {
		return sqlsession.insert("insertBooksVal", objParam);
	}
	
	public int deleteBooksVal(Object objParam) {
		return sqlsession.insert("deleteBooksVal", objParam);
	}
	
	public Object getBooksValByBooksNoAndMngNo(Object paramObj){
		return sqlsession.selectList("getBooksValByBooksNo", paramObj);
	}
}
