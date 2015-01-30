package com.john.mapTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.john.DAO.MainArticleDAO;
import com.john.DAO.SubArticleDAO;
import com.john.model.MainArticle;
import com.john.model.SubArticle;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@Resource(name="mainArticleDAO")
	MainArticleDAO mainArticleDAO;
	@Resource(name="subArticleDAO")
	SubArticleDAO subArticleDAO;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		model.addAttribute("realPATH", "/home/john");
//		List<MainArticle> list = null;
		System.out.println(mainArticleDAO.getDate());
		
//		try{
//			list = mainArticleDAO.select();
//			model.addAttribute("artList", list);
//			//System.out.println("not error");
////			System.out.println(list.get(0).getArt_title());
//		}catch(Exception ex){
//			ex.printStackTrace();
//			System.out.println("controller error");
//		}
		
		
		return "home";
	}
	
	
	// jsp page json -> controller
	// accept -> @RequestBody
	// controller -> jsp page json
	// @Responsbody
	@ResponseBody 
	@RequestMapping(value = "/", method = RequestMethod.POST, headers = "Content-Type=application/json")
	public List<MainArticle> home(@RequestBody HashMap<String, Object> param, Model model)throws Exception{
		List<MainArticle> list = new ArrayList<MainArticle>();
		System.out.println("ready : " + param.get("get"));
		
		try{
			list = mainArticleDAO.select();
//			model.addAttribute("artList", list);
			//System.out.println("not error");
//			System.out.println(list.get(0).getArt_title());
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("controller error");
		}
		
		return list;
	}
	
	
	
	
	@ResponseBody 
	@RequestMapping(value = "/addArticle", method = RequestMethod.POST, headers = "Content-Type=application/json")
	public HashMap<String, Object> echoArticle(@RequestBody HashMap<String, Object> param)throws Exception{
//		HashMap<String, Object> map = new HashMap<String, Object>();
		String date = mainArticleDAO.getDate();
		mainArticleDAO.insert((String)param.get("title"),date);
		param.put("date", date);
//		System.out.println(param.get("title"));
//		map.put("article", "hello");
			
		return param;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/delArticle", method = RequestMethod.POST, headers = "Content-Type=application/json")
	public HashMap<String, Object> deletArticle(@RequestBody HashMap<String, Object> param)throws Exception{
		mainArticleDAO.delete((String)param.get("date"));
		
		return param;
	}
	
	@ResponseBody
	@RequestMapping(value="/subArt", method = RequestMethod.POST, headers = "Content-Type=application/json")
	public List<SubArticle> subArt(@RequestBody HashMap<String, Object> param)throws Exception{
		List<SubArticle> list = null;
		
		try{
			list = subArticleDAO.select((String)param.get("key"));
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("subArt select error");
		}
		System.out.println("key : " + param.get("key"));
		
		return list;
		
	}
	
	@ResponseBody
	@RequestMapping(value="/addSubArt", method= RequestMethod.POST, headers = "Content-Type=application/json")
	public HashMap<String, Object> addSubArt(@RequestBody HashMap<String, Object> param)throws Exception{
		System.out.println(param);
		subArticleDAO.insert(param);
		
		return new HashMap<String,Object>();
	}
}
