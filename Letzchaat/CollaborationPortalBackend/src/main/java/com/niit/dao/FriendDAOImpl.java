package com.niit.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.Friend;
import com.niit.model.User;

@Repository
public class FriendDAOImpl implements FriendDAO{

	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession(){
		return sessionFactory.openSession();
	}
	
	public List<Friend> getFriendList(String username) {
		// TODO Auto-generated method stub
		Session session=getSession();
		Transaction tx=session.beginTransaction();
		Query q=session.createQuery("from User where username in(select username from Friend where friend_name='"+username+"'and friend_request='P')");
		List<Friend> f=q.list();
		tx.commit();
		session.close();
		return f;
	}

	public boolean updateFriend(Friend friend) {
		// TODO Auto-generated method stub
		try
		{Session session=getSession();
		Transaction tx=session.beginTransaction();
		session.update(friend);
		tx.commit();
		session.close();
		return true;
		}
		catch(Exception ex){
			System.out.println(ex);
			return false;
		}
		
	}

	public Friend getFriendRequest(String username, String friend_name) {
		// TODO Auto-generated method stub
		Session session=getSession();
		Transaction tx=session.beginTransaction();
		Query query=session.createQuery("from Friend where username='"+friend_name+"'and friend_name='"+username+"'");
		Friend friend=(Friend) query.uniqueResult();
		System.out.println("Inside getFriend Request"+friend);
		tx.commit();
		session.close();
		return friend;
	}

	public User getFriendDetails(String friend_name) {
		// TODO Auto-generated method stub
		Session session=getSession();
		User u=session.get(User.class, friend_name);
		session.close();
		return u;
	}

	public List<Friend> getFriendAcceptedList(String username) {
		// TODO Auto-generated method stub
		Session session=getSession();
		Transaction tx=session.beginTransaction();
		SQLQuery query=session.createSQLQuery("select * from User where username in(select username from Friend where friend_name=? and friend_request='A' union select friend_name from Friend where username=? and friend_request='A')");
		query.setString(0, username);
		query.setString(1, username);
		query.addEntity(User.class);
		List<Friend> friend=query.list();
		tx.commit();
		session.close();
		return friend;
	}

	public void setOnline(String username) {
		// TODO Auto-generated method stub
		Session session=getSession();
		Transaction tx=session.beginTransaction();
		Query q=session.createQuery("update Friend set is_online='Y' where (username='"+username+"'or friend_name='"+username+"'");
		q.executeUpdate();
		tx.commit();
		session.close();
	}

	public void setOffline(String username) {
		// TODO Auto-generated method stub
		Session session=getSession();
		Transaction tx=session.beginTransaction();
		Query q=session.createQuery("update Friend set is_online='N' where (username='"+username+"'or friend_name='"+username+"'");
		q.executeUpdate();
		tx.commit();
		session.close();
	}

}
