package com.teamnexters.mapper;

import java.util.ArrayList; 

import org.apache.ibatis.annotations.Param; 

import com.teamnexters.dto.MemberDTO;

public interface MemberMapper { 
	MemberDTO selectByLoginId(String loginId);
	MemberDTO selectById(int id); 
	ArrayList<MemberDTO> selectByUserGroupId(int userGroupId); 
	ArrayList<MemberDTO> selectPage(@Param("currentPage") int currentPage,   
			@Param("pageSize") int pageSize,          
			@Param("ordered") int order,            
			@Param("srchType") int srchType,       
			@Param("srchText") String srchText,     
			@Param("category") int category);   
	int getRecordCount(@Param("srchType") int srchType,     
			@Param("srchText") String srchText,            
			@Param("category") int category);  
	void insert(MemberDTO user);   
	void updateInfo(MemberDTO user);  
	void updatePasswd(MemberDTO user); 
} 
