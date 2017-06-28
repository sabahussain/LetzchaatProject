 package com.niit.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.Job;
import com.niit.model.JobApplied;


@Repository
public class JobDAOImpl implements JobDAO{
	
	@Autowired
	SessionFactory sessionFactory;
	
	
	public JobDAOImpl(SessionFactory sessionFactory)
	{
		this.sessionFactory=sessionFactory;
	}

	public List<Job> getAllOpenedJobs() {
		Session sess=sessionFactory.openSession();
		Transaction tx=sess.beginTransaction();
		Query q=sess.createQuery("from Job where status='V'");
		List<Job> j=q.list();
		System.out.println("inside JobDAOIMPL"+j);
		tx.commit();
		sess.close();
		return j;
	}

	public Job getJobDetails(int jobid) {
		// TODO Auto-generated method stub
		Session sess=sessionFactory.openSession();
		Transaction tx=sess.beginTransaction();
		Job j=sess.get(Job.class, jobid);
		tx.commit();
		sess.close();
		return j;
	}

	public boolean updateJob(Job job) {
		// TODO Auto-generated method stub
		try{
			Session sess=sessionFactory.openSession();
			Transaction tx=sess.beginTransaction();
			sess.update(job);
			tx.commit();
			sess.close();
			return true;
		}
		catch(Exception e){
			System.out.println(e);
		return false;
		}
	}

	public boolean updateJobApplied(JobApplied jobapplied) {
		// TODO Auto-generated method stub
		try{
			Session sess=sessionFactory.openSession();
			Transaction tx=sess.beginTransaction();
			sess.update(jobapplied);
			tx.commit();
			sess.close();
		return true;
		}
		catch(Exception ex){
			System.out.println(ex);
			return false;
		}
	}

	public boolean insertJob(Job job) {
		// TODO Auto-generated method stub
		try{
			System.out.println("insertJob in JobDAOImpl"+job);
			Session sess=sessionFactory.openSession();
			Transaction tx=sess.beginTransaction();
			sess.save(job);
			tx.commit();
			sess.close();
			return true;
		}
		catch(Exception ex){
			System.out.println(ex);
			return false;
		}
	}

	public boolean insertJobApplied(JobApplied jobapplied) {
		// TODO Auto-generated method stub
		try{
			Session sess=sessionFactory.openSession();
			Transaction tx=sess.beginTransaction();
			sess.save(jobapplied);
			tx.commit();
			sess.close();
			return true;
		}
		catch(Exception ex){
			System.out.println(ex);
			return false;
		}
	}

	public List<JobApplied> getMyAppliedJobs(String username) {
		Session sess=sessionFactory.openSession();
		Transaction tx=sess.beginTransaction();
		//??
		Query query=sess.createQuery("from Job where jobid in(select jobid from JobApplied where username='"+username+"')");
		List<JobApplied> j=query.list();
		tx.commit();
		sess.close();
		return j;
	}

	public JobApplied getAppliedJob(String username, int jobid) {
		// TODO Auto-generated method stub
		Session sess=sessionFactory.openSession();
		Transaction tx=sess.beginTransaction();
		Query query=sess.createQuery("from JobApplied where username='"+username+"' and jobid='"+jobid+"'");
		JobApplied jobapplied=(JobApplied) query.uniqueResult();
		tx.commit();
		sess.close();
		return jobapplied;
	}

	public JobApplied getAppliedJob(int jobid) {
		// TODO Auto-generated method stub
		Session sess=sessionFactory.openSession();
		Transaction tx=sess.beginTransaction();
		JobApplied j=sess.get(JobApplied.class, jobid);
		tx.commit();
		sess.close();
		return j;
	}

	public List<JobApplied> getallAppliedJobs() {
		// TODO Auto-generated method stub
		Session sess=sessionFactory.openSession();
		Transaction tx=sess.beginTransaction();
		Query q=sess.createQuery("from JobApplied");
		List<JobApplied> j=q.list();
		tx.commit();
		sess.close();
		return j;
	}

}
