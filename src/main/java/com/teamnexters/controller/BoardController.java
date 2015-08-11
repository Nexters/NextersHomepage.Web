package com.teamnexters.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamnexters.dao.BoardDAO;
import com.teamnexters.dto.BoardDTO;
import com.teamnexters.util.JsonUtil;

@Controller
public class BoardController {
	
	@Autowired
	private BoardDTO boardDto;
	@Autowired
	private BoardDAO boardDao;

	@RequestMapping("api/admin/boardAdd.do")
	public @ResponseBody Map<String,Object> boardAdd(@RequestParam(value="boardName") String boardName ){
		boardDto.setBoardName(boardName);
		boardDao.boardAdd(boardDto);
		
		return JsonUtil.putSuccessJsonContainer(null);
	}
}
