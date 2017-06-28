package com.niit.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.BlogDAOImpl;
import com.niit.model.Blog;
import com.niit.model.BlogComment;
import com.niit.model.User;
import com.sun.org.apache.regexp.internal.recompile;

@RestController
public class BlogController {

	
	
	public static Logger log=LoggerFactory.getLogger(BlogController.class);
	
	@Autowired
	BlogDAOImpl blogDAOImpl;
	
	@Autowired
	Blog blog;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	BlogComment blogComment;
	
	@RequestMapping(value="/createblog/", method=RequestMethod.POST)
	public ResponseEntity<Blog> creatingBlog(@RequestBody Blog blog, HttpSession session, HttpServletRequest request)
	{
		System.out.println("inside create blog spring controller");
		session=request.getSession(false);
		String username=(String) session.getAttribute("username");
		blog.setDate_of_creation(new Date());
		blog.setUsername(username);
		boolean x=blogDAOImpl.insertBlog(blog);
		if(x){
			System.out.println("Inside If part Blog controller");
			blog.setErrorcode("200");
			blog.setErrormessage("Blog is successfully Started!");
		    System.out.println("blog data "+blog);
			return new ResponseEntity<Blog>(blog, HttpStatus.OK);
		}
		else
		{
			System.out.println("Inside else part Blog controller");
			blog.setErrorcode("404");
			blog.setErrormessage("Blog Creation Unsuccessfull!!");
			return new ResponseEntity<Blog>(blog, HttpStatus.OK);
			
		}
		
	}
	

