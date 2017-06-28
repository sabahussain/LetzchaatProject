package com.niit.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.JobDAO;
import com.niit.dao.JobDAOImpl;
import com.niit.model.Job;
import com.niit.model.JobApplied;
import com.sun.org.apache.regexp.internal.recompile;

@RestController
public class JobController {

	@Autowired
	JobApplied jobapplied;
	
	@Autowired
	JobDAOImpl jobDAOImpl;
	
	@Autowired
	Job job;
	
	@Autowired
	HttpSession session;
	
	@RequestMapping(value="/getalljobs/",method=RequestMethod.GET)
	public ResponseEntity<List<Job>> getAllJobs()
	{
		List<Job> job=jobDAOImpl.getAllOpenedJobs();
		return new ResponseEntity<List<Job>>(job, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getmyappliedjobs/",method=RequestMethod.GET)
	public ResponseEntity<List<JobApplied>> getMyAppliedJobs(HttpSession session)
	{
		
		String username=(String) session.getAttribute("username");
		List<JobApplied> jobApplied=jobDAOImpl.getMyAppliedJobs(username);
		return new ResponseEntity<List<JobApplied>>(jobApplied, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getjobdetails/{jobid}",method=RequestMethod.GET)
	public ResponseEntity<Job> getJobDetails(@PathVariable("jobid") int jobid )
	{
		Job job=jobDAOImpl.getJobDetails(jobid);
		return new ResponseEntity<Job> (job,HttpStatus.OK);
	}
	
	private boolean isUserAppliedForTheJob(String username, int jobid)
	{
		if(jobDAOImpl.getAppliedJob(username, jobid)==null)
			{
				return true;
			}
		else
			return false;
	}
	
	private JobApplied updateJobAppliedStatus(String username, int jobid, char status)
	{
		if(isUserAppliedForTheJob(username,jobid))
		{
			jobapplied.setErrorcode("404");
			jobapplied.setErrormessage(username+" not applied for this job "+jobid);
			return jobapplied;
		}
		
		String role=(String) session.getAttribute("role");
		if(role==null || role.isEmpty())
		{
			jobapplied.setErrorcode("404");
			jobapplied.setErrormessage("You are not LoggedIN");
			return jobapplied;
		}
		if(!role.equalsIgnoreCase("admin"))
		{
			jobapplied.setErrorcode("404");
			jobapplied.setErrormessage("You are not Admin! you cannot perform this operation");
			return jobapplied;
		}
			jobapplied=jobDAOImpl.getAppliedJob(username, jobid);
			jobapplied.setStatus(status);
			
			if(jobDAOImpl.updateJobApplied(jobapplied))
			{
				jobapplied.setErrorcode("200");
				jobapplied.setErrormessage("Successfully Updated the Status as "+status);
			}
			
			else
			{
				job.setErrorcode("404");
				job.setErrormessage("Not Able to change the applied status"+status);
			}
		
			return jobapplied;
		
	}
	
	@RequestMapping(value="/selectuser/{username}/{jobid}",method=RequestMethod.PUT)
	public ResponseEntity<JobApplied> selectUser(@PathVariable ("username") String username, @PathVariable("jobid") int jobid)
	{
		jobapplied=updateJobAppliedStatus(username,jobid,'S');
		return new ResponseEntity<JobApplied>(jobapplied,HttpStatus.OK);
	}
	
	@RequestMapping(value="/callforinterview/{username}/{jobid}",method=RequestMethod.PUT)
	public ResponseEntity<JobApplied> callForInterview(@PathVariable ("username") String username, @PathVariable ("jobid") int jobid)
	{
		jobapplied=updateJobAppliedStatus(username, jobid, 'C');
		return new ResponseEntity<JobApplied>(jobapplied, HttpStatus.OK);
	}
	
	@RequestMapping(value="/rejectjobapplied/{username}/{jobid}",method=RequestMethod.PUT)
	public ResponseEntity<JobApplied> rejectJobApplied(@PathVariable ("username") String username, @PathVariable("jobid") int jobid)
	{
		jobapplied=updateJobAppliedStatus(username, jobid, 'R');
		return new ResponseEntity<JobApplied>(jobapplied, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/postjob/",method=RequestMethod.POST)
	public ResponseEntity<Job> postJob(@RequestBody Job job)
	{
		job.setStatus('V');
		job.setDate_time(new Date());
		System.out.println("job data is: "+job);
		if(jobDAOImpl.insertJob(job)==false)
		{
			job.setErrorcode("404");
			job.setErrormessage("Not Able to Post the Job");
		}
		else{
				job.setErrorcode("200");
				job.setErrormessage("Job Posted Successfully");
		}
		return new ResponseEntity<Job>(job,HttpStatus.OK);
	}
	
	@RequestMapping(value="/applyjob/{jobid}",method=RequestMethod.POST)
	public ResponseEntity<JobApplied> applyJob(@PathVariable("jobid") int jobid,HttpSession session)
	
	{
		String username = (String) session.getAttribute("username");
		
		if(username==null)
		{
			jobapplied.setErrorcode("404");
			jobapplied.setErrormessage("Please login in to apply for a job");
			
		}
		else
		{
			if(jobDAOImpl.getAppliedJob(username, jobid)==null)
			{
				
				jobapplied.setJobid(jobid);
				jobapplied.setUsername(username);
				jobapplied.setStatus('N');  //N=>Newly Applied,C=>Call for interview ,S=>selected
				jobapplied.setDate_applied(new Date(System.currentTimeMillis()));
				
				if(jobDAOImpl.insertJobApplied(jobapplied))
				{
					jobapplied.setErrorcode("200");
					jobapplied.setErrormessage("Successfully applied for  the job");
				}
				else
				{
					jobapplied.setErrorcode("404");
					jobapplied.setErrormessage("You already applied for  the job"+jobid);
				}
				
			}
			
		}
		return new ResponseEntity<JobApplied>(jobapplied,HttpStatus.OK);
		}
	
	
	
	@RequestMapping(value="/getallappliedjobs/",method=RequestMethod.GET)
	public ResponseEntity<List<JobApplied>> getAllAppliedJobs()
	{
		List<JobApplied> jobapplied=jobDAOImpl.getallAppliedJobs();
		return new ResponseEntity<List<JobApplied>>(jobapplied, HttpStatus.OK);
	}
	
}
