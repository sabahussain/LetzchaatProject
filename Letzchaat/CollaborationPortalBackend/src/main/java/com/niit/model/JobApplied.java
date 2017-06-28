package com.niit.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Component
@Entity
public class JobApplied extends Error {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String username;
	private int jobid;
	private char status;
	private String remarks;
	private Date date_applied;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getJobid() {
		return jobid;
	}
	public void setJobid(int jobid) {
		this.jobid = jobid;
	}
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getDate_applied() {
		return date_applied;
	}
	public void setDate_applied(Date date_applied) {
		this.date_applied = date_applied;
	}
	
	@Override
	public String toString() {
		return "JobApplied [id=" + id + ", username=" + username + ", jobid=" + jobid + ", status=" + status
				+ ", remarks=" + remarks + ", date_applied=" + date_applied + "]";
	}
	
	
}
