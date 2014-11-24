package com.john.pro1;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.john.DAO.ClientDao;
import com.john.models.Client;
import com.john.models.FileList;
import com.john.models.MyIP;
import com.john.models.User;

/**
 * Handles requests for the application home page.
 */

// map list (username, User)
// User -> Di reosurce create
// session login imfo
// when logout session, User destroy

@Controller
public class HomeController {

	@Resource(name = "clientDao")
	ClientDao cd;

	@Resource(name = "myIp")
	MyIP ip;

	//@Resource(name = "user")
	User user;
	
	@Resource(name = "fileList")
	FileList sFile;
	
	 // receiveId
	// tip.1 redirect => get method
	// Map create -> xml singleton
	// DownloadController -> Map creater?
	// map Homecon -> DownloadCon send?
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
		
//		if(session.getAttribute(request.getLocalAddr()) != null)
//			return "redirect:/login";
		return "home";
	}

	// login
	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	public String home(Client client, Model model, HttpSession session,
			HttpServletRequest request) {

		if (cd.validId(client)) {
			//class add
//			user = new User();
//			user.setID(client.getC_id());
//			user.setState("X");
//			user.setFileList(new LinkedHashMap<String, MultipartFile>());
			System.out.println("login : " + client.getC_id());
			//session add
			session.setAttribute("ID", client.getC_id());
			session.setAttribute("state", "X");
			session.setAttribute("fileList", new LinkedHashMap<String, MultipartFile>());
			session.setAttribute("sharedId", null);
			
			model.addAttribute("fList", ((LinkedHashMap<String,MultipartFile>)session.getAttribute("fileList")).keySet());
			model.addAttribute("ID", session.getAttribute("ID"));
			model.addAttribute("state", session.getAttribute("state"));
			//ip
//			session.setAttribute(request.getLocalAddr(), user.getID());
//			model.addAttribute("se", session.getAttribute(request.getLocalAddr()));
			
			session.setAttribute("se", client.getC_id());

//			System.out.println("User : " + user.getID());
		} else
			model.addAttribute("ID", "no");

		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String hom(Model model,HttpSession session, HttpServletRequest request) {
		// class add
//		model.addAttribute("fList", user.getFileList().keySet());
//		model.addAttribute("ID", user.getID());
//		model.addAttribute("state", user.getState());
//		
		//session add
		model.addAttribute("fList", ((LinkedHashMap<String,MultipartFile>)session.getAttribute("fileList")).keySet());
		model.addAttribute("ID", session.getAttribute("ID"));
		model.addAttribute("state", session.getAttribute("state"));
		
		//System.out.println("se : " + session.getAttribute(request.getLocalAddr()));
		if((String)session.getAttribute("sharedId") != null){
			model.addAttribute("receiveid", session.getAttribute("sharedId"));
			model.addAttribute("receiveFile", 
					((LinkedHashMap<String, MultipartFile>)sFile.getUser((String)session.getAttribute("sharedId"))).keySet());
		}else{
			model.addAttribute("receiveid", "");
			model.addAttribute("receiveFile", "");
		}
		
		return "login";
	}

	// logout
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(HttpSession session,HttpServletRequest request) {
		// session.removeAttribute("se");
		// if(session.getAttribute("se") == null)
		// System.out.println("removed");

		sFile.removeShare((String)session.getAttribute("ID"));
		session.invalidate();
		
		//System.out.println("se : " + session.getAttribute(request.getLocalAddr()));
//		user.init();
		return "redirect:/";
	}
	
	// file

	@RequestMapping(value = "/login/fileadd", method = RequestMethod.POST)
	public String fileadd(@RequestParam("file") MultipartFile mf, Model model, HttpSession session) {
//		user.getFileList().put(mf.getOriginalFilename(), mf);
		LinkedHashMap<String, MultipartFile> tmp = (LinkedHashMap<String, MultipartFile>)session.getAttribute("fileList");
		tmp.put(mf.getOriginalFilename(), mf);
		
		session.setAttribute("fileList", tmp);
		
//		System.out.println(mf.getOriginalFilename());
//		System.out.println(user.getFileList().keySet());
		
//		model.addAttribute("fList", user.getFileList().keySet());
		
		return "redirect:/login";

	}

	// session share modelandview
	// session add
	@RequestMapping(value = "/login/shared", method = RequestMethod.POST)
	public ModelAndView share(HttpServletResponse respone, HttpSession session) throws IOException {
		System.out.println("shared");
		ModelAndView mvc = new ModelAndView();
		RedirectView rv = new RedirectView();
		String url = "/pro1/login";
//		user.setState("O");
		session.setAttribute("state", "O");
		mvc.addObject("state", "O");
		// share
		sFile.addShare((String)session.getAttribute("ID"), 
				(LinkedHashMap<String, MultipartFile>)session.getAttribute("fileList"));
		
		rv.setExposeModelAttributes(false);
		rv.setUrl(url);
		mvc.setView(rv);
		// respone.sendRedirect("/");
		return mvc;
	}

	// session remove
	@RequestMapping(value = "/login/cancel", method = RequestMethod.POST)
	public ModelAndView cancel(HttpSession session, HttpServletResponse respone) throws IOException {
		System.out.println("cancel");
		ModelAndView mvc = new ModelAndView();
		RedirectView rv = new RedirectView();
		String url = "/pro1/login";
//		user.setState("X");
		session.setAttribute("state", "X");
		mvc.addObject("state", "X");
		sFile.removeShare((String)session.getAttribute("ID"));
//		session.removeAttribute(user.getID()); //remove file session
		rv.setExposeModelAttributes(false);
		rv.setUrl(url);
		mvc.setView(rv);
		// respone.sendRedirect("/");
		return mvc;
	}
	
	// receive file acc
	@RequestMapping(value = "/login/receive", method = RequestMethod.POST)
	public String receive(String receiveId, Model model, HttpSession session){
//		user.setShareId(receiveId);
		session.setAttribute("sharedId", receiveId);
		System.out.println("reID : " + receiveId);
		return "redirect:/login";
	}
	
	// receive cut off
	@RequestMapping(value = "/login/cutoff", method = RequestMethod.POST)
	public String cutoff(HttpSession session){
		session.setAttribute("sharedId", null);
		return "redirect:/login";
	}
}
