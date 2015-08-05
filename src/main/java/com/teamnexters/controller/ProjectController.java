package com.teamnexters.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	private ProjectDAO projectDao;
	@Value("#{imgpath['path']}")
	private String realPath;

	@RequestMapping("api/admin/projectAdd.do")
	@Autowired
	public @ResponseBody Map<String,Object> projectAdd(ProjectDTO fileDto,HttpServletRequest request) {

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
			fileName=pre+time+"."+end;




			try {
				
				System.out.println(realPath);
				File file = new File(realPath + fileName);



				uploadFile.transferTo(file);

				fileDto.setProjectImg(fileName);
				
				projectDao.insertProject(fileDto);
			} catch (IOException e) {
				e.printStackTrace();
			} // try - catch
		} // if

		return  JsonUtil.putSuccessJsonContainer(null);

	}
	
	@RequestMapping("api/admin/getProjectList.do")
	public @ResponseBody Map<String,Object> getProjectList(){
		
		List<ProjectDTO> list=(ArrayList<ProjectDTO>)projectDao.getProjectList();
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("list", list);
		
		return  JsonUtil.putSuccessJsonContainer(map);
	}
}
