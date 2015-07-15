package com.teamnexters.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamnexters.dao.MemberDAO;
import com.teamnexters.dto.MemberDTO;;

@Controller
public class LoginController {
	
	@Autowired
	MemberDAO memDao;
	MemberDTO memDto;
	
	@RequestMapping("/needlogin.do")
	public @ResponseBody Map<String, Object> getMemberList(Model model){
		
		Map<String, Object> resData = new HashMap<String, Object>();
		resData.put("result", "fail");
		resData.put("errMsg", "로그인이 필요합니다.");

		return resData;
	}
	
	@RequestMapping("/api/loginUser.do")
	public @ResponseBody Map<String, Object> getUserInfor() {
		Map<String, Object> mapRsltData = new HashMap<String, Object>();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String strUserId = auth.getName();
		if(!auth.isAuthenticated() || "anonymousUser".equals(strUserId)) {
			mapRsltData.put("result", "error");
			mapRsltData.put("_error_cd", "0001");
			mapRsltData.put("_error_msg", "로그인한 사용자가 아닙니다.");
		} else {
			Map<String, Object> mapUserData = new HashMap<String, Object>();
			memDto = (MemberDTO) memDao.searchByUserName(strUserId);
			mapUserData.put("userName", memDto.getUserNm());
			mapUserData.put("userNo", memDto.getUserNo());
			mapUserData.put("userId", memDto.getUserId());
			mapRsltData.put("result", "success");
			mapRsltData.put("userData", mapUserData);
		}
		return mapRsltData;
	}
}
