package com.subscription.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.subscription.entities.Payment;
import com.subscription.entities.Subscription;
import com.subscription.entities.User;

@Stateless
public class SubscriptionDAO extends AbstractDAO<Subscription, Integer> {
	public SubscriptionDAO() {
		super(Subscription.class);
	}

	public List<Subscription> getUserSubscription(Long id) {
		return em.createNamedQuery("getUserSubscription", Subscription.class)
				.setParameter("id", id).getResultList();
	}

	public List<Subscription> getSubscriptionByPaymentId(Long id) {

		List<Subscription> result = em
				.createNamedQuery("getSubscriptionByPayment",
						Subscription.class).setParameter("id", id)
				.getResultList();
		return result;
	}
}
