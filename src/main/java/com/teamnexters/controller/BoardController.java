package com.teamnexters.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

import javax.servlet.ServletContext;
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
import org.springframework.web.multipart.MultipartFile;

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
	
	@Value("#{uploadPath['imgpath']}")
	private String imageRealPath;

	@Value("#{uploadPath['url']}")
	private String serverUrl;
	
	@Value("#{uploadPath['boardpath']}")
	private String boardPath;
	
	private static final int BUFFER_SIZE = 4096;
	
	@RequestMapping("api/admin/boardAdd.do")
	public @ResponseBody Map<String,Object> boardAdd(@RequestParam(value="boardName") String boardName,@RequestParam(value="boardDir") String boardDir ){
		
		File dir=new File(boardPath+boardDir);
		System.out.println(boardPath+boardDir);
		if(!dir.exists()){
			dir.mkdirs();
		}
		boardDto.setBoardName(boardName);
		boardDto.setBoardDir(boardDir);
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
	
	@RequestMapping("api/admin/boardDelete.do")
	public @ResponseBody Map<String,Object> boardDelete(BoardDTO boardDto){
		boardDao.boardDelete(boardDto);
		
		return JsonUtil.putSuccessJsonContainer(null);
	}
	
	@RequestMapping("api/admin/postInsert.do")
	public @ResponseBody Map<String,Object> postInsert(BoardInfoDTO infoDto){
		MultipartFile uploadFile = infoDto.getUploadFile();
		
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
		
		if(uploadFile!=null){
			String fileName = uploadFile.getOriginalFilename();
			BoardDTO temp=(BoardDTO)boardDao.getUploadPath(infoDto);
			String uploadPath=temp.getBoardDir();
			int comma=fileName.lastIndexOf(".");
			String pre=fileName.substring(0,comma);
			String end=fileName.substring(comma+1,fileName.length());
			fileName=pre+time+"."+end;
			
			try {
				
				
				File file = new File(boardPath + uploadPath+"/"+fileName);
				


				uploadFile.transferTo(file);
				infoDto.setFile(uploadPath+"/"+fileName);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		infoDao.postInsert(infoDto);
		
		
		
		
		return JsonUtil.putSuccessJsonContainer(null);
	}
	@RequestMapping("api/admin/fileDownload.do")
	public void fileDownload(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="fileName")String fileName) throws IOException{
		// get absolute path of the application
		ServletContext context = request.getServletContext();
 
        // construct the complete absolute path of the file
        String fullPath = boardPath+fileName;         
        File downloadFile = new File(fullPath);
        FileInputStream inputStream = new FileInputStream(downloadFile);
         
        // get MIME type of the file
        String mimeType = context.getMimeType(fullPath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);
 
        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());
 
        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                URLEncoder.encode(downloadFile.getName(),"UTF-8"));
        System.out.println(downloadFile.getName());
        
        response.setHeader(headerKey, headerValue);
 
        // get output stream of the response
        OutputStream outStream = response.getOutputStream();
 
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;
 
        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
 
        inputStream.close();
        outStream.close();
        
        
       
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
		MultipartFile uploadFile = infoDto.getUploadFile();
		
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
		
		if(uploadFile!=null){
			String fileName = uploadFile.getOriginalFilename();
			BoardDTO temp=(BoardDTO)boardDao.getUploadPath(infoDto);
			String uploadPath=temp.getBoardDir();
			int comma=fileName.lastIndexOf(".");
			String pre=fileName.substring(0,comma);
			String end=fileName.substring(comma+1,fileName.length());
			fileName=pre+time+"."+end;
			
			try {
				
				
				File file = new File(boardPath + uploadPath+"/"+fileName);
				


				uploadFile.transferTo(file);
				infoDto.setFile(uploadPath+"/"+fileName);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
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
				sFileInfo+="&sFileURL="+serverUrl+"admin/img/upload/"+realFileName;
				PrintWriter print=response.getWriter();
				print.print(sFileInfo);
				print.flush();
				print.close();
				Thread.sleep(1000);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
}
