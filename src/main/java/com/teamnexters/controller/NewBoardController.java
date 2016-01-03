package com.teamnexters.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamnexters.dao.NewBoardDAO;
import com.teamnexters.dto.NewBoardDTO;
import com.teamnexters.util.JsonUtil;

@Controller
public class NewBoardController {
	@Autowired
	private NewBoardDAO newboarddao;
	

	@RequestMapping("/api/board/getNewBoardList.do")
	public @ResponseBody Map<String, Object> getNewBoardList(@RequestParam(value="limit") String strLimit
															,@RequestParam(value="offset") String strOffset) {
		Map<String, Object> mapRequestData = new HashMap<String, Object>();
		Map<String, Object> mapRsltData = new HashMap<String, Object>();
		
		mapRequestData.put("limit", Integer.parseInt(strLimit));
		mapRequestData.put("offset", Integer.parseInt(strOffset));
		
		ArrayList<NewBoardDTO> rsltData = (ArrayList<NewBoardDTO>)newboarddao.getBooksList(mapRequestData);
		mapRsltData.put("list", rsltData);
		
		return JsonUtil.putSuccessJsonContainer(mapRsltData);
	}
	
	@RequestMapping("/api/board/getNewBoardContent.do")
	public @ResponseBody Map<String, Object> getNewBoardContent(@RequestParam(value="no") String strNo){
		Map<String,Object> rsltData = (Map<String,Object>)newboarddao.getBoardContents(Integer.parseInt(strNo));
		return JsonUtil.putSuccessJsonContainer(rsltData);
	
	} 
	  
	@RequestMapping("/api/board/insertBoardContents.do")
	public @ResponseBody Map<String, Object> insertBoardContents(@RequestParam(value="writer") String strWriter
																,@RequestParam(value="title") String strTitle
																,@RequestParam(value="contents") String strContents
																,@RequestParam(value="insdate") String strInsdate
																,@RequestParam(value="password") String strPassword){
		Map<String, Object> mapRsltData = new HashMap<String, Object>();

		NewBoardDTO requestBoardDTO = new NewBoardDTO();
		requestBoardDTO.setContetns(strContents);
		requestBoardDTO.setInsdate(new Date());
		requestBoardDTO.setPassword(strPassword);
		requestBoardDTO.setTitle(strTitle);
		requestBoardDTO.setWriter(strWriter);
		int reusltCnt = newboarddao.insertBoard(requestBoardDTO);
		mapRsltData.put("rslt", reusltCnt);
		return JsonUtil.putSuccessJsonContainer(mapRsltData);
	}
	
	@RequestMapping("/api/board/getBoardTotCnt.do")
	public @ResponseBody Map<String, Object> getBoardTotCnt() {
		Map<String, Object> mapRsltData = new HashMap<String, Object>();
		mapRsltData.put("totCnt", newboarddao.selectBoardTotCnt());
		return JsonUtil.putSuccessJsonContainer(mapRsltData);
		
	}
}
