package com.teamnexters.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamnexters.dao.*;
import com.teamnexters.dto.*;
import com.teamnexters.util.JsonUtil;

@Controller
public class PushController {
	
	@Autowired
	private UserPushTransMessageDTO transMessageDto;
	@Autowired
	private UserPushTransMessageDAO transMessageDao;
	@Autowired
	private UserPushMessageDAO messageDao;
	
	@RequestMapping("pushMessage.do")
	public @ResponseBody Map<String, Object> pushMessageTrans(@RequestParam (value="senderBoard") String senderBoard,
															  @RequestParam (value="senderNo") String senderNo,
															  @RequestParam (value="senderNm") String senderNm,
															  @RequestParam (value="message") String message,
															  @RequestParam (value="userNo") String userNo){
		
		List<String> userNoArray=new ArrayList<String>();
		Collections.addAll(userNoArray, userNo.split(","));
		Date date=new Date();
		String year=String.valueOf(date.getYear()-100);
		
		String month=String.valueOf(date.getMonth()+1);
		if(month.length()==1){
			month="0"+month;
		}
		String day=String.valueOf(date.getDate());
		if(day.length()==1){
			day="0"+day;
		}
		String hour=String.valueOf(date.getHours());
		String minute=String.valueOf(date.getMinutes());
		String second=String.valueOf(date.getSeconds());
		String insertDate=year+month+day;
		String insertTime=hour+minute+second;
		String transDate=insertDate+insertTime;
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("senderBoard", senderBoard);
		params.put("senderNo", senderNo);
		params.put("senderNm", senderNm);
		params.put("message", message);
		params.put("userNo",userNoArray);
		params.put("insertTime", insertTime);
		params.put("insertDate", insertDate);
		params.put("transDate", transDate);
		transMessageDao.insertTransMessage(params);
		messageDao.insertMessage(params);
		
		
		
		return JsonUtil.putSuccessJsonContainer(null);
	}
}
