package com.john.models;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component("fileList")
public class FileList {
	private HashMap<String, LinkedHashMap<String,MultipartFile>> ShareList;
	
	public HashMap<String, LinkedHashMap<String,MultipartFile>> getShareList() {
		return ShareList;
	}

	public void setShareList(HashMap<String, LinkedHashMap<String, MultipartFile>> shareList) {
		ShareList = shareList;
	}

	public FileList(){
		this.ShareList = new HashMap<String, LinkedHashMap<String,MultipartFile>>();
	}
	
	public void addShare(String name, LinkedHashMap<String, MultipartFile> user){
		ShareList.put(name, user);
		System.out.println("added : " + name);
		System.out.println("now Size : " + ShareList.size());
	}
	
	public void removeShare(String name){
		
		ShareList.remove(name);
		System.out.println("removed : " + name);
	}
	
	public LinkedHashMap<String, MultipartFile> getUser(String name){
		
		return ShareList.get(name);
	}
	

}
