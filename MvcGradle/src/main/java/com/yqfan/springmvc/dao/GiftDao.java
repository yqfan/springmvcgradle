package com.yqfan.springmvc.dao;

import java.util.Collection;

import com.yqfan.springmvc.model.Gift;


public interface GiftDao {
	public void insert(Gift gift);
	public Gift findById(long id);
	public Collection<Gift> findByTitle(String title);
	public Collection<Gift> getAll();
}
