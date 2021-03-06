package com.teamnexters.dao;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.teamnexters.mapper.MemberMapper;

@Component
public class MemberDAO implements MemberMapper {
	protected Log log = LogFactory.getLog(MemberDAO.class);

	@Autowired
	private SqlSession sqlsession;

	public Object getMemberList(Object params) {
		return sqlsession.selectList("getMemberList", params);
	}

	public Object searchByUserName(String strUserName){
		return sqlsession.selectOne("searchByUserName", strUserName);
	}

	public Object searchUserTag(String str){
		return sqlsession.selectList("userTag",str);
	}

	public Object getMemberListByGener(Object paramObj) {
		return sqlsession.selectList("getMemberListByGener",paramObj);
	}

	public Object getMemberAdminListByGener(Object paramObj) {
		return sqlsession.selectList("getMemberAdminListByGener",paramObj);
	}

	public Object getMemberAttrList() {
		return sqlsession.selectList("getMemberInfoAttr");
	}

	public Object getGenerList() {
		return sqlsession.selectList("getGenerList");
	}

	public Object insertUser(Object tmp){
		return sqlsession.insert("insertUser", tmp);
	}
	public Object getMemberDetailInfo(Object userNo){
		return sqlsession.selectOne("getMemberDetailInfo",userNo);
	}
	public Object updateMember(Object memberInfo){
		return sqlsession.update("updateMember",memberInfo);
	}
	public Object deleteMember(Object userNo){
		return sqlsession.delete("deleteMember",userNo);
	}

	public Boolean memberExist(Object tmp){
		return sqlsession.selectOne("memberExist",tmp);
	}
	public Object insertNewUser(Object tmp){
		return sqlsession.insert("insertNewUser", tmp);
	}
	public void updateMail(Object param){
		sqlsession.update("updateMail",param);
	}
	public Object getAssosiateMemberList(){
		return sqlsession.selectList("getAssosiateMemberList");
	}
	public Object getMemberCountDeveloper(Object param){
		return sqlsession.selectOne("getMemberCountDeveloper",param);
	}
	public Object getMemberCountDesigner(Object param){
		return sqlsession.selectOne("getMemberCountDesigner",param);
	}
	public Object getMemberCountEtc(Object param){
		return sqlsession.selectOne("getMemberCountEtc",param);
	}
	public Object getMemberListForBooks(){
		return sqlsession.selectList("getMemberListForBooks");
	}
	public void insertPassword(Object param){
		sqlsession.insert("insertPassword",param);
	}
}

