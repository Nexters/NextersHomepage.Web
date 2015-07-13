package com.teamnexters.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamnexters.dao.MemberDAO;
import com.teamnexters.dto.MemberDTO;

@Controller
public class memberLogin {
	
	@Autowired
	MemberDAO memDao;
	
	@RequestMapping("/api/Login.do")
	public @ResponseBody MemberDTO Login(Model model){
		
		Map<String, String> mapMemberReqData = new HashMap<String, String>();
		mapMemberReqData.put("userid", "admin@teamnexters.com");
		mapMemberReqData.put("userpw", "admin@teamnexters.com");
		
		MemberDTO rsltListData = (MemberDTO) memDao.searchMember(mapMemberReqData);
		
		
		return rsltListData;
	}
}
