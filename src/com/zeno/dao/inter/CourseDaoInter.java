package com.zeno.dao.inter;

import java.util.List;

import com.zeno.bean.Course;

public interface CourseDaoInter extends BaseDaoInter {
	/**
	 * 获取课程信息
	 * 
	 * @param sql
	 * @param param
	 * @return
	 */
	public List<Course> getCourseList(String sql, List<Object> param);

}
