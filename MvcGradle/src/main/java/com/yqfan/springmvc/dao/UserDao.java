package com.yqfan.springmvc.dao;

import com.yqfan.springmvc.model.User;


public interface UserDao {
	public void insert(User user);
	public User findByName(String userName);
}
