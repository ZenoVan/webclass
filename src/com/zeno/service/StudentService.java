package com.zeno.service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.zeno.bean.Student;
import com.zeno.bean.User;
import com.zeno.dao.impl.StudentDaoImpl;
import com.zeno.dao.inter.StudentDaoInter;
import com.zeno.tools.MysqlTool;
import com.zeno.tools.StringTool;

import net.sf.json.JSONObject;

public class StudentService {

	private StudentDaoInter dao;

	public StudentService() {
		dao = new StudentDaoImpl();
	}

	/**
	 * 修改学生信息
	 * 
	 * @param student
	 */
	public void editStudent(Student student) {
		String sql = "UPDATE student SET sno=?, sname=?, sex=?, age=?, sdept=?";
		List<Object> params = new LinkedList<>();
		params.add(student.getSno());
		params.add(student.getSname());
		params.add(student.getSex());
		params.add(student.getAge());
		params.add(student.getSdept());

		// 更新学生信息
		dao.update(sql, params);
	}

	/**
	 * 删除学生
	 * 
	 * @param numbers
	 * @throws Exception
	 */
	public void deleteStudent(String[] numbers) throws Exception {
		String mark = StringTool.getMark(numbers.length);
		Connection conn = MysqlTool.getConnection();
		MysqlTool.startTransaction();

		try {
			// 删除学生
			dao.deleteTransaction(conn, "DELETE FROM student WHERE sno IN(" + mark + ")", numbers);
			// 删除相对于学生用户
			dao.deleteTransaction(conn, "DELETE FROM user WHERE account IN(" + mark + ")", numbers);

			// 提交事务
			MysqlTool.commit();
		} catch (Exception e) {
			// 回滚事务
			MysqlTool.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			MysqlTool.closeConnection();
		}
	}

	/**
	 * 添加学生，顺带添加相对应的学生登录帐号
	 * 
	 * @param student
	 */
	public void addStudent(Student student) {
		// 添加学生
		dao.insert("INSERT INTO student(sno, sname, sex, age, sdept) value(?,?,?,?,?)", new Object[] { student.getSno(),
				student.getSname(), student.getSex(), student.getAge(), student.getSdept() });
		// 添加登录用户，默认密码为学号
		dao.insert("INSERT INTO user(account, password, name, type) value(?,?,?,?)",
				new Object[] { student.getSno(), student.getSno(), student.getSname(), User.TYPE_STUDENT });
	}

	/**
	 * 获取所有学生信息
	 * 
	 * @param student
	 * @return
	 */
	public String getStudentList(Student student) {
		StringBuffer sb = new StringBuffer("SELECT * FROM student ");
		List<Object> param = new LinkedList<>();
		if (student != null) {
			if (student.getSdept() != null) {
				String sdept = student.getSdept();
				param.add(sdept);
				sb.append("WHERE sdept=?");
			}
		}
		sb.append("ORDER BY sno DESC");
		String sql = sb.toString();

		List<Student> list = dao.getStudentList(sql, param);
		long total = getCount(student);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", total);
		jsonMap.put("rows", list);
		String result = JSONObject.fromObject(jsonMap).toString();
		return result;
	}

	/**
	 * 获取学生人数
	 * 
	 * @param student
	 * @return
	 */
	private long getCount(Student student) {
		StringBuffer sb = new StringBuffer("SELECT * FROM student ");
		List<Object> param = new LinkedList<>();
		if (student != null) {
			if (student.getSdept() != null) {
				String sdept = student.getSdept();
				param.add(sdept);
				sb.append("WHERE sdept=?");
			}
		}

		String sql = sb.toString();
		// long count = dao.count(sql).intValue();
		long count = dao.count(sql, param);

		return count;
	}

	/**
	 * 获取跟学生一个班的班级同学
	 * 
	 * @param account
	 * @param page
	 * @return
	 */
	public String getStudentList(String account) {

		Student student = (Student) dao.getObject(Student.class, "SELECT * FROM student WHERE sno=?",
				new Object[] { account });

		return getStudentList(student);
	}

	/**
	 * 获取学生详细信息
	 * 
	 * @param account
	 * @return
	 */
	public Student getStudent(String account) {

		Student student = dao.getStudentList("SELECT * FROM student WHERE sno=" + account, null).get(0);

		return student;
	}

}
