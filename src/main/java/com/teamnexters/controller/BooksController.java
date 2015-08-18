package com.teamnexters.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamnexters.dao.BooksDAO;
import com.teamnexters.util.JsonUtil;

@Controller
public class BooksController {
	
	@Autowired
	BooksDAO booksDAO;

	@RequestMapping("api/admin/getBooksList_old.do")
	public @ResponseBody Map<String, Object> getBooksList() {
		Map<String, Object> mapRslt = new HashMap<String, Object>();
		mapRslt.put("list", booksDAO.getBooksList());
		return JsonUtil.putSuccessJsonContainer(mapRslt);
	}
	
	@RequestMapping("api/admin/getBooksUserList.do")
	public @ResponseBody Map<String, Object> getBooksUserList(@RequestParam(value="booksnum") int intBooksNumber) {
		Map<String, Object> mapRslt = new HashMap<String, Object>();
		mapRslt.put("list", booksDAO.getBooksUserList(intBooksNumber));
		return JsonUtil.putSuccessJsonContainer(mapRslt);
	}
}
