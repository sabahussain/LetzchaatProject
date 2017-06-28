package com.niit.dao;

import java.util.List;

import com.niit.model.Friend;
import com.niit.model.User;

public interface FriendDAO {
	
	public List<Friend> getFriendList(String username);
	public boolean updateFriend(Friend friend);
	public Friend getFriendRequest(String username, String friend_name);
	public User getFriendDetails(String friend_name);
	public List<Friend> getFriendAcceptedList(String username);
	public void setOnline(String username);
	public void setOffline(String username); 
	

}
