package com.zeno.service;

import java.util.LinkedList;
import java.util.List;

import com.zeno.bean.Exam;
import com.zeno.dao.impl.ExamDaoImple;
import com.zeno.dao.inter.ExamDaoInter;

import net.sf.json.JSONArray;

public class ExamService {
	
	private ExamDaoInter dao;
	
	public ExamService() {
		dao = new ExamDaoImple();
	}
	
	/**
	 * 获取某个学生考试列表
	 * @param number
	 * @return
	 */
	public String studentExamList(String account) {
		
		String sql = "SELECT cname, course.cno, teacher, grade FROM sc, course WHERE sc.cno=course.cno AND sno=" + account;
		
		List<Object> param = new LinkedList<>();
		
		//获取数据
		List<Exam> list = dao.getExamList(sql, param);
		
		//格式化Map,以json格式返回数据
        String result = JSONArray.fromObject(list).toString();
		
		return result;
	}
	
}















