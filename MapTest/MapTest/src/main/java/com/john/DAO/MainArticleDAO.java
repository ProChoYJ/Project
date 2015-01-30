package com.john.DAO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.john.model.MainArticle;

public class MainArticleDAO {
	private JdbcTemplate template;

	public MainArticleDAO(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}
	
	// select all main article
	public List<MainArticle> select(){
		String sql = "select * from main_art";
		List<MainArticle> list = null;
		try{
			list = template.query(sql, new Object[]{}, new MainArticleRowMapper());
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("error");
			return null;
			
		}
		return list;
	}
	
	// inset main article
	public void insert(String title, String date) {	
		String sql = "insert into main_art(art_title, art_date) values (?, ?)";
		
		int update = template.update(sql, title, date);
		System.out.println("inserted Count : " + update);
	
	}
	
	// update main article
	public void update(String title, String date){
		String sql = "update main_art set art_title = ? where art_date = ?";
		
		int update = template.update(sql, title, date);
		System.out.println("updated Count : " + update);
	}
	
	public void delete(String date){
		String sql = "delete from main_art where art_date = ?";
		String sub_sql = "delete from sub_art where sub_date = ?";
		int update = template.update(sql, date);
		int sub_update = template.update(sub_sql, date);
		System.out.println("delete Count : " + update);
		System.out.println("sub_delete Count : " + sub_update);
	}
	
	
	
	// create and get main article time => primary key , private
	public String getDate(){
		String time = "";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		time = sdf.format(date);
//		System.out.println(time);
		return time;
		
	}

}
