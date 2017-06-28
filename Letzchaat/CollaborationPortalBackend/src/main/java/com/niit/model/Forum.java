package com.niit.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Component
@Entity
public class Forum extends Error{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int forum_id;
	private String username;
	private String forum_topic;
	private Date date_created;
	private String forum_question;
	
	public int getForum_id() {
		return forum_id;
	}
	public void setForum_id(int forum_id) {
		this.forum_id = forum_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getForum_topic() {
		return forum_topic;
	}
	public void setForum_topic(String forum_topic) {
		this.forum_topic = forum_topic;
	}
	public Date getDate_created() {
		return date_created;
	}
	public void setDate_created(Date date_created) {
		this.date_created = date_created;
	}
	public String getForum_question() {
		return forum_question;
	}
	public void setForum_question(String forum_question) {
		this.forum_question = forum_question;
	}
	
	
}
