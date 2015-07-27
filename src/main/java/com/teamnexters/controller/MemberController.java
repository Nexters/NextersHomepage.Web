package com.teamnexters.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamnexters.dao.MemberAuthDAO;
import com.teamnexters.dao.MemberDAO;
import com.teamnexters.dao.MemberInfoDAO;
import com.teamnexters.dao.RecordBooksValueDAO;
import com.teamnexters.dto.MemberAuthDTO;
import com.teamnexters.dto.MemberDTO;
import com.teamnexters.dto.MemberInfoDTO;
import com.teamnexters.mail.EmailSender;
import com.teamnexters.util.JsonUtil;

@Controller
public class MemberController {

	private static final Logger logger =  Logger.getLogger(MemberController.class);

	@Autowired
	MemberDTO memDto;
	@Autowired
	MemberDAO memDao;
	@Autowired
	MemberInfoDAO memInfoDao;
	@Autowired
	MemberAuthDAO memAuthDao;
	@Autowired
	private EmailSender emailSender;
	@Autowired
	RecordBooksValueDAO booksValueDao;

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

	@RequestMapping("api/admin/SmemberAdd.do")
	public @ResponseBody Map<String,Object> memberAdd(@RequestParam(value = "grade", required= false) String grade,
			@RequestParam(value = "position", required= false) String position,
			@RequestParam(value = "userId", required= false) String userId,
			@RequestParam(value = "userNm", required= false) String userNm,
			@RequestParam(value = "userCellNum", required= false) String userCellNum){
		Map<String, Object> mapReqParam = new HashMap<String, Object>();

		String tmp="N";
		tmp+=grade;
		if(position.equals("개발자")){
			tmp+="T";
		}
		else if(position.equals("디자이너")){
			tmp+="D";
		}
		else{
			tmp+="O";
		}
		mapReqParam.put("userId", userId);
		mapReqParam.put("userNm", userNm);
		mapReqParam.put("userCellNum", userCellNum);
		
		int insertSuc=0;
		
		if(!memDao.memberExist(tmp)){
			tmp=tmp+"001";
			
			mapReqParam.put("userNo", tmp);
			memDao.insertNewUser(mapReqParam);
		}
		
		else{
			mapReqParam.put("userNo",tmp);
			insertSuc=(Integer)memDao.insertUser(mapReqParam);
		}
		
		







		Map<String, Object> mapMemberReqData =new HashMap<String, Object>();

		mapMemberReqData.put("insertSuc", insertSuc);


		return JsonUtil.putSuccessJsonContainer(mapMemberReqData);

	}

	@RequestMapping("api/admin/memberDetail.do")
	public @ResponseBody Map<String, Object> memberDetail(@RequestParam(value ="userNo", required=false) String userNo){

		ArrayList<MemberInfoDTO> list=(ArrayList<MemberInfoDTO>)memInfoDao.getMemberInfoAttr();
		ArrayList<String> arrayList=new ArrayList<String>();
		for(int i=0;i<list.size();i++){

			arrayList.add(list.get(i).getAttr());
		}
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("list", arrayList);
		map.put("userNo",userNo);
		Map<String, Object> mapMemberReqData =new HashMap<String,Object>();
		mapMemberReqData.put("memberData", memDao.getMemberDetailInfo(map));
		return JsonUtil.putSuccessJsonContainer(mapMemberReqData);
	}

	@RequestMapping("api/admin/memberModify.do")
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
		mapReqParam.put("userCellNum", params.get("userCellNum"));
		Map<String, Object> mapMemberReqData=new HashMap<String, Object>();
		mapMemberReqData.put("updateSuc", memDao.updateMember(mapReqParam));

