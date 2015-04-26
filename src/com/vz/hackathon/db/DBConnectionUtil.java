package com.vz.hackathon.db;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.vz.hackathon.util.LogUtil;


public class DBConnectionUtil {
	Context initContext;
	Context envContext;
	DataSource ds;
	private static DBConnectionUtil instance = new DBConnectionUtil();
	private DBConnectionUtil(){
		try {
			initContext = new InitialContext();
			envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/hack2");
		} catch (NamingException e) {
			LogUtil.logInfo("DBConnectionUtil", "DBConnectionUtil", e.getMessage());
			LogUtil.logTrace(e);
		}
	}
	public static DataSource getDataSource(){
		return instance.ds;
	}
	public static Connection getConection() throws SQLException{
		return instance.ds.getConnection();
	}
	
	public static void close(Statement st,ResultSet rs){
		if(null!=rs){
			try {
				rs.close();
			} catch (SQLException e) {
				LogUtil.logInfo("DBConnectionUtil", "close", e.getMessage());
			}
		}
		if(null!=st){
			try {
				st.close();
			} catch (SQLException e) {
				LogUtil.logInfo("DBConnectionUtil", "close", e.getMessage());
			}
		}
	}
	public static void closeConection(Connection con){
		if(null!=con){
		try {
			con.close();
		} catch (SQLException e) {
			LogUtil.logInfo("DBConnectionUtil", "closeConection", e.getMessage());
		}
		}
	}
}
