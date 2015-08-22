package com.teamnexters.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.teamnexters.dao.AchieveDAO;
import com.teamnexters.dao.AchieveMemberDAO;
import com.teamnexters.dao.MemberDAO;
import com.teamnexters.dto.AchieveDTO;
import com.teamnexters.dto.AchieveMemberDTO;
import com.teamnexters.dto.MemberDTO;
import com.teamnexters.util.JsonUtil;

@Controller
public class AchieveController {

	@Autowired
	private AchieveDAO achieveDao;
	@Value("#{uploadPath['imgpath']}")
	private String realPath;
	@Autowired
	private AchieveDTO achieveDto;
	@Autowired
	private AchieveMemberDAO achieveMemberDao;
	@Autowired
	private MemberDAO memberDao;
	
	@RequestMapping("api/admin/achieveAdd.do")
	public @ResponseBody Map<String,Object> achieveAdd(AchieveDTO fileDto,@RequestParam(value="memberList") String memberList) {

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

			String achieveDesc=fileDto.getAchieveDesc();
			fileDto.setAchieveDesc(achieveDesc.replaceAll("\n", "<br>"));
			



			try {
				
				
				File file = new File(realPath + "achieve/"+fileName);



				uploadFile.transferTo(file);
				
				

				fileDto.setAchieveImg(fileName);
				
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
					
				
				
				achieveDao.insertAchieve(fileDto);
				if(list!=null){
					Map<String,Object> param=new HashMap<String,Object>();
					param.put("list", list);
					achieveMemberDao.achieveMemberAdd(param);
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			} // try - catch
		} // if

		return  JsonUtil.putSuccessJsonContainer(null);

	}
	
	@RequestMapping("api/main/getAchieveList.do")
	public @ResponseBody Map<String,Object> getAchieveList(){
		
		List<AchieveDTO> list=(ArrayList<AchieveDTO>)achieveDao.getAchieveList();
		
		Map<String,Object> map=new HashMap<String,Object>();
		
		map.put("list", list);
		
		return  JsonUtil.putSuccessJsonContainer(map);
	}
	
	@RequestMapping("api/main/achieveMemberList.do")
	public @ResponseBody Map<String,Object> AchieveMemberList(AchieveMemberDTO member){
		
		List<MemberDTO> list=(ArrayList<MemberDTO>)achieveMemberDao.achieveMemberList(member);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("memberList", list);
		
		return  JsonUtil.putSuccessJsonContainer(map);
	}
	
	@RequestMapping("api/admin/deleteAchieve.do")
	public @ResponseBody Map<String,Object> deleteAchieve(@RequestParam(value="achieveNo") int achieveNo,
														  @RequestParam(value="achieveImg") String achieveImg){
		
		achieveDto.setAchieveNo(achieveNo);
		achieveDao.deleteAchieve(achieveDto);
		
		File file=new File(realPath + "achieve/"+ achieveImg);
		
		if(file.exists()){
			
			file.delete();
		}
		
		
		return  JsonUtil.putSuccessJsonContainer(null);
	}
	
	@RequestMapping("api/admin/updateAchieve.do")
	public @ResponseBody Map<String,Object> updateAchieve(AchieveDTO fileDto,@RequestParam(value="memberList" ,required=false) String memberList){
		
		int AchieveNo=fileDto.getAchieveNo();
		
		MultipartFile uploadFile = fileDto.getUploadFile();
		if(fileDto.getAchieveDesc()!=null){
			String achieveDesc=fileDto.getAchieveDesc();
			fileDto.setAchieveDesc(achieveDesc.replaceAll("\n", "<br>"));
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
				
				
				File file = new File(realPath + "achieve/"+fileName);

				System.out.println(fileDto.getOriginAchieveImg());
				File originFile=new File(realPath+fileDto.getOriginAchieveImg());
				if(originFile.exists()){
					originFile.delete();
				}

				uploadFile.transferTo(file);

				fileDto.setAchieveImg(fileName);
				
				achieveDao.updateAchieve(fileDto);
				
				
			} catch (Exception e) {
				e.printStackTrace();
			} // try - catch
		} // if
		else{
			System.out.println("dddd"+fileDto.getAchieveNo());
			if(fileDto.getAchieveDesc()!=null || fileDto.getAchieveImg()!=null || fileDto.getAchieveLink()!=null || fileDto.getAchieveNm() !=null){
				achieveDao.updateAchieve(fileDto);
			}
			
		}
		
		achieveMemberDao.achieveMemberDelete(fileDto);
		
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
				param.put("achieveNo", AchieveNo);
				achieveMemberDao.achieveMemberUpdate(param);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return  JsonUtil.putSuccessJsonContainer(null);
		
	}
}
