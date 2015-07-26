package com.teamnexters.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamnexters.dao.MemberDAO;
import com.teamnexters.dao.RecordBooksColumnDAO;
import com.teamnexters.dao.RecordBooksValueDAO;
import com.teamnexters.dto.MemberDTO;
import com.teamnexters.dto.RecordBooksColumnDTO;
import com.teamnexters.util.JsonUtil;

@Controller
public class RecordBooksController {

	@Autowired
	private RecordBooksColumnDTO booksColumnDto;
	@Autowired
	private RecordBooksColumnDAO booksColumnDao;
	@Autowired
	private MemberDAO memDao;
	@Autowired
	private RecordBooksValueDAO booksValueDao;


	@RequestMapping("api/admin/userActivityList.do")
	public @ResponseBody Map<String,Object> userActivityList(){
		ArrayList<MemberDTO> list=(ArrayList<MemberDTO>)memDao.getActivityMemberList();

		Map<String,Object> resultData=new HashMap<String, Object>();
		resultData.put("memberList", list);

		return  JsonUtil.putSuccessJsonContainer(resultData);
	}
	@RequestMapping("api/admin/getDateList.do")
	public @ResponseBody Map<String,Object> getDateList(@RequestParam Map<String,String> params){
		Map<String,Object> resultData=new HashMap<String, Object>();
		ArrayList<RecordBooksColumnDTO> dateList=(ArrayList<RecordBooksColumnDTO>)booksColumnDao.getDateList(params.get("bookNm"));
		resultData.put("dateList", dateList);
		return  JsonUtil.putSuccessJsonContainer(resultData);
	}
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
		

		return JsonUtil.putSuccessJsonContainer(null);
	}
	@RequestMapping("api/admin/insertBooksValue.do")
	public @ResponseBody Map<String, Object> insertBooksValue(@RequestParam Map<String,String> params){
		System.out.println(params.toString());
		booksValueDao.insertBooksValue(params);

		return JsonUtil.putSuccessJsonContainer(null);
	}
	@RequestMapping("api/admin/removeDateColumn.do")
	public @ResponseBody Map<String, Object> deleteDateColumn(@RequestParam Map<String,String> params){
		
		booksColumnDao.deleteDateColumn(params);

		return JsonUtil.putSuccessJsonContainer(null);
	}
}
