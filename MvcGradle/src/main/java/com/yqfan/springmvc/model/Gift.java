package com.yqfan.springmvc.model;

public class Gift {
	long id;
	String title;
	String description;
	String dataUrl;
	String oriName;
	long touchCount;
	
	public Gift(String t, String d) {
		title = t;
		description = d;
		id = 0;
		dataUrl = "";
		oriName = "";
		touchCount = 0;
	}
	
	public void setId(long x) {
		this.id = x;
	}
	
	public long getId() {
		return this.id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setDataUrl(String s) {
		this.dataUrl = s;
	}
	
	public String getDataUrl() {
		return this.dataUrl;
	}
	
	public void setOriName(String s) {
		this.oriName = s;
	}
	
	public String getOriName() {
		return oriName;
	}
	
	public long getTouchCount() {
		return touchCount;
	}
	
	public void setTouchCount(long touchCount) {
		this.touchCount = touchCount;
	}
}
