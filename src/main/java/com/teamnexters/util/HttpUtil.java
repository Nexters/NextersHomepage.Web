package com.teamnexters.util;

import javax.servlet.http.HttpServletRequest;

public class HttpUtil {
	public static boolean isAjax(HttpServletRequest request) {
		if("XMLHttpRequest".equals(request.getHeader("X-Requested-With")))
			return true;
		else
			return false;
 	}
}
