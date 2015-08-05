package com.teamnexters.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint{

	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException{
		String[] path=request.getRequestURI().split("/");
		String plusPath="";
		if(path[path.length-2].equals("admin")){
			plusPath="/admin";
		}
		response.sendRedirect(request.getContextPath()+plusPath);
		
	}
}
