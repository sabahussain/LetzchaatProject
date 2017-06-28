package com.niit.controller;

import java.util.ArrayList;
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

import com.niit.dao.FriendDAOImpl;
import com.niit.model.Friend;
import com.niit.model.User;

@RestController
public class FriendController {
	@Autowired
	FriendDAOImpl friendDAOImpl;
	
	@Autowired
	Friend friend;
	
	@Autowired
	User user;
	
	@Autowired
	HttpSession session;
	
	@RequestMapping(value="/pendingusers",method=RequestMethod.GET)
	public ResponseEntity<List<Friend>> getPendingFriendRequest()
	{
		
		System.out.println("Inside pending users method");
		String username=(String) session.getAttribute("username");
		List<Friend> friendList=friendDAOImpl.getFriendList(username);
		
		System.out.println("pending users"+friendList);
		
		if(friendList.isEmpty() || friendList==null)
		{
			System.out.println("Inside IF pending users");
			friend.setErrorcode("404");
			friend.setErrormessage("No Friends Are Available");
		}
		else
		{
			System.out.println("Inside ELSE pending users");
			friend.setErrorcode("200");
			friend.setErrormessage("Valued retrived Successfully");
		}
		
		return new ResponseEntity<List<Friend>>(friendList, HttpStatus.OK);
	}
	
	@RequestMapping(value="/acceptedusers",method=RequestMethod.GET)
	public ResponseEntity<List<Friend>> getAcceptedFriendRequest()
	{
		String username=(String) session.getAttribute("username");
		List<Friend> friends=new ArrayList<Friend>();
		
		if(username==null)
		{
			friend.setErrorcode("400");
			friend.setErrormessage("Please login to know your friends!");
			friends.add(friend);
			return new ResponseEntity<List<Friend>>(friends,HttpStatus.OK);
		}
		friends=friendDAOImpl.getFriendAcceptedList(username);
		
		if(friends.isEmpty() || friends==null)
		{
			friend.setErrorcode("404");
			friend.setErrormessage("No Friends Are available");
			friends.add(friend);
		}
		else
		{
			friend.setErrorcode("200");
			friend.setErrormessage("Successfully retrived the data");
		}
		
		return new ResponseEntity<List<Friend>>(friends, HttpStatus.OK);
		
		
	}
	
	@RequestMapping(value="/acceptfriend/{username}",method=RequestMethod.PUT)
	public ResponseEntity<Friend> acceptFriendRequest(@PathVariable ("username") String friend_name)
	{
		String username=(String) session.getAttribute("username");
		
		if(username==null || username.isEmpty())
		{
			friend.setErrorcode("404");
			friend.setErrormessage("Please login to continue!");
			return new ResponseEntity<Friend>(friend, HttpStatus.OK);
		}
		
		friend=friendDAOImpl.getFriendRequest(username, friend_name);
		friend.setFriend_request('A');
		
		if(friendDAOImpl.updateFriend(friend))
		{
			friend.setErrorcode("200");
			friend.setErrormessage("Your request has been accepted!");
		}
		else
		{
			friend.setErrorcode("400");
			friend.setErrormessage("Not able to change the request status");
		}
		
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	}
		
	@RequestMapping(value="/rejectfriend/{username}", method=RequestMethod.PUT)
	public ResponseEntity<Friend> rejectFriendRequest(@PathVariable("username") String friend_name)
	{
		String username=(String) session.getAttribute("friend_name");
		
		if(username==null || username.isEmpty())
		{
			friend.setErrorcode("404");
			friend.setErrormessage("Please login to continue");
			return new ResponseEntity<Friend>(friend, HttpStatus.OK);
		}
		
		friend=friendDAOImpl.getFriendRequest(username, friend_name);
		friend.setFriend_request('R');
		
		if(friendDAOImpl.updateFriend(friend))
		{
			friend.setErrorcode("200");
			friend.setErrormessage("Your request has been rejected!");
		}
		else
		{
			friend.setErrorcode("404");
			friend.setErrormessage("not able to update the friend request status!");
		}
		
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/friendProfile,{friend_name}",method=RequestMethod.GET)
	public ResponseEntity<User> getFriendProfile(@PathVariable("friend_name") String friend_name)
	{
		user=friendDAOImpl.getFriendDetails(friend_name);
		if(user==null)
		{
			user.setErrorcode("404");
			user.setErrormessage("user does not exist");
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		else
		{
			friend.setErrorcode("200");
			friend.setErrormessage("Successfully retrived Friend Value");
		}
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
}
