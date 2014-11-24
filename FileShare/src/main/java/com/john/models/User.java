package com.john.models;

import java.util.LinkedHashMap;

import org.springframework.web.multipart.MultipartFile;


//@Component("user")
//@Scope("prototype")
public class User {
	String ID;
	String State;
	String shareId;
	LinkedHashMap<String, MultipartFile> fileList;
	
	public User(){
		this.fileList = new LinkedHashMap<String, MultipartFile>();
	}
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public LinkedHashMap<String, MultipartFile> getFileList() {
		return fileList;
	}
	public void setFileList(LinkedHashMap<String, MultipartFile> fileList) {
		this.fileList = fileList;
	}
	
	public void init(){
		this.ID = null; this.State = null; this.shareId = null;
		this.fileList = new LinkedHashMap<String, MultipartFile>();
	}

	public String getShareId() {
		return shareId;
	}

	public void setShareId(String shareId) {
		this.shareId = shareId;
	}
	
	// controller per
	// session add
	// file add
	// state set

}
