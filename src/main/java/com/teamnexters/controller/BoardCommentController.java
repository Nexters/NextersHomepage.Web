package com.teamnexters.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamnexters.dao.BoardCommentDAO;
import com.teamnexters.dto.BoardCommentDTO;
import com.teamnexters.util.JsonUtil;

@Controller
public class BoardCommentController {

	@Autowired
	private BoardCommentDAO commentDao;
	
	@RequestMapping("api/admin/addPostComment.do")
	public @ResponseBody Map<String,Object> addPostComment(BoardCommentDTO commentDto){
		
		Date date=new Date();
		String year=String.valueOf(date.getYear()-100);
		String month="";
		if(date.getMonth()+1<10)
			month="0";
		month+=date.getMonth()+1;
		String day="";
		if(date.getDate()<10)
			day="0";
		day+=date.getDate();
		String hour="";
		if(date.getHours()<10)
			hour="0";
		hour+=date.getHours();
		String minute="";
		if(date.getMinutes()<10)
			minute="0";
		minute+=date.getMinutes();
		String second="";
		if(date.getSeconds()<10)
			second="0";
		second+=date.getSeconds();
		
		String time=year+month+day+hour+minute+second;
		commentDto.setCommentDate(time);
		commentDto.setPostComment(commentDto.getPostComment().replaceAll("\n", "<br>"));
		commentDao.addPostComment(commentDto);
		
		return JsonUtil.putSuccessJsonContainer(null);
	}
	
	@RequestMapping("api/admin/getCommentList.do")
	public @ResponseBody Map<String,Object> getCommentList(BoardCommentDTO commentDto){
		ArrayList<BoardCommentDTO> list=(ArrayList<BoardCommentDTO>)commentDao.getCommentList(commentDto);
		
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("commentList", list);
		
		return JsonUtil.putSuccessJsonContainer(param);
		
	}
	
	@RequestMapping("api/admin/commentRemove.do")
	public @ResponseBody Map<String,Object> commentRemove(BoardCommentDTO commentDto){
		
		commentDao.commentRemove(commentDto);
		return JsonUtil.putSuccessJsonContainer(null);
	}
}
