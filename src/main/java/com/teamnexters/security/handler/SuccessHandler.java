package com.teamnexters.security.handler;

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
		mapRslt.put("userRoles", isUserRoles(authentication.getAuthorities().toArray()));
		String jsonStr = om.writeValueAsString(JsonUtil.putSuccessJsonContainer(mapRslt));
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonStr);

	}
	
	private int isUserRoles(Object[] arrayUser) {
		int intRole = 2;
		for(int i=0; i<arrayUser.length; i++) {
			String strUserRole = arrayUser[i].toString();
			if("ROLE_ADMIN".equals(strUserRole))
				intRole--;
			else if("ROLE_SUPERADMIN".equals(strUserRole))
				intRole--;
		}
		return intRole;
		
	}
	
	

}
