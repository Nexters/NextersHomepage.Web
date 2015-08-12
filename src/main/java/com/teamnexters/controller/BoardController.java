package com.teamnexters.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
		
		
		infoDao.postInsert(infoDto);
		
		
		
		return JsonUtil.putSuccessJsonContainer(null);
	}
}
