package com.zeno.bean;

public class User {
	public static final int TYPE_ADMIN = 1;
	public static final int TYPE_STUDENT = 2;

	private String account;
	private String password;
	private String name;
	private int type;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public static int getTypeAdmin() {
		return TYPE_ADMIN;
	}

	public static int getTypeStudent() {
		return TYPE_STUDENT;
	}

}
