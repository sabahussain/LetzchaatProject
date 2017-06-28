package com.niit.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.Forum;
import com.niit.model.ForumComment;

@Repository
public class ForumDAOImpl implements ForumDAO{
	
	
	
	@Autowired
	SessionFactory sessionFactory;
	
	public ForumDAOImpl(SessionFactory sessionFactory)
	{
		this.sessionFactory=sessionFactory;
	}

	public boolean insertForum(Forum forum) {
		// TODO Auto-generated method stub
		try{
			Session sess=sessionFactory.openSession();
			Transaction tx=sess.beginTransaction();
			sess.save(forum);
			tx.commit();
			sess.close();
			return true;
		}
		catch(Exception e){
			System.out.println(e);
			return false;
		}
		
	}

	public boolean updateForum(Forum forum) {
		// TODO Auto-generated method stub
		try{
			Session sess=sessionFactory.openSession();
			Transaction tx=sess.beginTransaction();
			sess.update(forum);
			tx.commit();
			sess.close();
			return true;
		}
		catch(Exception ex){
			System.out.println(ex);
			return false;
		}
	}

	public boolean deleteForum(int forum_id) {
		// TODO Auto-generated method stub
		try{
			Session sess=sessionFactory.openSession();
			Transaction tx=sess.beginTransaction();
			Forum id=sess.get(Forum.class, forum_id);
			sess.delete(id);
			tx.commit();
			sess.close();
			return true;
		}
		catch(Exception ex){
			System.out.println("Exception"+ex);
			return false;
		}
	}

	public List<Forum> getForumList() {
		// TODO Auto-generated method stub
		Session sess= sessionFactory.openSession();
		Query query = sess.createQuery("from Forum");
		List<Forum> b = query.list();
		sess.close();
		return b;
	}


	public Forum getForumByID(int forum_id) {
		// TODO Auto-generated method stub
		Session sess=sessionFactory.openSession();
		Forum b=sess.get(Forum.class, forum_id);
		sess.close();
		return b;
	}

	public boolean insertForumComment(ForumComment forumcomment, String username, int forum_id) {
		// TODO Auto-generated method stub
		try {
			Session sess=sessionFactory.openSession();
			Transaction tx = sess.beginTransaction();
			ForumComment f=new ForumComment();
			f.setForumid(forum_id);
			//forumcomment.setId(id); ??
			//forumcomment.setForumid(forum_id);
			f.setUsername(username);
			f.setForum_comment(forumcomment.getForum_comment());
			f.setComment_date(new Date());
			
			System.out.println("FORUM COMMENT DATA "+f);
			sess.save(f);
			tx.commit();
			sess.close();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

	}

	public boolean updateForumComment(ForumComment forumcomment) {
		// TODO Auto-generated method stub
		try {
			Session sess=sessionFactory.openSession();
			Transaction tx = sess.beginTransaction();
			sess.update(forumcomment);
			tx.commit();
			sess.close();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public void deleteForumComment(int forum_id) {
		// TODO Auto-generated method stub
		Session sess=sessionFactory.openSession();
		Transaction tx=sess.beginTransaction();
		ForumComment fc=sess.get(ForumComment.class, forum_id);
		sess.delete(fc);
		tx.commit();
		sess.close();
	}

	public ForumComment getForumCommentByID(int forum_id) {
		// TODO Auto-generated method stub
		Session sess=sessionFactory.openSession();
		ForumComment b=sess.get(ForumComment.class, forum_id);
		sess.close();
		return b;
	}

	public List<ForumComment> getAllForumComment() {
		// TODO Auto-generated method stub
		Session sess=sessionFactory.openSession();
		Transaction tx = sess.beginTransaction();
		Query query = sess.createQuery("from ForumComment");
		List<ForumComment> f = query.list();
		tx.commit();
		sess.close();
		return f;
	}

	public List<ForumComment> getAllForumCommentNew(int forum_id) {
		// TODO Auto-generated method stub
		Session sess=sessionFactory.openSession();
		Transaction tx = sess.beginTransaction();
		Query query = sess.createQuery("from ForumComment where forum_id=:forum_id");
		query.setParameter("forum_id", forum_id);
		List<ForumComment> f = query.list();
		System.out.println("forum Comment of particular forum is: "+f);
		tx.commit();
		sess.close();
		return f;
	}
	
	

}
