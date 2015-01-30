package com.john.DAO;

import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.john.model.SubArticle;

public class SubArticleDAO {
	private JdbcTemplate template;
	
	public SubArticleDAO(DataSource dataSource){
		this.template = new JdbcTemplate(dataSource);
	}
	
	public List<SubArticle> select(String key){
		String sql = "select * from sub_art where sub_date = '" + key + "'";
		List<SubArticle> list = null;
		try{
			list = template.query(sql, new Object[]{}, new SubArticleRowMapper());
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("sub art error");
			return null;
		}
		
		return list;
		
	}
	
	public void insert(HashMap<String, Object> param){
		String sql = "insert into sub_art(sub_date, sub_a, sub_b, sub_content, sub_image) values (?,?,?,?,?)";
		int row = template.update(sql, param.get("sub_date"), param.get("sub_a"), param.get("sub_b"), param.get("sub_content"),param.get("sub_image"));
		System.out.println("insert Sub : " + row);
	}

}
