package com.niit.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.Blog;
import com.niit.model.BlogComment;
import com.niit.model.User;

@Repository
public class BlogDAOImpl implements BlogDAO{
	
	@Autowired
	SessionFactory sessionFactory;
	
	public BlogDAOImpl(SessionFactory sessionFactory)
	{
		this.sessionFactory=sessionFactory;
	}

	public void Update(Blog blog) {
		try {
			Session session=sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.update(blog);
			tx.commit();
			session.close();
		
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	
	public boolean insertBlog(Blog blog) {
		// TODO Auto-generated method stub
		try{
		Session sess=sessionFactory.openSession();
		Transaction tx=sess.beginTransaction();
		blog.setStatus('N');
		sess.save(blog);
		tx.commit();
		sess.close();
		return true;
		}
		catch(Exception ex){
		
			System.out.println("Exception"+ex);
			return false;
		
		}
		
	}


	public List<Blog> viewBlog() {
		// TODO Auto-generated method stub
		Session session=sessionFactory.openSession();
		Query q=session.createQuery("from Blog");
		List<Blog> b=q.list();
		session.close();
		return b;
	}


	public boolean updateBlogbyID(int blog_id) {
		// TODO Auto-generated method stub
		try{
			Session sess=sessionFactory.openSession();
			Transaction tx=sess.beginTransaction();
			sess.update(blog_id);
			tx.commit();
			sess.close();
			return true;
		}
		catch(Exception ex){
			
			System.out.println(ex);
			return false;
		}
		
	}


	public boolean deleteBlog(int blog_id) {
		// TODO Auto-generated method stub
		try{
			Session sess=sessionFactory.openSession();
			Transaction tx=sess.beginTransaction();
			Blog id=sess.get(Blog.class, blog_id);
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


	public boolean editBlog(int blog_id) {
		// TODO Auto-generated method stub
		try{
			Session sess=sessionFactory.openSession();
		
		Transaction tx=sess.beginTransaction();
		Blog b=sess.get(Blog.class, blog_id);
		sess.update(b);
		tx.commit();
		sess.close();
		return true;
		}
		catch(Exception ex){
			System.out.println(ex);
			return false;
			
		}
	}


	public Blog getBlogbyId(int blog_id) {
		// TODO Auto-generated method stub
		Session sess=sessionFactory.openSession();
		Blog id=sess.get(Blog.class, blog_id);
		return id;
	}


	public List<Blog> getBlogbyUsername(String username) {
		// TODO Auto-generated method stub
		Session sess=sessionFactory.openSession();
		Transaction tx=sess.beginTransaction();
		Query q=sess.createQuery("select * from Blog where username=:username");
		List<Blog> l=q.list();
		return l;
	}


	public boolean insertBlogComment(BlogComment blogComment, String username, int blog_id) {
		// TODO Auto-generated method stub
		try{
			Session sess=sessionFactory.openSession();
			Transaction tx=sess.beginTransaction();
			blogComment.setBlog_id(blog_id);
			blogComment.setUsername(username);
			blogComment.setComment_date(new Date());
			sess.save(blogComment);
			tx.commit();
			sess.close();
			
			return true;
		}
		catch(Exception ex)
		{
			System.out.println(ex);
			return false;
		}
	}


	public boolean updateBlogComment(BlogComment blogComment) {
		// TODO Auto-generated method stub
		try{
			Session sess=sessionFactory.openSession();
			Transaction tx=sess.beginTransaction();
			sess.update(blogComment);
			tx.commit();
			sess.close();
			return true;
		}
		catch(Exception e)
		{
			System.out.println(e);
			return false;
		}
	}


	public void deleteBlogComment(int blog_id) {
		// TODO Auto-generated method stub
		Session sess=sessionFactory.openSession();
		Transaction tx=sess.beginTransaction();
		BlogComment bc=sess.get(BlogComment.class, blog_id);
		sess.delete(bc);
		tx.commit();
		sess.close();
	}


	public BlogComment getBlogCommentbyId(int blog_id) {
		// TODO Auto-generated method stub
		Session sess=sessionFactory.openSession();
		BlogComment b=sess.get(BlogComment.class, blog_id);
		sess.close();
		return b;
	}


	public List<BlogComment> getBlogCommentList() {
		// TODO Auto-generated method stub
		Session sess=sessionFactory.openSession();
		Transaction tx=sess.beginTransaction();
		Query q=sess.createQuery("from BlogComment");
		List<BlogComment> l=q.list();
		tx.commit();
		sess.close();
		return l;
	}


	public List<Blog> getAllBlogs() {
		// TODO Auto-generated method stub
		Session sess=sessionFactory.openSession();
		Transaction tx=sess.beginTransaction();
		Query query=sess.createQuery("from Blog"); //where status='A'
		List<Blog> b=query.list();
		tx.commit();
		sess.close();
		return b;
	}

	public List<BlogComment> getBlogCommentListNew(int blog_id) {
		Session sess=sessionFactory.openSession();
		Transaction tx=sess.beginTransaction();
		Query q=sess.createQuery("from BlogComment where blog_id=:blog_id");
		q.setParameter("blog_id", blog_id);
		List<BlogComment> l=q.list();
		tx.commit();
		sess.close();
		return l;
	}

	public List<Blog> getAllBlogs1() {
		Session sess=sessionFactory.openSession();
		Transaction tx=sess.beginTransaction();
		Query query=sess.createQuery("from Blog where status='A'"); //where status='A'
		List<Blog> b=query.list();
		tx.commit();
		sess.close();
		return b;
	}

}
 