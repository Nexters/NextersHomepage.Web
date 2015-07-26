package com.teamnexters.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamnexters.dao.RecordBooksColumnDAO;
import com.teamnexters.dto.RecordBooksColumnDTO;
import com.teamnexters.util.JsonUtil;

@Controller
public class RecordBooksController {

	@Autowired
	private RecordBooksColumnDTO booksColumnDto;
	@Autowired
	private RecordBooksColumnDAO booksColumnDao;
	
	@RequestMapping("api/admin/addDate.do")
	public @ResponseBody Map<String, Object> addDate(@RequestParam Map<String,String> params){
		String year=params.get("year");
		String month=params.get("month");
		String day=params.get("day");
		String columnNo=year+"-"+month+"-"+day;
		Map<String,String> reqParam=new HashMap<String,String>();
		reqParam.put("columnNo", columnNo);
		reqParam.put("bookNm", params.get("bookNm"));
		booksColumnDao.insertDate(reqParam);
		ArrayList<RecordBooksColumnDTO> list=(ArrayList<RecordBooksColumnDTO>)booksColumnDao.getDateList(params.get("bookNm"));
		Map<String,Object> resultParam=new HashMap<String,Object>();
		resultParam.put("dateList", list);
		
		return JsonUtil.putSuccessJsonContainer(resultParam);
	}
}
