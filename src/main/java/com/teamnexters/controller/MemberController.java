package com.teamnexters.controller;

import java.util.*;

import javax.mail.MessagingException;

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
import com.teamnexters.mail.EmailSender;
import com.teamnexters.util.JsonUtil;

@Controller
public class MemberController {
	
	private static final Logger logger =  Logger.getLogger(MemberController.class);
	
	@Autowired
	MemberDAO memDao;
	@Autowired
	MemberInfoDAO memInfoDao;
	@Autowired
	private EmailSender emailSender;
		
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/api/main/memberList.do")
	public @ResponseBody Map<String, Object> getMemberListByGener(@RequestParam(value="gener") String strGener ){
		Map<String, Object> mapMemberReqData = new HashMap<String, Object>();
		Map<String, Object> mapMemberSqlReqData = new HashMap<String, Object>();
		
		mapMemberSqlReqData.put("gener", strGener);
		ArrayList<Map> memberList = (ArrayList<Map>) memDao.getMemberListByGener(mapMemberSqlReqData);

		ArrayList<String> memberArrayList = new ArrayList<String>();
		
		for (int i=0; i<memberList.size(); i++) {
			memberList.get(i).put("userAddInfo", new ArrayList<Map>());
			memberArrayList.add(memberList.get(i).get("userNo").toString());
		}
		
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/api/admin/memberList.do")
	public @ResponseBody Map<String, Object> getMemberAdminListByGener(@RequestParam(value="gener") String strGener ){
		Map<String, Object> mapMemberReqData = new HashMap<String, Object>();
		Map<String, Object> mapMemberSqlReqData = new HashMap<String, Object>();
		
		mapMemberSqlReqData.put("gener", strGener);
		ArrayList<Map> memberList = (ArrayList<Map>) memDao.getMemberAdminListByGener(mapMemberSqlReqData);

		ArrayList<String> memberArrayList = new ArrayList<String>();
		
		for (int i=0; i<memberList.size(); i++) {
			memberList.get(i).put("userAddInfo", new ArrayList<Map>());
			memberArrayList.add(memberList.get(i).get("userNo").toString());
		}
		
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
	
	@RequestMapping("/api/main/memberGenerList.do")
	public @ResponseBody Map<String, Object> getGenerList(){
		Map<String, Object> mapMemberReqData = new HashMap<String, Object>();
		
		@SuppressWarnings("unchecked")
		ArrayList<Map<String, Object>> memberInfoAttrList = (ArrayList<Map<String, Object>>) memDao.getGenerList();
		
		mapMemberReqData.put("generList", memberInfoAttrList);
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
	public @ResponseBody Map<String, Object> getUserTag(@RequestParam(value = "str", required = false) String str){
		
		@SuppressWarnings("unchecked")
		ArrayList<MemberDTO> memberList=(ArrayList<MemberDTO>) memDao.searchUserTag(str);
		Map<String, Object> mapMemberReqData = new HashMap<String, Object>();
		mapMemberReqData.put("tagList", memberList);
		
		
		
		
		return JsonUtil.putSuccessJsonContainer(mapMemberReqData);
	}
	
	@RequestMapping("/memberAdd.do")
	public @ResponseBody Map<String,Object> memberAdd(@RequestParam(value = "grade", required= false) String grade,
			@RequestParam(value = "position", required= false) String position,
			@RequestParam(value = "userId", required= false) String userId,
			@RequestParam(value = "userNm", required= false) String userNm,
			@RequestParam(value = "userCellNum", required= false) String userCellNum) throws MessagingException{
		Map<String, Object> mapReqParam = new HashMap<String, Object>();
		
		String tmp="N";
		tmp+=grade;
		/*if(position.equals("개발자")){
			tmp+="T";
		}
		else{
			tmp+="D";
		} */
		
		mapReqParam.put("userNo","N00T");
		
		int insertSuc=(Integer)memDao.insertUser(mapReqParam);
		
		Map<String, Object> mapMemberReqData =new HashMap<String, Object>();
		
		mapMemberReqData.put("insertSuc", insertSuc);
		String subject="[공지]회원가입 안내";
		String content="가입을 축하합니다";
		emailSender.sendEmail(subject,content,"ksi4687@nate.com");
		
		return JsonUtil.putSuccessJsonContainer(mapMemberReqData);
		
	}
	
	@RequestMapping("/memberDetail.do")
	public @ResponseBody Map<String, Object> memberDetail(@RequestParam(value ="userNo", required=false) String userNo){
		
		ArrayList<MemberInfoDTO> list=(ArrayList<MemberInfoDTO>)memInfoDao.getMemberInfoAttr();
		ArrayList<String> arrayList=new ArrayList<String>();
		for(int i=0;i<list.size();i++){
			
			arrayList.add(list.get(i).getAttr());
		}
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("list", arrayList);
		map.put("userNo","N00T001");
		Map<String, Object> mapMemberReqData =new HashMap<String,Object>();
		mapMemberReqData.put("memberData", memDao.getMemberDetailInfo(map));
		return JsonUtil.putSuccessJsonContainer(mapMemberReqData);
	}
	
	@RequestMapping("/memberModify.do")
	public @ResponseBody Map<String, Object> memberModify(@RequestParam Map<String,String> params){
		
		if(!memInfoDao.memberInfoValueExist(params)){
			memInfoDao.insertInfoValue(params.get("userNo"));
		}
		ArrayList<MemberInfoDTO> list=(ArrayList<MemberInfoDTO>)memInfoDao.getMemberInfoAttr();
		ArrayList<String> arrayList=new ArrayList<String>();
		for(int i=0;i<list.size();i++){
			arrayList.add(list.get(i).getAttr());
		}
		
		Map<String, Object> mapReqParam =new HashMap<String, Object>();
		mapReqParam.put("list", list);
		mapReqParam.put("userNo", params.get("userNo"));
		mapReqParam.put("userNm", params.get("userNm"));
		Map<String, Object> mapMemberReqData=new HashMap<String, Object>();
		mapMemberReqData.put("updateSuc", memDao.updateMember(mapReqParam));

		//
		for(int i=0;i<list.size();i++){
			String strAttr = list.get(i).getAttr();
			String strAttrValue = params.get(strAttr);
			
			if(strAttrValue != null) {
				HashMap<String, Object> paramReqData = new HashMap<String, Object>();
				paramReqData.put("attr", strAttr);
				paramReqData.put("value", strAttrValue);
				paramReqData.put("userNo", params.get("userNo"));
				memInfoDao.updateMemberInfo(paramReqData);
			}
		}
		
		return JsonUtil.putSuccessJsonContainer(mapMemberReqData);
	}
	
	@RequestMapping("/memberRemove.do")
	public @ResponseBody Map<String, Object> memberDelete(@RequestParam(value="userNo", required=false) String userNo){
		Map<String, Object> mapReqParam=new HashMap<String, Object>();
		mapReqParam.put("userNo", userNo);
		Map<String, Object> mapMemberReqData=new HashMap<String, Object>();
		mapMemberReqData.put("deleteSuc",memDao.deleteMember(mapReqParam));
		
		return JsonUtil.putSuccessJsonContainer(mapMemberReqData);
	}
	
	@RequestMapping("/contact.do")
	public @ResponseBody Map<String, Object> contactMailSubmit(@RequestParam Map<String,String> params) throws MessagingException{
		String name=params.get("userName");
		String email=params.get("userEmail");
		String comment=params.get("userComment");
		
		emailSender.sendContact(name, comment, email);
		
		return JsonUtil.putSuccessJsonContainer(null);
	}
}
