package com.teamnexters.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamnexters.dao.MngDAO;
import com.teamnexters.dto.AttendanceDTO;
import com.teamnexters.dto.BooksDTO;
import com.teamnexters.dto.BooksValDTO;
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

		mngDao.deleteAttendanceByMngNoAndUserNo(attendanceDto);
		int rsltIns = mngDao.insertAttendance(attendanceDto);
		
		Map<String, Object> mapRsltData = new HashMap<String, Object>();
		mapRsltData.put("rslt", rsltIns);
		
		return JsonUtil.putSuccessJsonContainer(mapRsltData);
	}
	
	@RequestMapping("api/admin/insertBooks.do")
	public @ResponseBody Map<String, Object> insertBooks(@RequestParam(value="mngno") String strMngNo
																	, @RequestParam(value="title") String strTitle
																	, @RequestParam(value="amount") String stramount){
		int amount, mngno;
		try {
			amount = Integer.parseInt(stramount);
			mngno = Integer.parseInt(strMngNo);
		} catch (Exception e) {
			return JsonUtil.putFailJsonContainer("ManagementControllerERR0003", "금액은이나 운영 코드는 숫자만 가능합니다.");
		}
		
		BooksDTO booksDto = new BooksDTO();
		booksDto.setAmount(amount);
		booksDto.setMngno(mngno);
		booksDto.setTitle(strTitle);
		
		int rsltIns = mngDao.insertBooks(booksDto);
		
		Map<String, Object> mapRsltData = new HashMap<String, Object>();
		mapRsltData.put("rslt", rsltIns);
		
		return JsonUtil.putSuccessJsonContainer(mapRsltData);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("api/admin/getBooksList.do")
	public @ResponseBody Map<String, Object> insertBooks(@RequestParam(value="mngno") String strMngNo){
		int  mngno;
		try {
			mngno = Integer.parseInt(strMngNo);
		} catch (Exception e) {
			return JsonUtil.putFailJsonContainer("ManagementControllerERR0003", "운영 코드는 숫자만 가능합니다.");
		}
		ArrayList<BooksDTO> listBooks = (ArrayList<BooksDTO>) mngDao.getBooksList(mngno);

		for(int i=0; i<listBooks.size(); i++) {
			BooksValDTO requestbooksvalDTO = new BooksValDTO();
			requestbooksvalDTO.setMngno(mngno);
			requestbooksvalDTO.setBooksno(listBooks.get(i).getBooksno());
			ArrayList<BooksValDTO> listBooksVal = (ArrayList<BooksValDTO>) mngDao.getBooksValByBooksNoAndMngNo(requestbooksvalDTO);
			listBooks.get(i).setVal(listBooksVal);
		}
		
		Map<String, Object> mapRsltData = new HashMap<String, Object>();
		mapRsltData.put("list", listBooks);
		
		return JsonUtil.putSuccessJsonContainer(mapRsltData);
	}
	
	
	@RequestMapping("api/admin/insertBooksVal.do")
	public @ResponseBody Map<String, Object> insertBooksVal(@RequestParam(value="booksno") String strBooksNo
																	, @RequestParam(value="userno") String strUserNo
																	, @RequestParam(value="amount") String stramount){
		int amount;
		int booksno;
		try {
			amount = Integer.parseInt(stramount);
			booksno = Integer.parseInt(strBooksNo);
		} catch (Exception e) {
			return JsonUtil.putFailJsonContainer("ManagementControllerERR0004", "금액이나 장부 번호는 숫자만 가능합니다.");
		}
		
		BooksValDTO booksValDto = new BooksValDTO();
		booksValDto.setAttval(amount);
		booksValDto.setBooksno(booksno);
		booksValDto.setUserno(strUserNo);
		
		int rsltIns = mngDao.insertBooksVal(booksValDto);
		
		Map<String, Object> mapRsltData = new HashMap<String, Object>();
		mapRsltData.put("rslt", rsltIns);
		
		return JsonUtil.putSuccessJsonContainer(mapRsltData);
	}
	
	@RequestMapping("api/admin/modifyBooksVal.do")
	public @ResponseBody Map<String, Object> modifyBooksVal(@RequestParam(value="booksno") String strBooksNo
																	, @RequestParam(value="userno") String strUserNo
																	, @RequestParam(value="amount") String stramount){
		int amount;
		int booksno;
		try {
			amount = Integer.parseInt(stramount);
			booksno = Integer.parseInt(strBooksNo);
		} catch (Exception e) {
			return JsonUtil.putFailJsonContainer("ManagementControllerERR0004", "금액이나 장부 번호는 숫자만 가능합니다.");
		}
		
		BooksValDTO booksValDto = new BooksValDTO();
		booksValDto.setAttval(amount);
		booksValDto.setBooksno(booksno);
		booksValDto.setUserno(strUserNo);
		
		mngDao.deleteBooksVal(booksValDto);
		
		int rsltIns = mngDao.insertBooksVal(booksValDto);
		
		Map<String, Object> mapRsltData = new HashMap<String, Object>();
		mapRsltData.put("rslt", rsltIns);
		
		return JsonUtil.putSuccessJsonContainer(mapRsltData);
	}
}
