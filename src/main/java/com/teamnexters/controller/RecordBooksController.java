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
import com.teamnexters.dto.RecordBooksValueDTO;
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
	@RequestMapping("api/admin/booksValueList.do")
	public @ResponseBody Map<String, Object> getBooksValueList(){
		ArrayList<RecordBooksValueDTO> list=(ArrayList<RecordBooksValueDTO>)booksValueDao.getBooksValueList();
		Map<String,Object> resultData=new HashMap<String,Object>();
		resultData.put("valueList", list);
		return JsonUtil.putSuccessJsonContainer(resultData);
	}
	@RequestMapping("api/admin/modifyBooksValue.do")
	public @ResponseBody Map<String, Object> updateBooksValue(@RequestParam Map<String,String> params){
		
		booksValueDao.updateBooksValue(params);
		
		return JsonUtil.putSuccessJsonContainer(null);
	}
	
	@RequestMapping("api/admin/attendenceCountList.do")
	public @ResponseBody Map<String, Object> attendenceCountList(){
		
		Map<String,Object> params=new HashMap<String,Object>();
		ArrayList<RecordBooksColumnDTO> list=(ArrayList<RecordBooksColumnDTO>)booksColumnDao.getAttendenceCount();
		Map<String,Object> resultParam=new HashMap<String,Object>();
		ArrayList countList=new ArrayList();
		for(int i=0;i<list.size();i++){
			
			ArrayList<Map<String,Object>> tmpList=(ArrayList<Map<String,Object>>)booksValueDao.getAttendenceCountList(list.get(i));
			Map<String,Object> tmpMap=new HashMap<String,Object>();
			tmpMap.put("date", list.get(i).getBookColumnNo());
			tmpMap.put("attend", tmpList.get(0).get("attend"));
			tmpMap.put("late", tmpList.get(0).get("late"));
			tmpMap.put("absence", tmpList.get(0).get("absence"));
			countList.add(tmpMap);
			
			
		}
		resultParam.put("list", countList);
		
		return JsonUtil.putSuccessJsonContainer(resultParam);
	}
}
