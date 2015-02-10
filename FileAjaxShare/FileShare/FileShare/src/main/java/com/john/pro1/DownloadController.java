package com.john.pro1;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.john.models.FileList;
import com.john.models.User;


@Controller
public class DownloadController implements ApplicationContextAware{

	private WebApplicationContext context = null;
	
	@Resource(name = "fileList")
	FileList sFile;
	
	//download/file/{filename}
	// add{/filename}/
	@RequestMapping("/{name}/{filename}/file")
	public ModelAndView download(@PathVariable("name")String name,@PathVariable("filename") String filename, HttpSession session) throws Exception{
		System.out.println("filename : " + filename);
		System.out.println("receive name : " + name);
		File downloadFile = getFile(name,filename);
		return new ModelAndView("download","downloadFile",downloadFile);
	}
	
	private File getFile(String name,String filename){
		String path = "/home/john/Document/" + name + "/" + filename;
		return new File(path);
	}
	
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = (WebApplicationContext)applicationContext;
		
	}

}
