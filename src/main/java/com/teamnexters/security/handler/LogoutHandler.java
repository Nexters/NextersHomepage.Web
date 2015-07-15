package com.teamnexters.security.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamnexters.util.JsonUtil;

public class LogoutHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		ObjectMapper om = new ObjectMapper();
		String jsonStr;
		
		if(authentication==null) {
			jsonStr = om.writeValueAsString(JsonUtil.putFailJsonContainer("LogoutHandlerERR0001", "로그인 상태가 아닙니다."));
		} else {
			Map<String, Object> mapRslt = new HashMap<String, Object>();
			mapRslt.put("userName", authentication.getName());
			jsonStr = om.writeValueAsString(JsonUtil.putSuccessJsonContainer(mapRslt));
		}
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonStr);
		
	}


	
	

}
