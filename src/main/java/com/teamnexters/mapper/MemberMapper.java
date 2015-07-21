package com.teamnexters.mapper;

public interface MemberMapper { 
	Object searchByUserName(String strUserName);
	Object getMemberListByGener(Object paramObj);
	Object getGenerList();
	
} 
