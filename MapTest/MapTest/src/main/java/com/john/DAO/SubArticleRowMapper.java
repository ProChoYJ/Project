package com.john.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.john.model.SubArticle;

public class SubArticleRowMapper implements RowMapper<SubArticle>{

	@Override
	public SubArticle mapRow(ResultSet rs, int row) throws SQLException {
		SubArticle tmp = new SubArticle();
		System.out.println("sub row : " + row);
		tmp.setSub_date(rs.getString("sub_date"));
		tmp.setSub_a(rs.getString("sub_a"));
		tmp.setSub_b(rs.getString("sub_b"));
		tmp.setSub_content(rs.getString("sub_content"));
		tmp.setSub_image(rs.getString("sub_image"));
		
		return tmp;
	}

}
