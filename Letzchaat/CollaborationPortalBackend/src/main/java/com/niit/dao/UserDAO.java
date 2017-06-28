package com.niit.dao;

import java.util.List;

import com.niit.model.User;

public interface UserDAO {
	
	public User validate(String username,String password);
	public boolean insertUser(User user);
	public void updateUser(User user);
	public List<User> list();
	public User get(String username, String password);
	public User getUsername(String username);
	public void setOnline(String username);
	public void setOffLine(String username);
	public void Update(User user);
	public void sendFriendRequest(String username,String friend_name);
	public List<User> getAllUsers(String username);
	

}
