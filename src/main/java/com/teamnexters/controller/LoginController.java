package com.teamnexters.controller;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;;

@Controller
public class LoginController {
	
	@RequestMapping("/needlogin.do")
	public @ResponseBody Map<String, Object> getMemberList(Model model){
		
		Map<String, Object> resData = new HashMap<String, Object>();
		resData.put("result", "fail");
		resData.put("errMsg", "로그인이 필요합니다.");

		return resData;
	}
}
