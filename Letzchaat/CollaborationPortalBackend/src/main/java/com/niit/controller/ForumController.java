package com.niit.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.ForumDAOImpl;
import com.niit.model.Forum;
import com.niit.model.ForumComment;

@RestController
public class ForumController {
	
	@Autowired
	ForumDAOImpl forumDAOImpl;
	
	@Autowired
	Forum forum;
	
	@Autowired
	ForumComment forumcomment; 
	
	
	
	@RequestMapping(value="/createforum/",method=RequestMethod.POST)
	public ResponseEntity<Forum> createForum(@RequestBody Forum forum, HttpSession session, HttpServletRequest request)
	{
		session=request.getSession(false);
		String username=(String) session.getAttribute("username");
		if(username==null)
		{
			forum.setErrorcode("404");
			forum.setErrormessage("User is not LoggedIN!");
			return new ResponseEntity<Forum>(HttpStatus.OK);
		}
		else
		{
			forum.setDate_created(new Date());
			forum.setUsername(username);
			forumDAOImpl.insertForum(forum);
			forum.setErrorcode("200");
			forum.setErrormessage("Forum is created successfully");
			return new ResponseEntity<Forum>(forum, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/updateforum/{forum_id}",method=RequestMethod.PUT)
	public ResponseEntity<Forum> updateForum(@PathVariable int forum_id)
	{
		forumDAOImpl.updateForum(forum);
		forum.setErrorcode("200");
		forum.setErrormessage("Forum successfully updated!");
		return new ResponseEntity<Forum>(forum,HttpStatus.OK);
	}
	
	@RequestMapping(value="/deleteforum/{forum_id}",method=RequestMethod.DELETE)
	public ResponseEntity<Forum> deleteForum(@PathVariable int forum_id)
	{
		forumDAOImpl.deleteForum(forum_id);
		forum.setErrorcode("200");
		forum.setErrormessage("Forum successfully DELETED!");
		return new ResponseEntity<Forum>(forum, HttpStatus.OK);
	}

	@RequestMapping(value="/getforum/{forum_id}",method=RequestMethod.GET)
	public ResponseEntity<Forum> getForum(@PathVariable int forum_id)
	{
		forum=forumDAOImpl.getForumByID(forum_id);
		forum.setErrorcode("200");
		forum.setErrormessage("Retrieved the Forum");
		return new ResponseEntity<Forum>(forum,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/getallforums/",method=RequestMethod.GET)
	public ResponseEntity<List<Forum>> getAllForums()
	{
		 
		List<Forum>forumlist=forumDAOImpl.getForumList();
		forum.setErrorcode("200");
		forum.setErrormessage("Fetched All the blogs");
		return new ResponseEntity<List<Forum>>(forumlist,HttpStatus.OK);
	}
	
	@RequestMapping(value="/createforumcomment/{forum_id}",method=RequestMethod.POST)
	public ResponseEntity<ForumComment> createForumcomment(@RequestBody ForumComment forumcomment,@PathVariable int forum_id,HttpSession session,HttpServletRequest request)
	{
		session=request.getSession(false);
		String username=(String)session.getAttribute("username");
		System.out.println("inside createforumcontroller"+forumcomment+"forum id= "+forum_id);
		
			
			if(forumDAOImpl.insertForumComment(forumcomment,username,forum_id)==true)
			{
				forumcomment.setErrorcode("200");
				forumcomment.setErrormessage("The user has successfully created the forumcomment");
				return new ResponseEntity<ForumComment>(forumcomment,HttpStatus.OK);
			
			}
			else
			{
				
				forumcomment.setErrorcode("404");
				forumcomment.setErrormessage("Could not complete the operation.Please contact Admin");
				
				return new ResponseEntity<ForumComment>(HttpStatus.OK);
				
			}
	}
	
	@RequestMapping(value="/updateforumcomment/",method=RequestMethod.PUT)
	public ResponseEntity<ForumComment> updateForumComment(@RequestBody ForumComment forumcomment,HttpSession session,HttpServletRequest request)
	{
		session=request.getSession(false);
		String username=(String)session.getAttribute("username");
		if(username == null)
		{
			forumcomment.setErrorcode("404");
			forumcomment.setErrormessage("User is not Logged In");
			return new ResponseEntity<ForumComment>(HttpStatus.OK);
		}
		else
		{
			forumDAOImpl.updateForumComment(forumcomment);
			forumcomment.setErrorcode("200");
			forumcomment.setErrormessage("The user has successfully updated the forumcomment");
			return new ResponseEntity<ForumComment>(forumcomment,HttpStatus.OK);
		
		}
	}
	
	@RequestMapping(value="/deleteforumcomment/{forum_id}",method=RequestMethod.DELETE)
	public ResponseEntity<ForumComment> deleteForumComment(@PathVariable int forum_id,HttpSession session,HttpServletRequest request)
	{
		session=request.getSession(false);
		String username=(String)session.getAttribute("username");
		if(username==null)
		{
			forumcomment.setErrorcode("404");
			forumcomment.setErrormessage("User is not Logged In");
			return new ResponseEntity<ForumComment>(HttpStatus.OK);
		}
		else{
			forumDAOImpl.deleteForumComment(forum_id);
			forumcomment.setErrorcode("200");
			forumcomment.setErrormessage("The user has successfully deleted the blogcomment");
			return new ResponseEntity<ForumComment>(forumcomment,HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/getforumcomment/{forum_id}",method=RequestMethod.GET)
	public ResponseEntity<ForumComment> getForumComment(@PathVariable int forum_id)
	{
		System.out.println("Inside getforumcomment in ForumController.java"+forum_id);
		forumcomment=forumDAOImpl.getForumCommentByID(forum_id);
		forumcomment.setErrorcode("200");
		forumcomment.setErrormessage("Retrieved the blogcomment");
		return new ResponseEntity<ForumComment>(forumcomment,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/getallforumcomments/",method=RequestMethod.GET)
	public ResponseEntity<List<ForumComment>> getAllForumComments()
	{
		 
		List<ForumComment>forumlist= forumDAOImpl.getAllForumComment();
		forumcomment.setErrorcode("200");
		forumcomment.setErrormessage("Fetched All the forumscomment");
		forumlist.add(forumcomment);
		return new ResponseEntity<List<ForumComment>>(forumlist,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getallforumcommentsnew/{forum_id}",method=RequestMethod.GET)
	public ResponseEntity<List<ForumComment>> getAllForumCommentsnew(@PathVariable int forum_id)
	{
		 List<ForumComment> forumlist= forumDAOImpl.getAllForumCommentNew(forum_id);
		forumcomment.setErrorcode("200");
		forumcomment.setErrormessage("Fetched All the forumscomment");
		forumlist.add(forumcomment);
		return new ResponseEntity<List<ForumComment>>(forumlist,HttpStatus.OK);
	}

}