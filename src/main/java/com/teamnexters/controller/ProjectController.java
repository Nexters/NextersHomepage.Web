package com.teamnexters.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.teamnexters.dao.ProjectDAO;
import com.teamnexters.dto.ProjectDTO;
import com.teamnexters.util.JsonUtil;

@Controller
public class ProjectController {

	@Autowired
	private ProjectDAO projectDao;
	@Value("#{uploadPath['imgpath']}")
	private String realPath;
	@Autowired
	private ProjectDTO projectDto;

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
			fileName=pre+time+"."+end;

			String projectDesc=fileDto.getProjectDesc();
			fileDto.setProjectDesc(projectDesc.replaceAll("\n", "<br>"));
			



			try {
				
				
				File file = new File(realPath + "product/"+fileName);



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
	
	@RequestMapping("api/admin/deleteProject.do")
	public @ResponseBody Map<String,Object> deleteProject(@RequestParam(value="projectNo") int projectNo,
														  @RequestParam(value="projectImg") String projectImg){
		System.out.println("dkfdf"+projectImg);
		projectDto.setProjectNo(projectNo);
		projectDao.deleteProject(projectDto);
		
		File file=new File(realPath + projectImg);
		
		if(file.exists()){
			
			file.delete();
		}
		
		
		return  JsonUtil.putSuccessJsonContainer(null);
	}
	
	@RequestMapping("api/admin/updateProject.do")
	public @ResponseBody Map<String,Object> updateProject(ProjectDTO fileDto){
		System.out.println(fileDto.getProjectDesc());
		System.out.println(fileDto.getProjectNm());
		MultipartFile uploadFile = fileDto.getUploadFile();
		if(fileDto.getProjectDesc()!=null){
			String projectDesc=fileDto.getProjectDesc();
			fileDto.setProjectDesc(projectDesc.replaceAll("\n", "<br>"));
		}
		

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
				File file = new File(realPath + "product/"+fileName);

				System.out.println(fileDto.getOriginProjectImg());
				File originFile=new File(realPath+fileDto.getOriginProjectImg());
				if(originFile.exists()){
					originFile.delete();
				}

				uploadFile.transferTo(file);

				fileDto.setProjectImg(fileName);
				
				projectDao.updateProject(fileDto);
			} catch (IOException e) {
				e.printStackTrace();
			} // try - catch
		} // if
		else{
			projectDao.updateProject(fileDto);
		}
		
		return  JsonUtil.putSuccessJsonContainer(null);
		
	}
}
