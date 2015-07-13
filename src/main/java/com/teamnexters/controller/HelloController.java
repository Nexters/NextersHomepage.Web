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
public class HelloController {
	
	@Autowired
	MemberDAO memDao;
	
	@RequestMapping("/hello.do")
	public @ResponseBody List<MemberDTO> hello(Model model){

		Map<String, String> mapMemberReqData = new HashMap<String, String>();
		mapMemberReqData.put("userid", "admin@teamnexters.com");
		
		List<MemberDTO> rsltListData = memDao.selectList("selectByLoginId",mapMemberReqData);
		
		return rsltListData;
	}
}
