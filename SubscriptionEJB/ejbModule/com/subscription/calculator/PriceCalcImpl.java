package com.subscription.calculator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import com.subscription.entities.Subscription;
import com.subscription.helpfulenam.Duration;
import com.subscription.helpfulenam.Period;

public class PriceCalcImpl implements PriceCalc, Serializable {
	@Inject
	DateCalculator dataCalc;

	public DateCalculator getDataCalc() {
		return dataCalc;
	}

	public void setDataCalc(DateCalculator dataCalc) {
		this.dataCalc = dataCalc;
	}

	@Override
	public BigDecimal getSubscriptionPrice(Subscription subscription) {
		Duration duration = subscription.getDuration();
		Date startDate = subscription.getStartDate();
		Date endDate;
		endDate = dataCalc.addMonths(startDate, duration.getAmount());
		switch (subscription.getEdition().getPeriod()) {
		case daily: {
			return subscription.getEdition().getPrice().multiply(
					new BigDecimal(dataCalc.daysInterval(startDate, endDate,
							false)));
		}
		case weekly: {
			int weekAmount = (int) Math.floor(dataCalc.daysInterval(startDate,
					endDate, false) / 7);
			return subscription.getEdition().getPrice().multiply(new BigDecimal(weekAmount));

		}

		case twice_per_month:{
			int weekAmount = (int) Math.floor(dataCalc.daysInterval(startDate,
					endDate, false) / 14);
			return subscription.getEdition().getPrice().multiply(new BigDecimal(weekAmount));
		}
			
		case monthly:
			return subscription.getEdition().getPrice().multiply(new BigDecimal(duration.getAmount())) ;
		case yearly:
			return subscription.getEdition().getPrice();
			
		}

		return null;
	}

	@Override
	public BigDecimal getTotalPrice(List<Subscription> order) {
		BigDecimal total = new BigDecimal(0);
		for (Subscription sub : order) {
		total=total.add(sub.getPrice());			
		}
		return total;
	}

}
