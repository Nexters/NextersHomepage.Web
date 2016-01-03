package com.teamnexters.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewBoardDAO {
	@Autowired
	private SqlSession sqlsession;
	
	public Object getBooksList(Object requestParam){
		return sqlsession.selectList("getNewBoardList", requestParam);
	}
	
	public Object getBoardContents(String no) {
		return sqlsession.selectOne("getNewBoardContent", no);
	}
	
	public int insertBoard(Object requestParam) {
		return sqlsession.insert("insertBoard", requestParam);
	}
	
	public int selectBoardTotCnt() {
		return sqlsession.selectOne("getNewBoardContentCount");
	}
}
