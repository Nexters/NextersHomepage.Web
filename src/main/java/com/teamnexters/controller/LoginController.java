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
import com.teamnexters.dto.MemberDTO;
import com.teamnexters.util.JsonUtil;;

@Controller
public class LoginController {
	
	@Autowired
	MemberDAO memDao;
	MemberDTO memDto;
	
	@RequestMapping("/needlogin.do")
	public @ResponseBody Map<String, Object> showNeedLogin(Model model){
		return JsonUtil.putFailJsonContainer("LoginControllerERR0001", "접근이 거부되었습니다.");
	}
	
	@RequestMapping("/expiredSession.do")
	public @ResponseBody Map<String, Object> showSessionExpired(Model model){
		return JsonUtil.putFailJsonContainer("LoginControllerERR0003", "세션이 파괴되었습니다.(중복로그인 불가능)");
	}
	
	
	@RequestMapping("/api/main/loginUser.do")
	public @ResponseBody Map<String, Object> getUserInfor() {
		Map<String, Object> mapRsltData = new HashMap<String, Object>();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String strUserId = auth.getName();
		if(!auth.isAuthenticated() || "anonymousUser".equals(strUserId)) {
			mapRsltData = JsonUtil.putFailJsonContainer("LoginControllerERR0002", "로그인하시길 바랍니다.");
		} else {
			Map<String, Object> mapUserData = new HashMap<String, Object>();
			memDto = (MemberDTO) memDao.searchByUserName(strUserId);
			mapUserData.put("userName", memDto.getUserNm());
			mapUserData.put("userNo", memDto.getUserNo());
			mapUserData.put("userId", memDto.getUserId());
			mapUserData.put("userStatus", memDto.getUserStatus());
			mapUserData.put("userRole", memDto.getUserRole());
			mapRsltData = JsonUtil.putSuccessJsonContainer(mapUserData);
		}
		return mapRsltData;
	}
}
