package com.zeno.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.zeno.dao.inter.BaseDaoInter;
import com.zeno.tools.MysqlTool;

/**
 * 基础Dao
 * 
 * @author Light
 *
 */
@SuppressWarnings("unchecked")
public class BaseDaoImpl implements BaseDaoInter {

	@Override
	public List<Object> getList(Class type, String sql) {
		QueryRunner qr = new QueryRunner(MysqlTool.getDataSource());
		List<Object> list = new LinkedList<>();
		try {
			list = qr.query(sql, new BeanListHandler<>(type));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Object> getList(Class type, String sql, Object[] param) {
		QueryRunner qr = new QueryRunner(MysqlTool.getDataSource());
		List<Object> list = new LinkedList<>();
		try {
			list = qr.query(sql, new BeanListHandler<>(type), param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Object> getList(Class type, String sql, List<Object> param) {
		Object[] params = new Object[param.size()];
		for (int i = 0; i < param.size(); i++) {
			params[i] = param.get(i);
		}
		return getList(type, sql, params);
	}

	@Override
	public List<Object> getList(Connection conn, Class type, String sql) {
		QueryRunner qr = new QueryRunner();
		List<Object> list = new LinkedList<>();
		try {
			list = qr.query(conn, sql, new BeanListHandler<>(type));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Object> getList(Connection conn, Class type, String sql, Object[] param) {
		QueryRunner qr = new QueryRunner();
		List<Object> list = new LinkedList<>();
		try {
			list = qr.query(conn, sql, new BeanListHandler<>(type));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Object> getList(Connection conn, Class type, String sql, List<Object> param) {
		Object[] params = new Object[param.size()];
		for (int i = 0; i < param.size(); i++) {
			params[i] = param.get(i);
		}
		return getList(conn, type, sql, params);
	}

	@Override
	public Object getObject(Class type, String sql, Object[] param) {
		QueryRunner qr = new QueryRunner(MysqlTool.getDataSource());
		Object obj = new LinkedList<>();
		try {
			obj = qr.query(sql, new BeanHandler(type), param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public Object getObject(Connection conn, Class type, String sql, Object[] param) {
		QueryRunner qr = new QueryRunner();
		Object obj = new LinkedList<>();
		try {
			obj = qr.query(conn, sql, new BeanHandler(type), param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public Long count(String sql) {
		QueryRunner qr = new QueryRunner(MysqlTool.getDataSource());
		Long count = 0L;
		try {
			count = qr.query(sql, new ScalarHandler<>());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public Long count(String sql, Object[] param) {
		QueryRunner qr = new QueryRunner(MysqlTool.getDataSource());
		Long count = 0L;
		try {
			count = qr.query(sql, new ScalarHandler<>(), param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Long count(String sql, List<Object> param) {
		// 将集合中的参数封装到数组对象中
		Object[] params = new Object[param.size()];
		for (int i = 0; i < params.length; i++) {
			params[i] = param.get(i);
		}
		return count(sql, params);
	}

	@Override
	public void update(String sql, Object[] param) {
		QueryRunner qr = new QueryRunner(MysqlTool.getDataSource());
		try {
			qr.update(sql, param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(String sql, List<Object> param) {
		Object[] params = new Object[param.size()];
		for (int i = 0; i < params.length; i++) {
			params[i] = param.get(i);
		}
		update(sql, params);
	}

	@Override
	public void updateTransaction(Connection conn, String sql, Object[] param) throws SQLException {
		QueryRunner qr = new QueryRunner();
		qr.update(sql, param);
	}

	@Override
	public void updateBatch(String sql, Object[][] param) {
		insertBatch(sql, param);
	}

	@Override
	public void insert(String sql, Object[] param) {
		update(sql, param);
	}

	@Override
	public void insertTransaction(Connection conn, String sql, Object[] param) throws SQLException {
		updateTransaction(conn, sql, param);
	}

	@Override
	public int insertReturnKeys(String sql, Object[] param) {
		int key = 0;
		Connection conn = MysqlTool.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			if (param != null && param.length > 0) {
				for (int i = 0; i < param.length; i++) {
					ps.setObject(i + 1, param[i]);
				}
			}
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				key = rs.getInt(1);
			}
			//ע这里不关闭连接：一般获取主键后，还会做下一步操作
//			MysqlTool.closeConnection();
//			MysqlTool.close(rs);
//			MysqlTool.close(ps);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return key;
	}

	@Override
	public int insertReturnKeysTransaction(Connection conn, String sql, Object[] param) throws SQLException {
		int key = 0;
		PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				ps.setObject(i+1, param[i]);
			}
		}
		return key;
	}

	@Override
	public void insertBatch(String sql, Object[][] param) {
		QueryRunner runner = new QueryRunner(MysqlTool.getDataSource());
		try {
			runner.batch(sql, param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertBatchTransaction(Connection conn, String sql, Object[][] param) throws SQLException {
		QueryRunner runner = new QueryRunner();
		try {
			runner.batch(conn, sql, param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(String sql, Object[] param) {
		update(sql, param);
	}

	@Override
	public void deleteTransaction(Connection conn, String sql, Object[] param) throws SQLException {
		updateTransaction(conn, sql, param);
	}

	@Override
	public void deleteTransaction(Connection conn, String sql, List<Object> param) throws SQLException {
		Object[] params = new Object[param.size()];
		for (int i = 0; i < params.length; i++) {
			params[i] = param.get(i);
		}
	}

	@Override
	public void deleteBatchTransaction(Connection conn, String sql, Object[][] param) {
		QueryRunner runner = new QueryRunner();
		try {
			runner.batch(conn, sql, param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> getColumn(String sql, Object[] param) {
		List<String> list = new LinkedList<>();
		try {
			Connection conn = MysqlTool.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			if (param != null && param.length > 0) {
				for (int i = 0; i < param.length; i++) {
					ps.setObject(i+1, param[i]);
				}
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String account = rs.getString(1);
				list.add(account);
			}
			// 关闭连接
			MysqlTool.closeConnection();
			MysqlTool.close(ps);
			MysqlTool.close(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
