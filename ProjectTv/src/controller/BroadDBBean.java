package controller;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class BroadDBBean {
	private static BroadDBBean instance = new BroadDBBean();
	private static HashMap<String,String> map = new HashMap<String,String>();
	private String jdbc_driver = "com.mysql.jdbc.Driver";
	private String jdbc_url = "jdbc:mysql://localhost:3306/mydb";
	private String[] channel = {"ebs", "kbs1", "kbs2", "mbc", "sbs"};
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private PreparedStatement pstmt2 = null;
	public static BroadDBBean getInstance(){
		return instance;
	}
	
	public static HashMap<String,String> getchannel(){
		return map;
	}
	private BroadDBBean(){
	}
	
	private void connect(){
		try{
			System.out.println("be connect.....");
			Class.forName(jdbc_driver);
			System.out.println("be connect.....");
			conn = DriverManager.getConnection(jdbc_url, "root","webservice");
			System.out.println("connect");
		}catch(Exception e){}
	}
	
	private void disconnect(){
		try{
			pstmt.close();
			conn.close();
		}catch(Exception e){
		}
	}
	
	public void setLog(String filename)throws Exception{
		Scanner s = new Scanner(new File(filename));
		System.out.println(filename);
		String day = "", name = "", chname = "";
		String pattern = "yyy-MM-dd HH:mm:ss";
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat(pattern);
		//insert into ? values (?,?,?)
		String sql = "";
		int count = 0;
		System.out.println("test");
		try{
			connect();
			
			System.out.println("test2");
	//		
			Date inDate = null;
			
			while(s.hasNextLine()){
				String line = s.nextLine();
				if(line.equals("@")){
					chname = s.nextLine();
					count = 0;
					System.out.println("testok " + chname);
					sql = "insert into " + chname + " values(?,?,?)";
					pstmt = conn.prepareStatement(sql);
				}else{
				count += 1;
				System.out.println(line); // 확인
				Scanner s2 = new Scanner(line).useDelimiter(",");
				day = s2.next(); name = s2.next();
				inDate = df.parse(day);
				cal.setTime(inDate);
				
				pstmt.setInt(1, count);
				pstmt.setString(2, "" + cal.getTimeInMillis());
				pstmt.setString(3, name);
				pstmt.executeUpdate();
				
				}
				
				// 디비 다 넣고 추출 
				getChannel(chname);
			}
		}catch(Exception e){
		}finally{
			disconnect();
			System.out.println("disconnect");
		}
	
	}
	
	public void addChannel(String bro, String name){
		map.put(bro, name);
	}
	
	public void getChannel(String bro){
		//select program from ? where id = (select count(*) from ? where ptime <= ?)
		String sql = "";
		Calendar today = Calendar.getInstance();
		ResultSet rs = null;
		String result = "";
		try{
			connect();
			sql = "select program from " + bro + " where id = (select count(*) from " + bro +
					" where ptime <= ?)";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setLong(1, today.getTimeInMillis());
			rs = pstmt2.executeQuery();
			
			if(rs.next()){
				result = rs.getString(1);
			}
			
			addChannel(bro,result);
			System.out.println(result);
			
		}catch(Exception e){
		}
	}
	
	public void resetChannel(){
		String sql = "";
		Calendar today = Calendar.getInstance();
		ResultSet rs = null;
		String result = "";
		try{
			connect();
			for(int i=0; i<channel.length; i++){
				sql = "select program from " + channel[i] + " where id = (select count(*) from " + channel[i] +
						" where ptime <= ?)";
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setLong(1, today.getTimeInMillis());
				rs = pstmt2.executeQuery();
				
				if(rs.next()){
					result = rs.getString(1);
				}
				addChannel(channel[i],result);
				System.out.println(result);
			}
		}catch(Exception e){
		}
	}
}
