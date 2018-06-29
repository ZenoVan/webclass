package com.zeno.dao.inter;

import java.util.List;

import com.zeno.bean.Student;

public interface StudentDaoInter extends BaseDaoInter {
	/**
	 * 获取学生信息
	 * 
	 * @param sql
	 * @param param
	 * @return
	 */
	public List<Student> getStudentList(String sql, List<Object> param);

}
