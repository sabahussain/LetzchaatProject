package com.niit.dao;

import java.util.List;

import com.niit.model.Job;
import com.niit.model.JobApplied;

public interface JobDAO {
	
	public List<Job> getAllOpenedJobs();
	public Job getJobDetails(int jobid);
	public boolean updateJob(Job job);
	public boolean updateJobApplied(JobApplied jobapplied);
	public boolean insertJob(Job job);
	public boolean insertJobApplied(JobApplied jobapplied);
	public List<JobApplied> getMyAppliedJobs(String username);
	public JobApplied getAppliedJob(String username, int jobid);
	public JobApplied getAppliedJob(int jobid);
	public List<JobApplied> getallAppliedJobs();
	
	

}
