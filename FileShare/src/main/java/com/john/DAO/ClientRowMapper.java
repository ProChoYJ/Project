package com.john.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.john.models.Client;

public class ClientRowMapper implements RowMapper<Client>{

	@Override
	public Client mapRow(ResultSet rs, int row) throws SQLException {
		Client client = new Client();
		client.setC_id(rs.getString("c_id"));
		client.setC_password(rs.getString("c_password"));
		return client;
	}

}
