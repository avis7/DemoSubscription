package com.subscription.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import com.subscription.entities.User;

@Stateless
public class UserDAO extends AbstractDAO<User, Integer> {
	@Inject
private SubscriptionDAO subscriptionDao;
	public UserDAO() {
		super(User.class);

	}

	public User login(String login, String password) {
//		
		User result = em.createNamedQuery("login", User.class)
				.setParameter("login", login)
				.setParameter("password", password).getSingleResult();
		result.setSubscriptions(subscriptionDao.getUserSubscription(result.getId()));
		return result;
	}

	public User getLoggedUser(String login) {
		try{
		User result = em.createNamedQuery("getLoggedUser", User.class)
				.setParameter("login", login)
				.getSingleResult();
			return result;
			
		}
		catch(Exception e){
			return null;
		}
	}
}
