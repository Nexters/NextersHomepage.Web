package com.teamnexters.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.teamnexters.dao.ProjectDAO;
import com.teamnexters.dto.ProjectDTO;
import com.teamnexters.util.JsonUtil;

@Controller
public class ProjectController {

	@Autowired
	ProjectDAO projectDao;

	@RequestMapping("api/admin/projectAdd.do")
	@Autowired
	public @ResponseBody Map<String,Object> projectAdd(ProjectDTO fileDto) {

		MultipartFile uploadFile = fileDto.getUploadFile();


		if (uploadFile != null) {

			String fileName = uploadFile.getOriginalFilename();

			Date date=new Date();
			int year=date.getYear()-100;
			int month=date.getMonth()+1;
			int day=date.getDate();
			int hour=date.getHours();
			int minute=date.getMinutes();
			int second=date.getSeconds();
			String time=year+""+month+""+day+""+hour+""+minute+""+second;
			int comma=fileName.lastIndexOf(".");
			String pre=fileName.substring(0,comma);
			String end=fileName.substring(comma+1,fileName.length());
			fileName=pre+"_"+time+"."+end;




			try {

				File file = new File("C:/images/" + fileName);



				uploadFile.transferTo(file);

				fileDto.setProjectImg(fileName);
				
				projectDao.insertProject(fileDto);
			} catch (IOException e) {
				e.printStackTrace();
			} // try - catch
		} // if

		return  JsonUtil.putSuccessJsonContainer(null);

	}
}
