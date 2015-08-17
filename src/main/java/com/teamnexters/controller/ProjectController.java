package com.teamnexters.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;













import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.postgresql.core.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.teamnexters.dao.MemberDAO;
import com.teamnexters.dao.ProjectDAO;
import com.teamnexters.dao.ProjectMemberDAO;
import com.teamnexters.dto.MemberDTO;
import com.teamnexters.dto.ProjectDTO;
import com.teamnexters.dto.ProjectMemberDTO;
import com.teamnexters.util.JsonUtil;

@Controller
public class ProjectController {

	@Autowired
	private ProjectDAO projectDao;
	@Value("#{uploadPath['imgpath']}")
	private String realPath;
	@Autowired
	private ProjectDTO projectDto;
	@Autowired
	private ProjectMemberDAO projectMemberDao;
	@Autowired
	private MemberDAO memberDao;

	@RequestMapping("api/admin/projectAdd.do")
	public @ResponseBody Map<String,Object> projectAdd(ProjectDTO fileDto,@RequestParam(value="memberList") String memberList) {

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
				
				System.out.println(memberList);
				JSONParser parser=new JSONParser();
					JSONArray ja=(JSONArray)parser.parse(memberList);
					System.out.println(ja.toJSONString());
					ArrayList list = new ArrayList();
					for(int i=0;i<ja.size();i++){
						JSONObject jo=(JSONObject)ja.get(i);
						System.out.println(jo.get("userNo"));
						list.add(jo.get("userNo"));
					
					}
					
				
				
				projectDao.insertProject(fileDto);
				if(list!=null){
					Map<String,Object> param=new HashMap<String,Object>();
					param.put("list", list);
					projectMemberDao.projectMemberAdd(param);
				}
				
				
			} catch (Exception e) {
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
	
	@RequestMapping("api/admin/projectMemberList.do")
	public @ResponseBody Map<String,Object> projectMemberList(ProjectMemberDTO member){
		
		List<MemberDTO> list=(ArrayList<MemberDTO>)projectMemberDao.projectMemberList(member);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("memberList", list);
		
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
	public @ResponseBody Map<String,Object> updateProject(ProjectDTO fileDto,@RequestParam(value="memberList" ,required=false) String memberList){
		System.out.println(fileDto.getProjectDesc());
		System.out.println(fileDto.getProjectNm());
		int projectNo=fileDto.getProjectNo();
		
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
				
				
			} catch (Exception e) {
				e.printStackTrace();
			} // try - catch
		} // if
		else{
			System.out.println("dddd"+fileDto.getProjectNo());
			if(fileDto.getProjectDesc()!=null || fileDto.getProjectImg()!=null || fileDto.getProjectLink()!=null || fileDto.getProjectNm() !=null){
				projectDao.updateProject(fileDto);
			}
			
		}
		
		projectMemberDao.projectMemberDelete(fileDto);
		
		JSONParser parser=new JSONParser();
		JSONArray ja;
		try {
			ja = (JSONArray)parser.parse(memberList);
			System.out.println(ja.toJSONString());
			ArrayList list = new ArrayList();
			for(int i=0;i<ja.size();i++){
				JSONObject jo=(JSONObject)ja.get(i);
				System.out.println(jo.get("userNo"));
				list.add(jo.get("userNo"));
			
			}
			
			if(list!=null){
				Map<String,Object> param=new HashMap<String,Object>();
				param.put("list", list);
				param.put("projectNo", projectNo);
				projectMemberDao.projectMemberUpdate(param);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return  JsonUtil.putSuccessJsonContainer(null);
		
	}
}
