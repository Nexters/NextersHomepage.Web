package com.teamnexters.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamnexters.dao.BoardDAO;
import com.teamnexters.dao.BoardInfoDAO;
import com.teamnexters.dto.BoardDTO;
import com.teamnexters.dto.BoardInfoDTO;
import com.teamnexters.util.JsonUtil;

@Controller
public class BoardController {
	
	@Autowired
	private BoardDTO boardDto;
	@Autowired
	private BoardDAO boardDao;
	@Autowired
	private BoardInfoDAO infoDao;
	@Autowired
	private BoardInfoDTO infoDto;

	@RequestMapping("api/admin/boardAdd.do")
	public @ResponseBody Map<String,Object> boardAdd(@RequestParam(value="boardName") String boardName ){
		boardDto.setBoardName(boardName);
		boardDao.boardAdd(boardDto);
		
		return JsonUtil.putSuccessJsonContainer(null);
	}
	
	@RequestMapping("api/admin/boardList.do")
	public @ResponseBody Map<String,Object> boardList(){
		ArrayList<BoardDTO> list=(ArrayList<BoardDTO>)boardDao.boardList();
		
		Map<String,Object> param=new HashMap<String,Object>();
	
		System.out.println();
		param.put("list", list);
		
		
		
		
		return JsonUtil.putSuccessJsonContainer(param);
	}
	
	@RequestMapping("api/admin/postInsert.do")
	public @ResponseBody Map<String,Object> postInsert(BoardInfoDTO infoDto){
		
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
		infoDto.setPostDate(time);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getCredentials());
		infoDao.postInsert(infoDto);
		
		
		
		return JsonUtil.putSuccessJsonContainer(null);
	}
	
	@RequestMapping("api/admin/postList.do")
	public @ResponseBody Map<String,Object> postList(BoardInfoDTO infoDto){
		ArrayList<BoardInfoDTO> list=(ArrayList<BoardInfoDTO>)infoDao.postList(infoDto);
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("list", list);
		
		return JsonUtil.putSuccessJsonContainer(param);
	}
	
	@RequestMapping("api/admin/getPost.do")
	public @ResponseBody Map<String,Object> getPost(BoardInfoDTO infoDto){
		Map<String,Object> param=new HashMap<String,Object>();
		
		param.put("list",  (BoardInfoDTO)infoDao.getPost(infoDto));
		infoDao.increasePostHits(infoDto);
		return JsonUtil.putSuccessJsonContainer(param);
	}
	
	@RequestMapping("api/admin/removePost.do")
	public @ResponseBody Map<String,Object> removePost(BoardInfoDTO infoDto){
		infoDao.removePost(infoDto);
		
		return JsonUtil.putSuccessJsonContainer(null);
	}
	
	@RequestMapping("api/admin/modifyPost.do")
	public @ResponseBody Map<String,Object> modifyPost(BoardInfoDTO infoDto){
		System.out.println(infoDto.getPostTitle()+" "+infoDto.getPostContent());
		infoDao.modifyPost(infoDto);
		
		return JsonUtil.putSuccessJsonContainer(null);
	}
}
