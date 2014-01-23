package com.subscription.calculator;

import java.math.BigDecimal;
import java.util.List;

import com.subscription.entities.Subscription;
import com.subscription.helpfulenam.Duration;

public interface PriceCalc {
	
	public BigDecimal getSubscriptionPrice(Subscription subscription);
	public BigDecimal getTotalPrice(List<Subscription> order);
}
