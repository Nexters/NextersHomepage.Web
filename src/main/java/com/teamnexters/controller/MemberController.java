package com.teamnexters.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamnexters.dao.MemberDAO;
import com.teamnexters.dto.MemberDTO;

@Controller
public class MemberController {
	
	@Autowired
	MemberDAO memDao;
	MemberDTO memDto;
	
	@RequestMapping("/api/member.do")
	public @ResponseBody ArrayList<MemberDTO> getMemberList(Model model){
		
		Map<String, String> mapMemberReqData = new HashMap<String, String>();

		@SuppressWarnings("unchecked")
		ArrayList<MemberDTO> memberList = (ArrayList<MemberDTO>) memDao.getMemberList(mapMemberReqData);
		
		return  memberList;
	}
	
	@RequestMapping("/api/user/member.do")
	public @ResponseBody ArrayList<MemberDTO> getUserMemberList(Model model){
		
		Map<String, String> mapMemberReqData = new HashMap<String, String>();

		@SuppressWarnings("unchecked")
		ArrayList<MemberDTO> memberList = (ArrayList<MemberDTO>) memDao.getMemberList(mapMemberReqData);
		
		return  memberList;
	}
	
	@RequestMapping("userTag.do")
	public @ResponseBody Map<String, Object> getUserTag(Model model,@RequestParam(value = "str", required = false) 
														String str){
		
		@SuppressWarnings("unchecked")
		ArrayList<MemberDTO> memberList=(ArrayList<MemberDTO>) memDao.searchUserTag(str);
		
		Map<String, Object> resData=new HashMap<String, Object>();
		resData.put("reslut", "success");
		resData.put("resData", memberList);
		
		
		
		return resData;
	}
}