	@RequestMapping(value="/updateBlog/{blog_id}",method=RequestMethod.PUT)
	public ResponseEntity<Blog> updateBlog(@PathVariable int blog_id, HttpSession session, HttpServletRequest request)
	{
		session=request.getSession(false);
		String username=(String) session.getAttribute("username");
		if(username==null)
		{
			blog.setErrorcode("404");
			blog.setErrormessage("User is not LoggedIn!!");
			return new ResponseEntity<Blog>(blog,HttpStatus.OK);
		
		
		}
		else
		{
			blogDAOImpl.updateBlogbyID(blog_id);
			blog.setErrorcode("200");
			blog.setErrormessage("Succesfully Updated the Blog");
			return new ResponseEntity<Blog>(blog,HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/deleteBlog/{blog_id}",method=RequestMethod.DELETE)
	public ResponseEntity<Blog> deleteBlog(@PathVariable int blog_id, HttpSession session, HttpServletRequest request)
	{
		session=request.getSession(false);
		String username=(String) session.getAttribute("username");
		if(username==null)
		{
			blog.setErrorcode("404");
			blog.setErrormessage("User is not LoggedIn!!");
			return new ResponseEntity<Blog>(HttpStatus.OK);
		}
		else{
			blogDAOImpl.deleteBlog(blog_id);
			blog.setErrorcode("200");
			blog.setErrormessage("Succesfully deleted the Blog");
			return new ResponseEntity<Blog>(blog,HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/getblog/{blog_id}",method=RequestMethod.GET)
	public ResponseEntity<Blog> getBlog(@PathVariable int blog_id)
	{
		blog=blogDAOImpl.getBlogbyId(blog_id);
		blog.setErrorcode("200");
		blog.setErrormessage("Blog Retreived!");
		return new ResponseEntity<Blog>(blog,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getAllblogs/",method=RequestMethod.GET)
	public ResponseEntity<List<Blog>> getAllBlogs()
	{
		List<Blog> blogList=blogDAOImpl.getAllBlogs1();
		blog.setErrorcode("200");
		blog.setErrormessage("All Blogs Fetched!!");
		return new ResponseEntity<List<Blog>>(blogList, HttpStatus.OK);
	}
	
	@RequestMapping(value="/createblogcomment/{blog_id}",method=RequestMethod.POST)
	public ResponseEntity<BlogComment> createBlogComment(@RequestBody BlogComment blogComment,@PathVariable int blog_id, HttpSession session, HttpServletRequest request)
	{
		session=request.getSession(false);
		String username=(String) session.getAttribute("username");
		System.out.println("Inside createBlogcomment"+blogComment);
		if(blogDAOImpl.insertBlogComment(blogComment, username, blog_id)==true)
		{
			blogComment.setErrorcode("200");
			blogComment.setErrormessage("Successfully created Blog Comment!");
			return new ResponseEntity<BlogComment>(blogComment, HttpStatus.OK);
		}
		else
		{
			blogComment.setErrorcode("404");
			blogComment.setErrormessage("Could not post the comment, Please contact Admin");
			return new ResponseEntity<BlogComment>(HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/updateblogcomment/",method=RequestMethod.PUT)
	public ResponseEntity<BlogComment> updateBlogComment(@RequestBody BlogComment blogcomment, HttpSession session, HttpServletRequest request)
	{
			session=request.getSession(false);
			String username=(String) session.getAttribute("username");
			if(username==null)
			{
				blogComment.setErrorcode("404");
				blogComment.setErrormessage("User Not LoggedIn!");
				return new ResponseEntity<BlogComment>(HttpStatus.OK);
			}
			else
			{
				blogComment.setErrorcode("200");
				blogComment.setErrormessage("Comment Successfully updated!");
				return new ResponseEntity<BlogComment>(blogComment, HttpStatus.OK);
			}
	}
	
	@RequestMapping(value="/deleteblogcomment/{blog_id}",method=RequestMethod.DELETE)
	public ResponseEntity<BlogComment> deleteblogComment(@PathVariable int blog_id,HttpSession session, HttpServletRequest request)
	{
		session=request.getSession(false);
		String username=(String) session.getAttribute("username");
		if(username==null)
		{
			blogComment.setErrorcode("404");
			blogComment.setErrormessage("User is Not LoggedIN!");
			return new ResponseEntity<BlogComment>(HttpStatus.OK);
		}
		else
		{
			blogComment.setErrorcode("200");
			blogComment.setErrormessage("Successfully deleted the Blog Comment");
			return new ResponseEntity<BlogComment>(blogComment,HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/getblogcomment/{blog_id}",method=RequestMethod.GET)
	public ResponseEntity<BlogComment> getBlogbyID(@PathVariable int blog_id)
	{
		blogComment=blogDAOImpl.getBlogCommentbyId(blog_id);
		blogComment.setErrorcode("200");
		blogComment.setErrormessage("Blog Comment Fetched!");
		return new ResponseEntity<BlogComment>(blogComment, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getblogcomments/",method=RequestMethod.GET)
	public ResponseEntity<List<BlogComment>> getallBlogComments()
	{
		List<BlogComment> bloglist=blogDAOImpl.getBlogCommentList();
		blogComment.setErrorcode("200");
		blogComment.setErrormessage("ALL Blog Comments Fetched!!");
		return new ResponseEntity<List<BlogComment>>(bloglist,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getblogcommentsnew/{blog_id}",method=RequestMethod.GET)
	public ResponseEntity<List<BlogComment>> getallBlogComments(@PathVariable("blog_id") int blog_id)
	{
		List<BlogComment> bloglist=blogDAOImpl.getBlogCommentListNew(blog_id);
		blogComment.setErrorcode("200");
		blogComment.setErrormessage("ALL Blog Comments Fetched!!");
		return new ResponseEntity<List<BlogComment>>(bloglist,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/acceptb/{blog_id}", method = RequestMethod.GET)
	public ResponseEntity<Blog> accept(@PathVariable("blog_id") int blog_id) {
		log.debug("Starting of the method accept");

		blog = updateStatus(blog_id, 'A', "");
		log.debug("Ending of the method accept");
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);

	}

	@RequestMapping(value = "/rejectb/{blog_id}/{reason}", method = RequestMethod.GET)
	public ResponseEntity<Blog> reject(@PathVariable("blog_id") int blog_id, @PathVariable("reason") String reason) {
		log.debug("Starting of the method reject");

		blog = updateStatus(blog_id, 'R', reason);
		log.debug("Ending of the method reject");
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);

	}
	
	private Blog updateStatus(int blog_id, char status, String reason) {
		log.debug("Starting of the method updateStatus");

		log.debug("status: " + status);
		blog = blogDAOImpl.getBlogbyId(blog_id);

		if (blog == null) {
			blog = new Blog();
			blog.setErrorcode("404");
			blog.setErrormessage("Could not update the status to " + status);
		} else {
			
			String role=(String)session.getAttribute("role");
			if(role==null ||role.isEmpty()){
				blog.setErrorcode("404");
				blog.setErrormessage("You are not logged in");
				return blog;
			}
			if(!role.equalsIgnoreCase("admin"))
			{
				blog.setErrorcode("404");
				blog.setErrormessage("You are not admin.You cannot do this operation");
				log.debug("You are not admin.You cannot do this operation");
				return blog;
			}

			blog.setStatus(status);
			blog.setReason(reason);
			
			blogDAOImpl.Update(blog);
			
			blog.setErrorcode("200");
			blog.setErrormessage("Updated the status successfully");
		}
		log.debug("Ending of the method updateStatus");
		return blog;

	}

	
	
}
