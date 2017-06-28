package com.niit.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.niit.dao.FriendDAOImpl;
import com.niit.dao.UserDAOImpl;
import com.niit.model.User;

@RestController
public class UserController {
	
	private static Logger log=LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserDAOImpl userDAOImpl;
	@Autowired
	User user;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	FriendDAOImpl friendDAOImpl;
	
	
	@RequestMapping(value="/getUsers",method=RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUsers(HttpSession session){
		String username=(String)session.getAttribute("username");
		List<User> users=userDAOImpl.getAllUsers(username);
		if(username==null)
		{
			user.setErrorcode("404");
			user.setErrormessage("Data not found");
			users.add(user);
		}
		
		
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/login/",method=RequestMethod.POST)
	public ResponseEntity<User> validateUser(@RequestBody User user,HttpServletRequest request,HttpSession session)
	{
		log.debug("Starting of the Method isValidUser");
		String username=user.getUsername();
		String password=user.getPassword();
		
		
		System.out.println("Username is:"+username);
		System.out.println("Password is:"+password);
		
		
		user=userDAOImpl.validate(username, password);
		System.out.println("User value:"+user);
		
		if(user==null)
		{
			user = new User();
			user.setErrorcode("404");
			user.setErrormessage("Username and password doesnt exists...");
			return new ResponseEntity<User>(user,HttpStatus.OK);
			
			
		}
		char status=user.getStatus();
		System.out.println("Status is:"+status);
		
		
		if(status=='N')
		{
			user.setErrorcode("404");
			user.setErrormessage("Your registration is pending to approve.. ");
			return new ResponseEntity<User>(user,HttpStatus.OK);
		}
		if(status=='R')
		{
			user.setErrorcode("404");
			user.setErrormessage("Your registration is rejected by admin.. ");
			return new ResponseEntity<User>(user,HttpStatus.OK);
		}
		else 
		{
			session.setAttribute("username", username);
			session.setAttribute("role", user.getRole());
			//user.setIsonline('Y');
			user.setErrorcode("200");
			user.setErrormessage("You have successfully Loggedin");
			userDAOImpl.setOnline(username);
			//friendDAOImpl.setOnline(username);
			log.debug("Ending of the Method isValidUser");
			return new ResponseEntity<User>(user,HttpStatus.OK) ;
		}
	
	}
	
	@RequestMapping(value="/register/", method=RequestMethod.POST)
	public ResponseEntity<User> registerUser(@RequestBody User user)
	{
		
			log.debug("USERCONTROLLER=>REGISTER " + user);
			if(userDAOImpl.getUsername(user.getUsername())==null)
			{
			user.setStatus('N');
			user.setIsonline('N');
			
			if(userDAOImpl.insertUser(user)==true)
				{
				user.setErrorcode("200");
				user.setErrormessage("You have Registered Successfully ");
				return new ResponseEntity<User>(user,HttpStatus.OK);
				}
				else{
					user.setErrorcode("404");
					user.setErrormessage("Couldnt insert user details ");
					return new ResponseEntity<User>(user , HttpStatus.OK);
					
				}
				/*return new ResponseEntity<User>(user, HttpStatus.OK);*/
			}
			log.debug("->->->->User already exist with id " + user.getUsername());
			user.setErrorcode("404");
			user.setErrormessage("User already exist with id : " + user.getUsername());
			return new ResponseEntity<User>(user, HttpStatus.OK);
			
			
		
	}
	
	@RequestMapping(value="/user/logout",method=RequestMethod.PUT)
	public ResponseEntity<User> logout(HttpSession session)
	{
		String username=(String)session.getAttribute("username");
		if(user!=null)
		{
			//user.setIsonline('N');
			userDAOImpl.setOffLine(username);
			//friendDAOImpl.setOffLine(username);
		}
		
		session.removeAttribute("username");
		session.invalidate();
		return new ResponseEntity<User>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/myProfile", method = RequestMethod.GET)
	public ResponseEntity<User> myProfile(HttpSession session) {
		log.debug("->->calling method myProfile");
		String username = (String) session.getAttribute("username");
		User user = userDAOImpl.getUsername(username);
		if (user == null) {
			log.debug("->->->-> User does not exist wiht id" + username);
			user = new User();
			user.setErrorcode("404");
			user.setErrormessage("User does not exist");
			return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
		}
		log.debug("->->->-> User exist with username" + username);
		log.debug(user.getName());
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {

		log.debug("->->->->calling method listAllUsers");
		List<User> users = userDAOImpl.list();

		// errorCode :200 :404
		// errorMessage :Success :Not found

		if (users.isEmpty()) {
			user.setErrorcode("404");
			user.setErrormessage("No users are available");
			users.add(user);
		}

		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/user/", method = RequestMethod.PUT)
	public ResponseEntity<User> update(@RequestBody User user) {
		log.debug("->->->->calling method update");
		if (userDAOImpl.getUsername(user.getUsername()) == null) {
			log.debug("->->->->User does not exist with id " + user.getUsername());
			user = new User(); // ?
			user.setErrorcode("404");
			user.setErrormessage("User does not exist with id " + user.getUsername());
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}

		userDAOImpl.Update(user);
		log.debug("->->->->User updated successfully");
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	
	
		
	@RequestMapping(value = "/accept/{username}", method = RequestMethod.GET)
	public ResponseEntity<User> accept(@PathVariable("username") String username) {
		log.debug("Starting of the method accept");

		user = updateStatus(username, 'A', "");
		log.debug("Ending of the method accept");
		return new ResponseEntity<User>(user, HttpStatus.OK);

	}

	@RequestMapping(value = "/reject/{username}/{reason}", method = RequestMethod.GET)
	public ResponseEntity<User> reject(@PathVariable("username") String username, @PathVariable("reason") String reason) {
		log.debug("Starting of the method reject");

		user = updateStatus(username, 'R', reason);
		log.debug("Ending of the method reject");
		return new ResponseEntity<User>(user, HttpStatus.OK);

	}

	private User updateStatus(String username, char status, String reason) {
		log.debug("Starting of the method updateStatus");

		log.debug("status: " + status);
		user = userDAOImpl.getUsername(username);

		if (user == null) {
			user = new User();
			user.setErrorcode("404");
			user.setErrormessage("Could not update the status to " + status);
		} else {
			
			String role=(String)session.getAttribute("role");
			if(role==null ||role.isEmpty()){
				user.setErrorcode("404");
				user.setErrormessage("You are not logged in");
				return user;
			}
			if(!role.equalsIgnoreCase("admin"))
			{
				user.setErrorcode("404");
				user.setErrormessage("You are not admin.You cannot do this operation");
				log.debug("You are not admin.You cannot do this operation");
				return user;
			}

			user.setStatus(status);
			user.setReason(reason);
			
			userDAOImpl.Update(user);
			
			user.setErrorcode("200");
			user.setErrormessage("Updated the status successfully");
		}
		log.debug("Ending of the method updateStatus");
		return user;

	}
	@RequestMapping(value = "/friendRequest/", method = RequestMethod.POST)
	public ResponseEntity<User> friendRequest(@RequestBody String friend_name,HttpSession session) {
		System.out.println("calling method Friend request");
		String currentusername = (String) session.getAttribute("username");
		if(currentusername==null)
		{
			
				System.out.println("User does not exist wiht id" + currentusername);
			
			user.setErrorcode("404");
			user.setErrormessage("User does not exist");
			return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
		}
		else
		{
			
			if(isUserExist(friend_name)==false)
			{
				user.setErrorcode("404");
				user.setErrormessage("User does not exist with the id:"+ friend_name);
				return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
				
			}
			/*if(friendDAOImpl.getFriendRequest(currentusername, friendname)!=null){
				user.setErrorcode("404");
				user.setErrormessage("You have already send the frined request to id:"+ friendname);
				return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
			}*/
		
			
		userDAOImpl.sendFriendRequest(currentusername, friend_name);
		/*user.setErrorcode("200");
		user.setErrormessage("User does not exist with the id:"+ friendname);*/
		
		log.debug("->->->-> User exist with username" + currentusername);
		log.debug("friend_name is:"+friend_name);
			
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	}
	
	
	
	private boolean isUserExist(String id){
		if(userDAOImpl.getUsername(id)==null)
			return false;
		else 
			return true;
	}
	
	
		}
		