		//
		for(int i=0;i<list.size();i++){
			String strAttr = list.get(i).getAttr();
			String strAttrValue = params.get(strAttr);
			if(strAttrValue==null){
				strAttrValue="";
			}
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

	@RequestMapping("api/admin/memberRemove.do")
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
	@RequestMapping("api/admin/memberInfoAdd.do")
	public @ResponseBody Map<String, Object> insertMemberInfo(@RequestParam(value="attr") String attr,@RequestParam(value="desc") String desc){
		Map<String, String> params =new HashMap<String,String>(); 
		params.put("attr", attr);
		params.put("desc", desc);

		memInfoDao.insertInfo(params);

		return JsonUtil.putSuccessJsonContainer(null);
	}
	@RequestMapping("api/admin/memberInfoRemove.do")
	public @ResponseBody Map<String, Object> deleteMemberInfo(@RequestParam(value="desc") String desc){
		Map<String ,String> params=new HashMap<String,String>();
		params.put("desc", desc);
		memInfoDao.deleteInfo(params);

		return JsonUtil.putSuccessJsonContainer(null);
	}
	@RequestMapping("api/admin/activitySet.do")
	public @ResponseBody Map<String, Object> updateActivity(@RequestParam Map<String,String> params){
		
		memDao.updateActivity(params);
		String activityYN=params.get("activityYN");
		if(activityYN.equals("Y")){
			if(!booksValueDao.bookValueExist(params)){
				booksValueDao.insertNewBooksValue(params);
			}
		}
		return JsonUtil.putSuccessJsonContainer(null);
	}

	@RequestMapping("sendAuthEmail.do")
	public @ResponseBody Map<String, Object> emailSend(@RequestParam(value="userNo", required=false) String userNo,@RequestParam(value="userId", required=false) String userId ) throws MessagingException{

		memDto.setUserNo(userNo);
		memDto.setUserId(userId);
		String subject="회원가입 안내";
		String content="회원가입입니다 <br> ㅊㅋㅊㅋ";
		emailSender.sendEmail(subject, content, memDto);

		return JsonUtil.putSuccessJsonContainer(null);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("api/main/userAuth.do")
	public @ResponseBody Map<String, Object> authUserInfo(@RequestParam(value="key") String strKey) {
		MemberAuthDTO memAuthData = (MemberAuthDTO)memAuthDao.getMemberAuth(strKey);
		Map<String, Object> mapRsltData = new HashMap<String, Object>();
		SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");

		if(memAuthData==null) {
			return JsonUtil.putFailJsonContainer("MemberControllerERR001", "존재하지 않는 인증 값입니다.");
		} else if("N".equals(memAuthData.getAuth_valid())) {
			return JsonUtil.putFailJsonContainer("MemberControllerERR002", "만료된 인증 값입니다.");
		}

		try {
			Date authDate = format.parse(memAuthData.getAuth_insDate());
			Calendar c = Calendar.getInstance(); 
			long lnValidDate;
			long lnNowDate;

			c.setTime(authDate); 
			c.add(Calendar.DATE, 1);
			authDate = c.getTime();
			lnValidDate = authDate.getTime();

			Date nowDate = new Date();
			lnNowDate = nowDate.getTime();

			if(lnNowDate>lnValidDate){
				HashMap<String, Object> mapChgValid = new HashMap<String, Object>();
				mapChgValid.put("valid", "N");
				mapChgValid.put("key", strKey);
				memAuthDao.setMemberAuthValid(mapChgValid);
				return JsonUtil.putFailJsonContainer("MemberControllerERR003", "만료된 인증 값입니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.putFailJsonContainer("MemberControllerERR999", "알 수 없는 오류가 발생했습니다.");
		}

		Map<String, Object> mapUserData = new HashMap<String, Object>();
		MemberDTO memDto = (MemberDTO) memDao.searchByUserName(memAuthData.getAuth_user());
		mapUserData.put("userName", memDto.getUserNm());
		mapUserData.put("userNo", memDto.getUserNo());
		mapUserData.put("userId", memDto.getUserId());
		mapUserData.put("userCellNum", memDto.getUserCellNum());

		ArrayList<MemberInfoDTO> memInfoDto = (ArrayList<MemberInfoDTO>) memInfoDao.getMemberInfoAttr();
		mapRsltData.put("userInfo", mapUserData);
		mapRsltData.put("info", memInfoDto);
		return  JsonUtil.putSuccessJsonContainer(mapRsltData);

	}
	
	@RequestMapping("api/admin/memberCount.do")
	public @ResponseBody Map<String, Object> getMemberCount(@RequestParam(value="gener", required=false) String gener){
		System.out.println(gener);
		Map<String,String> reqParam=new HashMap<String,String>();
		reqParam.put("gener", gener);
		Map<String,Object> resultParam=new HashMap<String,Object>();
		resultParam.put("developer", memDao.getMemberCountDeveloper(reqParam));
		resultParam.put("designer", memDao.getMemberCountDesigner(reqParam));
		resultParam.put("etc", memDao.getMemberCountEtc(reqParam));
		return  JsonUtil.putSuccessJsonContainer(resultParam);
	}
	
	
}
