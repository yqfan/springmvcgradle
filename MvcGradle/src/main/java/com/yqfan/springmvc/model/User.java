package com.yqfan.springmvc.model;

public class User {
	private String userName;
	private String passWord;
	private String role;
	 
    public String getUserName() {
        return userName;
    }
 
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getPassWord() {
    	return passWord;
    }
    
    public void setPassWord(String password) {
    	this.passWord = password;
    }
    
    public String getRole() {
    	return role;
    }
    
    public void setRole(String role) {
    	this.role = role;
    }
}
