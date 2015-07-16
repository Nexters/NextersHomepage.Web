package com.teamnexters.controller;

import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamnexters.dao.MemberDAO;
import com.teamnexters.dao.MemberInfoDAO;
import com.teamnexters.dto.MemberDTO;
import com.teamnexters.dto.MemberInfoDTO;
import com.teamnexters.util.JsonUtil;

@Controller
public class MemberController {
	
	private static final Logger logger =  Logger.getLogger(MemberController.class);
	
	@Autowired
	MemberDAO memDao;
	@Autowired
	MemberInfoDAO memInfoDao;
		
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/api/main/memberList.do")
	public @ResponseBody Map<String, Object> getMemberListByGener(@RequestParam(value="gener") String strGener ){
		Map<String, Object> mapMemberReqData = new HashMap<String, Object>();
		ArrayList<Map> memberList = (ArrayList<Map>) memDao.getMemberListByGener(strGener);

		ArrayList<String> memberArrayList = new ArrayList<String>();
		
		for (int i=0; i<memberList.size(); i++) {
			memberList.get(i).put("userAddInfo", new ArrayList<Map>());
			memberArrayList.add(memberList.get(i).get("userNo").toString());
		}
		logger.debug("UserList::"+memberArrayList.toString());
		
		ArrayList<Map> memberInfoValue = (ArrayList<Map>)memInfoDao.getMemberInfoValue(new HashMap<String, Object>().put("userNoArray", memberArrayList));
		for(int i=0; i<memberInfoValue.size(); i++) {
			String strInfoUserNo = memberInfoValue.get(i).get("userNo").toString();
			for(int j=0; j<memberList.size(); j++){
				if(memberList.get(j).get("userNo").equals(strInfoUserNo)) {
					((ArrayList<Map>)memberList.get(j).get("userAddInfo")).add(memberInfoValue.get(i));
				}
			}
		}
		
		mapMemberReqData.put("userList", memberList);
		return  JsonUtil.putSuccessJsonContainer(mapMemberReqData);
	}
	
	@RequestMapping("/api/main/memberAttr.do")
	public @ResponseBody Map<String, Object> getMemberAttr(){
		Map<String, Object> mapMemberReqData = new HashMap<String, Object>();
		
		@SuppressWarnings("unchecked")
		ArrayList<MemberInfoDTO> memberInfoAttrList = (ArrayList<MemberInfoDTO>) memDao.getMemberAttrList();
		
		mapMemberReqData.put("attrList", memberInfoAttrList);
		return  JsonUtil.putSuccessJsonContainer(mapMemberReqData);
	}
	
	@RequestMapping("/api/user/member.do")
	public @ResponseBody  Map<String, Object> getUserMemberList(Model model){
		Map<String, Object> mapMemberReqData = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		ArrayList<MemberDTO> memberList = (ArrayList<MemberDTO>) memDao.getMemberList(mapMemberReqData);
		mapMemberReqData.put("userList", memberList);
		return  JsonUtil.putSuccessJsonContainer(mapMemberReqData);
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
