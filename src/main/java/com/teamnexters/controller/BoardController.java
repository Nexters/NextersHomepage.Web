package com.teamnexters.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.teamnexters.dto.ImageFileDTO;
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
	
	@Value("#{imgpath['path']}")
	private String imageRealPath;

	@Value("#{imgpath['url']}")
	private String serverUrl;
	
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
	@RequestMapping("api/user/imageUpload.do")
	public @ResponseBody Map<String,Object>  imageUpload(HttpServletRequest request,HttpServletResponse response,ImageFileDTO imageFile){
		String return1=request.getParameter("callback");
		String return2="?callback_func="+request.getParameter("callback_func");
		String return3="";
		String name="";
		try{
			if(imageFile.getFileData() !=null && imageFile.getFileData().getOriginalFilename()!=null && !imageFile.getFileData().getOriginalFilename().equals("")){
				name=imageFile.getFileData().getOriginalFilename().substring(imageFile.getFileData().getOriginalFilename().lastIndexOf(File.separator)+1);
				String filename_ext=name.substring(name.lastIndexOf(".")+1);
				filename_ext=filename_ext.toLowerCase();
				String[] allow_file={"jpg","png","bmp","gif"};
				int cnt=0;
				for(int i=0;i<allow_file.length;i++){
					if(filename_ext.equals(allow_file[i])){
						cnt++;
					}
				}
				if(cnt==0){
					return3="&errstr="+name;
				}else{
					String filePath=imageRealPath+"upload"+File.separator;
					String realFileName="";
					SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMddHHmmss");
					String today=formatter.format(new Date());
					realFileName=today+UUID.randomUUID().toString()+name.substring(name.lastIndexOf("."));
					String rFileName=filePath+realFileName;
					
					imageFile.getFileData().transferTo(new File(rFileName));
					
					return3+="&bNewLine=true";
					return3+="&sFileName="+name;
					return3+="&sFileURL=../img/upload/"+realFileName;
				}
			}else{
				return3+="&errstr=error";
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("redirect", return1+return2+return3);
		
		return map;
	}
	
	@RequestMapping("api/user/imageUploadHTML5.do")
	public void imageUploadHTML5(HttpServletRequest request,HttpServletResponse response){
		try{
			String sFileInfo="";
			String fileName=request.getHeader("file-name");
			
			String filename_ext=fileName.substring(fileName.lastIndexOf(".")+1);
			filename_ext=filename_ext.toLowerCase();
			
			String allow_file[]={"jpg","png","bmp","gif"};
			
			int cnt=0;
			for(int i=0;i<allow_file.length;i++){
				if(filename_ext.equals(allow_file[i])){
					cnt++;
				}
			}
			if(cnt==0){
				PrintWriter print=response.getWriter();
				print.print("NOTALLOW_"+fileName);
				print.flush();
				print.close();
			}else{
				String filePath=imageRealPath+"upload"+File.separator;
				String realFileName="";
				SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMddHHmmss");
				String today=formatter.format(new Date());
				realFileName=today+UUID.randomUUID().toString()+fileName.substring(fileName.lastIndexOf("."));
				String rFileName=filePath+realFileName;
				
				InputStream is=request.getInputStream();
				OutputStream os=new FileOutputStream(rFileName);
				int numRead;
				byte b[]=new byte[Integer.parseInt(request.getHeader("file-size"))];
				while((numRead=is.read(b,0,b.length))!=-1){
					os.write(b,0,numRead);
				}
				if(is!=null){
					is.close();
				}
				os.flush();
				os.close();
				sFileInfo+="&bNewLine=true";
				sFileInfo+="&sFileName="+fileName;
				//서버 URL를 설정하는 환경 설정 값이 필요
				sFileInfo+="&sFileURL="+serverUrl+"upload/"+realFileName;
				PrintWriter print=response.getWriter();
				print.print(sFileInfo);
				print.flush();
				print.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
