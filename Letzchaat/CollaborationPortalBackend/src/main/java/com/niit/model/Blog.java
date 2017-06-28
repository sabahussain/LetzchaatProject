package com.niit.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Entity
@Component
public class Blog extends Error{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int blog_id;
	private String blogTitle;
	private String description;
	private Date date_of_creation;
	private String username;
	private char status;
	private String reason;
	
	
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getBlog_id() {
		return blog_id;
	}
	public void setBlog_id(int blog_id) {
		this.blog_id = blog_id;
	}
	public String getBlogTitle() {
		return blogTitle;
	}
	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate_of_creation() {
		return date_of_creation;
	}
	public void setDate_of_creation(Date date_of_creation) {
		this.date_of_creation = date_of_creation;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "Blog [blog_id=" + blog_id + ", blogTitle=" + blogTitle + ", description=" + description
				+ ", date_of_creation=" + date_of_creation + ", username=" + username + "]";
	}
	
	
	
}
