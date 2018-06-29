package com.zeno.tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class MysqlTool {
	private static ComboPooledDataSource dataSource = null;
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>(); 
	
	static {
		// 空参会自动加载src目录下c3p0-config.xml数据库配置文件
		dataSource = new ComboPooledDataSource();
	}
	
	/**
	 * 获取数据库
	 * @return DataSource
	 */
	public static DataSource getDataSource(){
		return dataSource;
	}
	
	/**
	 * 对获取数据库的方式进行包装
	 * 这里的包装，使c3p0池让每个线程(客户端)获得的是同一个连接，方便做b/s框架下的事务
	 * @return Connection
	 */
	public static Connection getConnection(){
		Connection conn = tl.get();
		try {
			if(conn == null)
				conn = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tl.set(conn);
		return conn;
	}
	
	/**
	 * 开始（再度包装，感觉效果是保证操作的原子性）
	 */
	public static void startTransaction(){
		Connection conn = getConnection();
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 回滚
	 */
	public static void rollback(){
		Connection conn = getConnection();
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 提交
	 */
	public static void commit(){
		Connection conn = getConnection();
		try {
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭Connection，并移除线程中的连接
	 */
	public static void closeConnection(){
		close(getConnection());
		tl.remove();
	}
	
	public static void close(Connection conn){
		try {
			if(conn != null)
				conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void close(Statement stm){
		try {
			if(stm != null)
				stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rs){
		try {
			if(rs != null)
				rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
