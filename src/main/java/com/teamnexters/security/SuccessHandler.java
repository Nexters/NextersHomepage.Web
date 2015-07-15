package com.teamnexters.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamnexters.util.JsonUtil;

public class SuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		ObjectMapper om = new ObjectMapper();
		Map<String, Object> mapRslt = new HashMap<String, Object>();
		mapRslt.put("userName", authentication.getName());
		String jsonStr = om.writeValueAsString(JsonUtil.putSuccessJsonContainer(mapRslt));
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonStr);

	}
	
	

}
