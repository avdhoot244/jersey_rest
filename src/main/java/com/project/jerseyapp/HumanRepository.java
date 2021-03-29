package com.project.jerseyapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

public class HumanRepository {

	final static Logger logger = Logger.getLogger(HumanRepository.class);

	Connection con;

	public HumanRepository() {
		String url = "jdbc:mysql://localhost:3306/humans_database";
		String username = "root";
		String pwd = "root";
		logger.info("Creating Connection Object");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, username, pwd);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	public Response getHumans() {
		List<Human> humans = new ArrayList<Human>();
		String sql = "select * from humans";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				Human human = new Human();
				human.sethId(rs.getInt("h_id"));
				human.setFirstName(rs.getString("first_name"));
				human.setLastName(rs.getString("last_name"));
				human.setDob(rs.getString("dob"));
				human.setCity(rs.getString("city"));
				human.setState(rs.getString("state"));
				humans.add(human);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			Map<String, String> errorMap = new HashMap<String, String>();
			errorMap.put("message", e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMap).build();
		}
		if (humans.isEmpty()) {
			logger.info("No humans are present");
			Map<String, String> errorMap = new HashMap<String, String>();
			errorMap.put("message", "No humans are present");
			return Response.status(Response.Status.NO_CONTENT).entity(errorMap).build();
		}
		return Response.status(Response.Status.OK).entity(humans).build();
	}

	public Response createHuman(Human human) {
		String sql = "insert into humans (first_name,last_name,dob,city,state) values (?,?,?,?,?)";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, human.getFirstName());
			ps.setString(2, human.getLastName());
			ps.setString(3, human.getDob());
			ps.setString(4, human.getCity());
			ps.setString(5, human.getState());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			Map<String, String> errorMap = new HashMap<String, String>();
			errorMap.put("message", e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMap).build();
		}
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("message", "Successfully Added one Human");
		return Response.status(Response.Status.OK).entity(responseMap).build();
	}
}
