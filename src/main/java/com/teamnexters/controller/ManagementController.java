package com.teamnexters.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamnexters.dao.MngDAO;
import com.teamnexters.dto.AttendanceDTO;
import com.teamnexters.dto.MngDTO;
import com.teamnexters.util.JsonUtil;

@Controller
public class ManagementController {
	
	private static final Logger logger =  Logger.getLogger(ManagementController.class);

	
	@Autowired
	MngDAO mngDao;
	
	@RequestMapping("api/admin/getManageList.do")
	public @ResponseBody Map<String,Object> getManageList() {
		Map<String, Object> mapRslt = new HashMap<String, Object>();
		mapRslt.put("list", mngDao.getManageList());
		
		return JsonUtil.putSuccessJsonContainer(mapRslt);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("api/admin/getManageCnt.do")
	public @ResponseBody Map<String,Object> getManageCnt(@RequestParam(value="date") String strMngDate) {
		
		return JsonUtil.putSuccessJsonContainer((Map<String, Object>)mngDao.getManageCnt(strMngDate));
	}
	
	@RequestMapping("api/admin/insertManager.do")
	public @ResponseBody Map<String,Object> insertManager(@RequestParam(value="date") String strMngDate
														, @RequestParam(value="title") String strMngTitle
														, @RequestParam(value="remark",required=false) String strMngRemark) {
		
		@SuppressWarnings("unchecked")
		Map<String, Object> mapCheckDate = (Map<String, Object>)mngDao.getManageCnt(strMngDate);
		if((Long)mapCheckDate.get("cnt")>0){
			return JsonUtil.putFailJsonContainer("ManagementControllerERR0001", "중복된 날짜 등록을 시도함");
		}
		
		Map<String, Object> mapRslt = new HashMap<String, Object>();
		MngDTO mngDto = new MngDTO();
		mngDto.setMngdate(strMngDate);
		mngDto.setMngtitle(strMngTitle);
		mngDto.setMngremark(strMngRemark);
		mapRslt.put("result", mngDao.insertManager(mngDto));
		
		return JsonUtil.putSuccessJsonContainer(mapRslt);
	}
	
	@RequestMapping("api/admin/getAttendanceListByMngNo.do")
	public @ResponseBody Map<String, Object> getAttendanceListByMngNo(@RequestParam(value="mngno") String strMngNo){
		int intMngNo;
		try {
			intMngNo = Integer.parseInt(strMngNo);
		} catch (Exception e) {
			return JsonUtil.putFailJsonContainer("ManagementControllerERR0002", "운영 관리 번호는 숫자만 가능합니다.");
		}
		Map<String, Object> mapRsltData = new HashMap<String, Object>();
		mapRsltData.put("list", mngDao.getAttendanceListByMngNo(intMngNo));
		return JsonUtil.putSuccessJsonContainer(mapRsltData);
	}
	
	@RequestMapping("api/admin/insertAttendance.do")
	public @ResponseBody Map<String, Object> getAttendanceListByMngNo(@RequestParam(value="mngno") String strMngNo
																	, @RequestParam(value="userno") String strUserNo
																	, @RequestParam(value="value") String strValue){
		AttendanceDTO attendanceDto = new AttendanceDTO();
		attendanceDto.setUserNo(strUserNo);
		attendanceDto.setMngNo(Integer.parseInt(strMngNo));
		attendanceDto.setEleId(strValue);
		
		logger.debug("attendanceDto::"+attendanceDto.toString());
		
		int rsltDlt = mngDao.deleteAttendanceByMngNoAndUserNo(attendanceDto);
		logger.debug("Delete Cnt ::"+rsltDlt);
		
		int rsltIns = mngDao.insertAttendance(attendanceDto);
		logger.debug("Insert Cnt ::"+rsltIns);
		
		Map<String, Object> mapRsltData = new HashMap<String, Object>();
		mapRsltData.put("rslt", rsltIns);
		
		return JsonUtil.putSuccessJsonContainer(mapRsltData);
	}
	
}
