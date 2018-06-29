package com.zeno.dao.inter;

import java.util.List;

import com.zeno.bean.Exam;

public interface ExamDaoInter extends BaseDaoInter {

	public List<Exam> getExamList(String sql, List<Object> param);

}
