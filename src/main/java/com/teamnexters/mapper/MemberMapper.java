package com.teamnexters.mapper;

public interface MemberMapper { 
	Object searchByUserName(String strUserName);
	Object getMemberListByGener(String strGener);
	Object getGenerList();
	
} 
