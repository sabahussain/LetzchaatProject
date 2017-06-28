package com.niit.dao;


import java.util.List;



import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.Friend;
import com.niit.model.User;

@Repository("UserDAO")
public class UserDAOImpl implements UserDAO {
	
private static Logger log=LoggerFactory.getLogger(UserDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	public Session getSession()
	{
		return sessionFactory.openSession();
	}
@Autowired
User user;

	public User validate(String username, String password) {
		log.debug("Starting of the Method USERDAOIMPL :: VALIDATE");
		Session session=getSession();
		//Transaction tx=session.beginTransaction();
		/*boolean userfound=false;*/
		String sql_query="from User where username=:username and password=:password ";
		  Query query=session.createQuery(sql_query);
		  query.setParameter("username", username);
		  query.setParameter("password", password);
		  /*query.setParameter("status", status);*/
		 // query.setParameter("role", role);
		  List<User> list=query.list();
		  System.out.println("List value:"+list);
		  if((list!=null)&&(list.size()>0)){
		  session.close();
		  log.debug("Ending of the Method USERDAOIMPL :: VALIDATE");
		  return list.get(0);
		  }
		  else{
			  user.setErrorcode("404");
				return null;  
		  }
			
		
	}

	public boolean insertUser(User user) {
		
		try {
			log.debug("Starting of the Method USERDAOIMPL :: INSERTUSER");
			 Session session = getSession();
			  Transaction tx = session.beginTransaction();
			  session.saveOrUpdate(user);
			  tx.commit();
			 /* Serializable username = session.getIdentifier(user);*/
			  session.close();
			  log.debug("Ending of the Method USERDAOIMPL :: INSERTUSER");
			  return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		 
		
	}

	public void updateUser(User user) {
		log.debug("Starting of the Method USERDAOIMPL::UPDATEUSER");
		log.debug("ISONLINE VALUE IS [BEFORE UPDATE]" + user.getIsonline());
		Session session=getSession();
		/*User existingUser=(User)session.get(User.class, user.getUsername());
		//update online status as true
		existingUser.setIsonline(user.getIsonline());;*/ 

		session.update(user);
		session.flush();
		session.close();
		log.debug("ISONLINE VALUE IS [AFTER UPDATE] " + user.getIsonline());
		log.debug("Starting of the Method USERDAOIMPL::UPDATEUSER");
		
	}

	public List<User> list() {
		log.debug("Starting of the USERDAO Method GETUSERLIST");
		Session session = getSession();
		// Transaction tx = session.beginTransaction();
		String s = "from User";
		Query query = session.createQuery(s);
		List<User> b = query.list();
		// tx.commit();
		session.close();
		log.debug("Ending of the USERDAO Method GETUSERLIST");
		return b;
	}

	public User get(String username, String password) {
		
		return null;
	}



	public void setOnline(String username) {
		log.debug("Starting of the USERDAO Method SETONLINE");
		Session session = getSession();
		 Transaction tx = session.beginTransaction();
		String s = "update User set isonline='Y' where username='"+username+"'";
		log.debug("String value"+s);
		Query query = session.createQuery(s);
		query.executeUpdate();
		 tx.commit();
		session.close();
		log.debug("Ending of the USERDAO Method SETONLINE");
		
		
		
	}

	public void setOffLine(String username) {
		log.debug("Starting of the USERDAO Method SETOFFLINE");
		Session session = getSession();
		 Transaction tx = session.beginTransaction();
		String s = "update User set isonline='N' where username='"+username+"'";
		log.debug("String value"+s);
		Query query = session.createQuery(s);
		query.executeUpdate();
		 tx.commit();
		session.close();
		log.debug("Ending of the USERDAO Method SETOFFLINE");
		
	}

	public User getUsername(String username) {
log.debug("Staring of the USERDAO Method getUsername");
		
		Session session = getSession();
		//Transaction tx=session.beginTransaction();
		 User u=session.get(User.class,username);
		session.close();
		log.debug("Ending of the USERDAO Method getUsername");
		return u;
	}

	public void Update(User user) {
	
		log.debug("Starting of the USERAO Method UPDATEUSERDETAILS");
		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();
			session.update(user);
			tx.commit();
			session.close();
			log.debug("Ending of the USERAO Method UPDATEUSERDETAILS");
		
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	
	private Integer getMaxId()
	{
		Integer maxid=1;
		try {
			Session session=getSession();
			
			String hql="select max(id) from Friend";
			Query query=session.createQuery(hql);
			maxid=(Integer)query.uniqueResult();
			
		} catch (Exception e) {
			maxid=1;
			e.printStackTrace();
		}
		return maxid+1;
	}
	
	

	public void sendFriendRequest(String username, String friend_name) {
		System.out.println("Starting of the USERAO Method SENDFRIENDREQUEST");
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		Friend friend=new Friend();
		friend.setFriend_name(friend_name);
		friend.setUsername(username);
		friend.setFriend_request('P');
		friend.setIs_online('N');
		session.save(friend);
		tx.commit();
		session.close();
		log.debug("Ending of the USERAO Method SENDFRIENDREQUEST");
	}
	
	public List<User> getAllUsers(String username) {
		log.debug("Starting of the USERDAO Method GETALLUSERLIST");
		Session session = getSession();
		// Transaction tx = session.beginTransaction();
		SQLQuery query=session.createSQLQuery("select * from User where username in (select username from User where username!=? minus(select friend_name from Friend where username=? union select username from Friend where friend_name=?))");
		query.setString(0, username);
		query.setString(1, username);
		query.setString(2, username);
		query.addEntity(User.class);
		List<User> users=query.list();
		System.out.println(users);
		System.out.println("Getall users in userdao"+users);
		// tx.commit();
		session.close();
		log.debug("Ending of the USERDAO Method GETALLUSERLIST");
		return users;
	}
	

}
