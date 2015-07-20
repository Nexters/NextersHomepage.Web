package com.teamnexters.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamnexters.util.HttpUtil;
import com.teamnexters.util.JsonUtil;

public class AccessdeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		if(HttpUtil.isAjax(request)) {
			ObjectMapper om = new ObjectMapper();
			String jsonStr = om.writeValueAsString(JsonUtil.putFailJsonContainer("AccessdeniedHandlerERR9999", accessDeniedException.getLocalizedMessage()));
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonStr);
		} else {
			try {
				response.sendRedirect(request.getContextPath()+"/");
			} catch(Exception e) {}
		}
		
	}
}
