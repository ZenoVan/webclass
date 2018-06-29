package com.zeno.service;

import java.util.List;

import com.zeno.bean.User;
import com.zeno.dao.impl.UserDaoImpl;
import com.zeno.dao.inter.UserDaoInter;

import net.sf.json.JSONArray;

public class UserService {

	UserDaoInter dao = new UserDaoImpl();

	/**
	 * 获取所有帐号
	 * 
	 * @return
	 */
	public String getAccountList() {
		// 获取数据
		List<String> list = dao.getColumn("SELECT account FROM user", null);
		// json化
		String result = JSONArray.fromObject(list).toString();

		return result;
	}

	/**
	 * 登录验证
	 * 
	 * @param user
	 * @return
	 */
	public User getAdmin(User user) {
		User searchUser = (User) dao.getObject(User.class,
				"SELECT * FROM user WHERE account=? AND password=? AND type=?",
				new Object[] { user.getAccount(), user.getPassword(), user.getType() });

		return searchUser;
	}

	/**
	 * 修改密码
	 * 
	 * @param user
	 */
	public void editPassword(User user) {
		dao.update("UPDATE user SET password=? WHERE account=?",
				new Object[] { user.getPassword(), user.getAccount() });
	}

	/**
	 * 修改任一项数据
	 * 
	 * @param user
	 * @param name
	 * @param value
	 * @return
	 */
	public User editUser(User user, String name, String value) {
		User newUser = null;
		boolean isAccountAlreadyExists = false;
		if (name.equalsIgnoreCase("account")) {
			String tempAccount = (String) dao.getObject(User.class, "SELECT account FROM user WHERE account=?",
					new Object[] { value });
			List<Object> accountList = dao.getList(User.class, "SELECT account FROM user");
			for (int i = 0; i < accountList.size(); i++) {
				if (tempAccount.equals(accountList.get(i).toString())) {
					isAccountAlreadyExists = true;
					break;
				}
			}
		}
		if (!isAccountAlreadyExists) {
			dao.update("UPDATE user SET " + name + " =? WHERE account=?", new Object[] { value, user.getAccount() });
			newUser = (User) dao.getObject(User.class, "SELECT * FROM user WHERE account=?",
					new Object[] { user.getAccount() });
		}
		return newUser;
	}

}
