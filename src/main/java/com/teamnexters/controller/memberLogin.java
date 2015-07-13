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
	MemberDTO memDto;
	
	@RequestMapping("api/login.do")
	public @ResponseBody MemberDTO Login(Model model){
		
		Map<String, String> mapMemberReqData = new HashMap<String, String>();
		mapMemberReqData.put("userid", "admin@teamnexters.com");
		mapMemberReqData.put("userpw", "qwer1234");
		
		memDto = (MemberDTO) memDao.searchMember(mapMemberReqData);
		
		if(memDto == null) {
			memDto.setErrAct("9999");
			memDto.setErrCd("0001");
			memDto.setErrMsg("아이디가 존재하지 않거나 비밀번호가 틀렸습니다.");
		}
		
		return memDto;
	}
}
