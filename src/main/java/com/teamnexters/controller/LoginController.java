package com.teamnexters.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamnexters.dao.MemberDAO;
import com.teamnexters.dto.MemberDTO;
import com.teamnexters.util.HttpUtil;
import com.teamnexters.util.JsonUtil;

@Controller
public class LoginController {
	
	@Autowired
	MemberDAO memDao;
	MemberDTO memDto;
	
	@RequestMapping("/needlogin.do")
	public @ResponseBody Map<String, Object> showNeedLogin(HttpServletRequest request, HttpServletResponse response){
		if(HttpUtil.isAjax(request)) {
			return JsonUtil.putFailJsonContainer("LoginControllerERR0001", "로그인이 필요합니다.");
		} else {
			try {
				response.sendRedirect(request.getContextPath()+"/");
			} catch(Exception e) {}
			return null;
		}
	}
	
	@RequestMapping("/expiredSession.do")
	public @ResponseBody Map<String, Object> showSessionExpired(HttpServletRequest request, HttpServletResponse response){
		if(HttpUtil.isAjax(request)) {
			return JsonUtil.putFailJsonContainer("LoginControllerERR0003", "세션이 파괴되었습니다.(중복로그인 불가능)");
		} else {
			try {
				response.sendRedirect(request.getContextPath()+"/expiredSession.html");
			} catch(Exception e) {}
			return null;
		}
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
