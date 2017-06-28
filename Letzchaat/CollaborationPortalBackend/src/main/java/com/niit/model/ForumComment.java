package com.niit.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Component
@Entity
public class ForumComment extends Error{

	 @Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
	 private int id;
	 private int forum_id;
	 private String username;
	 private String forum_comment;
	 private Date comment_date;
	 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getForumid() {
		return forum_id;
	}
	public void setForumid(int forumid) {
		this.forum_id = forumid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getForum_comment() {
		return forum_comment;
	}
	public void setForum_comment(String forum_comment) {
		this.forum_comment = forum_comment;
	}
	public Date getComment_date() {
		return comment_date;
	}
	public void setComment_date(Date comment_date) {
		this.comment_date = comment_date;
	}
	@Override
	public String toString() {
		return "ForumComment [id=" + id + ", forum_id=" + forum_id + ", username=" + username + ", forum_comment="
				+ forum_comment + ", comment_date=" + comment_date + "]";
	}
	 
	 
	
	 
}
