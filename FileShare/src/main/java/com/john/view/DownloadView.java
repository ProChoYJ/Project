package com.john.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.AbstractView;

public class DownloadView extends AbstractView{
	
	public DownloadView(){
		setContentType("application/download; charset=utf-8");
	}
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		MultipartFile file = (MultipartFile)model.get("downloadFile");
		
		response.setContentType(getContentType());
		response.setContentLength((int)file.getSize());
		
		String userAgent = request.getHeader("User-Agent");
		boolean ie = userAgent.indexOf("MSIE") > -1;
		String filename = null;
		if(ie){
			filename = URLEncoder.encode(file.getOriginalFilename(),"utf-8");
		}else{
			filename = new String(file.getOriginalFilename().getBytes("utf-8"),"iso-8859-1");
		}
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		OutputStream out = response.getOutputStream();
		InputStream fis = null;
		try{
			fis = file.getInputStream();
			FileCopyUtils.copy(fis, out);
		}finally{
			if(fis != null)
				try{
					fis.close();
				}catch(IOException ex){
				}
		}
		out.flush();
	}

}
