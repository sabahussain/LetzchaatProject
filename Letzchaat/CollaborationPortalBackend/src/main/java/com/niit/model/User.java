package com.niit.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
@Entity
@Component
public class User extends Error {
	@Id
	private String username;
	private String name;
	private String email;
	private String password;
	private String mobile;
	private String address;
	//n-new,r-reject,a-accept
	private char status;
	//student,alumni,admin,employee
	private String role;
	private String reason;
	//y-online, n-offline
	private char isonline;
	
	@Transient
	private MultipartFile file;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public char getIsonline() {
		return isonline;
	}
	public void setIsonline(char isonline) {
		this.isonline = isonline;
	}
	
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", mobile=" + mobile + ", address=" + address + ", status=" + status + ", role=" + role + ", reason="
				+ reason + ", isonline=" + isonline + ", file=" + file + "]";
	}
	

}
