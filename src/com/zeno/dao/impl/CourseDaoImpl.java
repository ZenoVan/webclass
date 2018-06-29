package com.zeno.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.zeno.bean.Course;
import com.zeno.dao.inter.CourseDaoInter;
import com.zeno.tools.MysqlTool;

public class CourseDaoImpl extends BaseDaoImpl implements CourseDaoInter {

	@Override
	public List<Course> getCourseList(String sql, List<Object> param) {
		List<Course> list = new LinkedList<>();
		try {
			Connection conn = MysqlTool.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			if (param != null && param.size() > 0) {
				for (int i = 0; i < param.size(); i++) {
					ps.setObject(i+1, param.get(i));
				}
			}
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			while(rs.next()){
				Course cou = new Course();
				for (int i = 0; i < meta.getColumnCount(); i++) {
					String field = meta.getColumnTypeName(i+1);
					BeanUtils.setProperty(cou, field, rs.getObject(field));
				}
				list.add(cou);
			}
			// 关闭连接
			MysqlTool.close(conn);
			MysqlTool.close(ps);
			MysqlTool.close(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
