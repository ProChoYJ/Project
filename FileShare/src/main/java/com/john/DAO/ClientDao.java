package com.john.DAO;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.john.models.Client;

public class ClientDao {
	private JdbcTemplate template;
	
	public ClientDao(DataSource dataSource){
		this.template = new JdbcTemplate(dataSource);
	}
	
	public boolean validId(Client client){
	
		if(getClient(client) == null){
			return false;
		}else{
			return true;
		}
		
	}
	
	public Client getClient(Client client){
		
		String sql = "select * from Client where c_id = '" + client.getC_id() +
				"' and c_password = '" + client.getC_password() + "'";
		Client tmp = null;
		try{
			tmp = template.queryForObject(sql, new Object[]{}, new ClientRowMapper());	
		}catch(Exception ex){
			return null;
		}
		
		return tmp;
		
	}
	
}
