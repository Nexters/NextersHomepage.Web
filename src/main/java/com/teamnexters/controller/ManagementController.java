package com.teamnexters.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamnexters.dao.MngDAO;
import com.teamnexters.dto.MngDTO;
import com.teamnexters.util.JsonUtil;

@Controller
public class ManagementController {
	
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
	
}
