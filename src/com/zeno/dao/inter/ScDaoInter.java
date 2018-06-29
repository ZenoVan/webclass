package com.zeno.dao.inter;

import java.util.List;

import com.zeno.bean.Sc;

public interface ScDaoInter extends BaseDaoInter {
	/**
	 * 获取选课信息
	 * 
	 * @param sql
	 * @param param
	 * @return
	 */
	public List<Sc> getScList(String sql, List<Object> param);

}
