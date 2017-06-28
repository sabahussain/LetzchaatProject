package com.niit.dao;

import java.util.List;

import com.niit.model.Forum;
import com.niit.model.ForumComment;

public interface ForumDAO {
	 
	public boolean insertForum(Forum forum);
	public boolean updateForum(Forum forum);
	public boolean deleteForum(int forum_id);
	public List<Forum> getForumList();
	public Forum getForumByID(int forum_id);
	
	
	public boolean insertForumComment(ForumComment forumcomment, String username, int forum_id);
	public boolean updateForumComment(ForumComment forumcomment);
	public void deleteForumComment(int forum_id);
	public ForumComment getForumCommentByID(int forum_id);
	public List<ForumComment> getAllForumComment();
	public List<ForumComment> getAllForumCommentNew(int forum_id);
	

}
