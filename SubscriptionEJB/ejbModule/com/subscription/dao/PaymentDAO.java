package com.subscription.dao;

import java.util.Collections;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.subscription.entities.Payment;
import com.subscription.entities.Subscription;

@Stateless
public class PaymentDAO extends AbstractDAO<Payment, Integer> {
	@Inject
	private SubscriptionDAO subscriptionDao;
	
	public PaymentDAO() {
		super(Payment.class);
	}

	public List<Payment> getUserPayments(Long id) {
		List<Payment> result= em.createNamedQuery("getUserPayments", Payment.class)
				.setParameter("id", id).getResultList();
		for (Payment payment: result){
			List<Subscription> subscriptions = subscriptionDao.getSubscriptionByPaymentId(payment.getId());
			payment.setSubscription(subscriptions);	
			
		}
		
		Collections.reverse(result);
		return result;
	}
	
}
