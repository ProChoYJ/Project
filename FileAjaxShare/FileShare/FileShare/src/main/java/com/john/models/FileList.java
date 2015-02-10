package com.john.models;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component("fileList")
public class FileList {
	private HashMap<String, ArrayList<String>> shareList;

	public HashMap<String, ArrayList<String>> getShareList() {
		return shareList;
	}

	public void setShareList(HashMap<String, ArrayList<String>> shareList) {
		this.shareList = shareList;
	}
	
	public FileList(){
		shareList = new HashMap<String,ArrayList<String>>();
	}
	// shared -> O
	public void addList(String id){
		shareList.put(id, getList(id));
	}
	
	public void removeList(String id){
		shareList.remove(id);
	}
	
	public ArrayList<String> getReceiveList(String id){
		return shareList.get(id);
	}
	
	public ArrayList<String> getList(String id){
		ArrayList<String> list = new ArrayList<String>();
		String path = "/home/john/Document/" + id + "/";
		File file = new File(path);
		for(File a:file.listFiles()){
			list.add(a.getName());
			System.out.println(a.getName());
		}
		Collections.sort(list);
		return list;
		
	}
	
	public void printUserSize(){
		System.out.println(shareList.size());
	}
	
	public ArrayList<String> removeFile(String id, String filename){
		String path = "/home/john/Document/" + id + "/" + filename;
		File file = new File(path);
		System.out.println("delete : " + file.delete());
		if(shareList.containsKey(id)){
			addList(id);
		}
		
		return getList(id);
	}
	
	

}
