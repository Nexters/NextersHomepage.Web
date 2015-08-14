package com.teamnexters.dto;

import org.springframework.web.multipart.MultipartFile;

public class ImageFileDTO {

	private MultipartFile fileData;
	
	public void setFileData(MultipartFile fileData){
		this.fileData=fileData;
	}
	public MultipartFile getFileData(){
		return fileData;
	}
}
