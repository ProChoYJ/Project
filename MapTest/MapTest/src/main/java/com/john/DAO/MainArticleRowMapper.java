package com.john.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.john.model.MainArticle;

public class MainArticleRowMapper implements RowMapper<MainArticle>{

	@Override
	public MainArticle mapRow(ResultSet rs, int row)
			throws SQLException {
		System.out.println("Row : " + row);
		MainArticle ps = new MainArticle();
		ps.setArt_title(rs.getString("art_title"));
		ps.setArt_date(rs.getString("art_date"));
		return ps;
	}

}
