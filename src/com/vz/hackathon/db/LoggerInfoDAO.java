package com.vz.hackathon.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import com.vz.hackathon.logtracker.to.ConfigInfoTO;
import com.vz.hackathon.logtracker.to.ErrorMessageInfoTO;
import com.vz.hackathon.logtracker.to.GroupInfoTO;
import com.vz.hackathon.logtracker.to.GroupTypeCountInfoTO;
import com.vz.hackathon.logtracker.to.SearchResponseTO;

public class LoggerInfoDAO {
	
	private static HashMap<String, String> mapping = new HashMap<String, String>();
	private static HashMap<String, String> alertMapping = new HashMap<String, String>();
	
	static{
		loadMapping();
	}
	
	public String getGroup(String keyword){
		return mapping.get(keyword);
	}
	
	public String getAlertType(String keyword){
		return alertMapping.get(keyword);
	}

	public void persistLogDetails(SearchResponseTO response){
		System.out.println("inside persistLogDetails");
		Connection con=null;
		PreparedStatement ps = null;
		String error =response.getErrorLog();
		try {
			con = DBConnectionUtil.getConection();
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO LOGGER_INFO (SEARCH_KEY,DEPARTMENT,ALERT_TYPE,VERIFIED,ACTUAL_MESSAGE,TIME,MAIL_SENT) VALUES (?,?,?,?,?,?,?)");
			ps = con.prepareStatement(sb.toString());
				
			ps.setString(1,response.getKeyword());
			ps.setString(2,mapping.get(response.getKeyword()));
			ps.setString(3,response.getAlertType());
			ps.setString(4,"NO");
			ps.setString(5,error);
			ps.setDate(6,new Date(System.currentTimeMillis()));
			if(response.isMailSent()){
			ps.setString(7,"YES");
			}
			else{
			ps.setString(7,"NO");
			}
			
			
		
		ps.executeUpdate();
		System.out.println("completed persistLogDetails");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			DBConnectionUtil.close(ps, null);
			DBConnectionUtil.closeConection(con);
		}
	}
	
	public static void loadMapping(){
		mapping = new HashMap<String, String>();
		alertMapping=new HashMap<String, String>();
		Connection con=null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		
		try {
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DEPARTMENT,SEARCH_KEY,ALERT_TYPE FROM DEPT_KEY_MAPPING");
		
			con = DBConnectionUtil.getConection();
			ps = con.prepareStatement(sb.toString());
			
			rs=ps.executeQuery();
			while(rs.next()){
				mapping.put(rs.getString("SEARCH_KEY"), rs.getString("DEPARTMENT"));
				alertMapping.put(rs.getString("SEARCH_KEY"), rs.getString("ALERT_TYPE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			DBConnectionUtil.close(ps, null);
			DBConnectionUtil.closeConection(con);
		}
	}
	
	public Set<String> getKeys(){
		return mapping.keySet();
	}
	
	public String getGroupCountInfo(){
		Connection con=null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		List<GroupInfoTO> groups = new ArrayList<GroupInfoTO>();
		try {
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT count(1) as COUNT,DEPARTMENT FROM LOGGER_INFO WHERE VERIFIED='NO' GROUP BY DEPARTMENT");
		
			con = DBConnectionUtil.getConection();
			ps = con.prepareStatement(sb.toString());
			
			rs=ps.executeQuery();
			while(rs.next()){
				GroupInfoTO groupInfo = new GroupInfoTO();
				groupInfo.setCount(rs.getInt("COUNT"));
				groupInfo.setGroup(rs.getString("DEPARTMENT"));
				groups.add(groupInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			DBConnectionUtil.close(ps, rs);
			DBConnectionUtil.closeConection(con);
		}
		return convertToJson(groups);
	}
	
	public String getGroupInfo(String group){
		Connection con=null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		GroupTypeCountInfoTO response = new GroupTypeCountInfoTO();
		try {
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT count(1) AS COUNT ,ALERT_TYPE,DEPARTMENT FROM LOGGER_INFO WHERE DEPARTMENT=? AND VERIFIED='NO' GROUP BY DEPARTMENT ");
		
			con = DBConnectionUtil.getConection();
			ps = con.prepareStatement(sb.toString());
			ps.setString(1,group);
			
			rs=ps.executeQuery();
			while(rs.next()){
				String alertType=rs.getString("ALERT_TYPE");
				if(null!=alertType){
					if(alertType.trim().equals("ERROR")){
						response.setAlertCount(rs.getLong("COUNT"));
					}
					else if(alertType.trim().equals("ALERT")){
						response.setErrorCount(rs.getLong("COUNT"));
					}
					else if(alertType.trim().equals("WARN")){
						response.setWarningCount(rs.getLong("COUNT"));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			DBConnectionUtil.close(ps, rs);
			DBConnectionUtil.closeConection(con);
		}
		return convertToJson(response);
	}
	
	public String getGroupInfo(String group,String alertType){
		Connection con=null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		List<ErrorMessageInfoTO> errorMsgs = new ArrayList<ErrorMessageInfoTO>();
		try {
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT LOGGER_INFO_ID,ACTUAL_MESSAGE FROM LOGGER_INFO WHERE DEPARTMENT=? AND ALERT_TYPE=? AND VERIFIED='NO'");
		
			con = DBConnectionUtil.getConection();
			ps = con.prepareStatement(sb.toString());
			ps.setString(1,group);
			ps.setString(2,alertType);
			rs=ps.executeQuery();
			while(rs.next()){
				ErrorMessageInfoTO msgInfo = new ErrorMessageInfoTO();
				msgInfo.setErrorMessage(rs.getString("ACTUAL_MESSAGE"));
				msgInfo.setId(rs.getLong("LOGGER_INFO_ID"));
				errorMsgs.add(msgInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			DBConnectionUtil.close(ps, rs);
			DBConnectionUtil.closeConection(con);
		}
		return convertToJson(errorMsgs);
	}
	

	public void deleteErrorMessage(long id) {
		Connection con=null;
		PreparedStatement ps = null;
		try {
		
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE LOGGER_INFO SET VERIFIED='YES' WHERE LOGGER_INFO_ID=?");
		
			con = DBConnectionUtil.getConection();
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1,id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			DBConnectionUtil.close(ps, null);
			DBConnectionUtil.closeConection(con);
		}
	}
	
	

	public ConfigInfoTO getConfigValues() {
		ConfigInfoTO configInfo= new ConfigInfoTO();
		Connection con=null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		try {
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT MILLI,NANO,ERROR_CONFIG FROM CONFIG_INFO");
		
			con = DBConnectionUtil.getConection();
			ps = con.prepareStatement(sb.toString());
			
			rs=ps.executeQuery();
			if(rs.next()){
				configInfo.setMilli(rs.getLong("MILLI"));
				configInfo.setNano(rs.getInt("NANO"));
				configInfo.setErrorCount(rs.getInt("ERROR_CONFIG"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			DBConnectionUtil.close(ps, rs);
			DBConnectionUtil.closeConection(con);
		}
		return configInfo;
	}
	
	private String convertToJson(Object obj){
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			return ow.writeValueAsString(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<String> getFiles(){
		List<String> files = new ArrayList<String>();
		Connection con=null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		try {
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT FILE FROM GROUP_FILE_MAPPING");
		
			con = DBConnectionUtil.getConection();
			ps = con.prepareStatement(sb.toString());
			
			rs=ps.executeQuery();
			while(rs.next()){
				files.add(rs.getString("FILE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			DBConnectionUtil.close(ps, rs);
			DBConnectionUtil.closeConection(con);
		}
		return files;
	}
	
	

}
