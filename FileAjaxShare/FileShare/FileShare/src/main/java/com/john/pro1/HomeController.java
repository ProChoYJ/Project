package com.john.pro1;

import java.io.File;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.john.DAO.ClientDao;
import com.john.models.Client;
import com.john.models.FileList;
import com.john.models.MyIP;

/**
 * Handles requests for the application home page.
 */


@Controller
public class HomeController {

	@Resource(name = "clientDao")
	ClientDao cd;

	@Resource(name = "myIp")
	MyIP ip;

	
	@Resource(name = "fileList")
	FileList sFile;
	
	
	//
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	// home
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("addr : " + request.getLocalAddr());
		System.out.println("name : " + request.getLocalName());
		System.out.println("port : " + request.getLocalPort());
		
		return "home";
	}

	// login
	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	public String home(Client client, Model model, HttpSession session,
			HttpServletRequest request) {

		if (cd.validId(client)) {
			
			System.out.println("login : " + client.getC_id());
			//session add
			session.setAttribute("ID", client.getC_id());
			session.setAttribute("state", "X");
			session.setAttribute("sharedId", null);
			
		} else
			return "redirect:/";

		return "redirect:/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String hom(Model model,HttpSession session, HttpServletRequest request) {
		model.addAttribute("ID", (String)session.getAttribute("ID"));
//		model.addAttribute("filelist", sFile.getList((String)session.getAttribute("ID")));
		return "login";
	}

	// logout
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(HttpSession session) {

		sFile.removeList((String)session.getAttribute("ID"));
		
		session.invalidate();
		return "redirect:/";
	}
	
	// file
	//none return 
	@ResponseBody
	@RequestMapping(value = "/login/fileadd", method = RequestMethod.POST)
	public HashMap<String, Object> fileadd(@RequestParam("file") MultipartFile param, HttpSession session) throws Exception {
		System.out.println((String)session.getAttribute("ID"));
		System.out.println(param.getContentType());
		HashMap<String,Object> json = new HashMap<String,Object>();
		String id = (String)session.getAttribute("ID");
		String path = "/home/john/Document/" + id 
				+ "/" + param.getOriginalFilename();
		File file = new File(path);
		
		param.transferTo(file);
		
		json.put("ID", id);
		json.put("list", sFile.getList((String)session.getAttribute("ID")));
		return json;
		

	}

	// add list
	@ResponseBody
	@RequestMapping(value = "/login/share", method = RequestMethod.POST, headers="Content-Type=application/json")
	public HashMap<String, Object> share(@RequestBody HashMap<String,Object> param, HttpSession session) throws Exception {
		sFile.addList((String)session.getAttribute("ID"));
		sFile.printUserSize();
		return param;
		
	}
	// remove list
	@ResponseBody
	@RequestMapping(value = "/login/cancel", method = RequestMethod.POST, headers="Content-Type=application/json")
	public HashMap<String, Object> cancel(@RequestBody HashMap<String, Object> param, HttpSession session) throws Exception {
		sFile.removeList((String)session.getAttribute("ID"));
		sFile.printUserSize();
		return param;
	}
	
	// receive file acc
	@ResponseBody
	@RequestMapping(value = "/login/receive", method = RequestMethod.POST, headers="Content-Type=application/json")
	public HashMap<String, Object> receive(@RequestBody HashMap<String, Object> param, HttpSession session, Model model){
		model.addAttribute("reID", (String)param.get("receiveId"));
		System.out.println("reID : " + param.get("receiveId"));
		param.put("list", sFile.getReceiveList((String)param.get("ID")));
		return param;
	}
	
	// receive cut off
	@RequestMapping(value = "/login/cutoff", method = RequestMethod.POST)
	public String cutoff(HttpSession session){
		session.setAttribute("sharedId", null);
		return "redirect:/login";
	}
	
	@ResponseBody
	@RequestMapping(value="/login/list", method=RequestMethod.POST, headers="Content-Type=application/json")
	public HashMap<String,Object> fileList(@RequestBody HashMap<String, Object> param, HttpSession session){
		System.out.println(param.get("request"));
		String id = (String)session.getAttribute("ID");
		param.put("ID", id);
		param.put("list", sFile.getList((String)session.getAttribute("ID")));
		return param;
	}
	
	@ResponseBody
	@RequestMapping(value="/login/delete", method=RequestMethod.POST, headers="Content-Type=application/json")
	public HashMap<String, Object> deleteFile(@RequestBody HashMap<String, Object> param, HttpSession session){
		param.put("ID", (String)session.getAttribute("ID"));
		param.put("list", sFile.removeFile((String)session.getAttribute("ID"), (String)param.get("filename")));
		
		return param;
	}
	
	
}
