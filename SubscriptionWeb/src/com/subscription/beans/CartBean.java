package com.subscription.beans;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.subscription.additional.Util;
import com.subscription.calculator.DateCalculator;
import com.subscription.calculator.PriceCalcImpl;
import com.subscription.dao.PaymentDAO;
import com.subscription.dao.SubscriptionDAO;
import com.subscription.dao.UserDAO;

import com.subscription.entities.Edition;
import com.subscription.entities.Payment;
import com.subscription.entities.Subscription;
import com.subscription.entities.User;
import com.subscription.helpfulenam.Duration;

@ManagedBean
@SessionScoped
public class CartBean {
	@Inject
	private PriceCalcImpl calc;
	@Inject
	private PaymentDAO paymentDao;
	@Inject
	private UserDAO userDao;
	@Inject 
	private SubscriptionDAO subscriptionDao;
	private List<Subscription> preOrder;
	BigDecimal total;
	private String cardHolder;

	@PostConstruct
	private void init() {
		total = new BigDecimal(0);
		preOrder = new ArrayList<Subscription>();
	}

	public String getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	public PriceCalcImpl getCalc() {
		return calc;
	}

	public void setCalc(PriceCalcImpl calc) {
		this.calc = calc;
	}

	public List<Subscription> getPreOrder() {
		return preOrder;
	}

	public void setPreOrder(List<Subscription> preOrder) {
		this.preOrder = preOrder;
	}

	public void addToCart(Edition edition, Duration duration) {
		if (edition != null) {

			Subscription subscription = new Subscription(
					DateCalculator.addDays(new Date(), 1), duration, edition);
			Date endDate = DateCalculator.addMonths(
					subscription.getStartDate(), duration.getAmount());
			subscription.setEndDate(endDate);
			BigDecimal price = calc.getSubscriptionPrice(subscription);
			subscription.setPrice(price);
			preOrder.add(subscription);
			countTotalPrice();
		}
	}

	public void countTotalPrice() {
		total = calc.getTotalPrice(preOrder);
	}

	public StreamedContent getImage() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the view. Return a stub StreamedContent so
			// that it will generate right URL.
			return new DefaultStreamedContent();
		} else {
			String id = context.getExternalContext().getRequestParameterMap()
					.get("id");
			// Edition edition = preOrder.get(id).getEdition();
			return new DefaultStreamedContent(new ByteArrayInputStream(preOrder
					.get(Integer.parseInt(id)).getEdition().getImage()
					.getImageBlob()), "image/png");
		}
	}

	public String delete(Subscription subscription) {
		preOrder.remove(subscription);
		countTotalPrice();
		return null;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public void doPayment() {
		FacesMessage msg = null;
		try {
			User currentUser = userDao.getLoggedUser(FacesContext
					.getCurrentInstance().getExternalContext().getRemoteUser());
			Payment payment = new Payment();
			payment.setCardHolder(cardHolder);
			payment.setDate(new Date());
			payment.setPrice(total);
			
			for (Subscription subs:preOrder) {
				subs.setUser(currentUser);
				
			}
			payment.setSubscription(preOrder);
			paymentDao.create(payment);
			//
					
//			currentUser.getSubscriptions().addAll(preOrder);
	//		userDao.update(currentUser);
			
			preOrder.clear();
			countTotalPrice();
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					Util.getResourceText("successfulTransaction"), "");
		} catch (Exception e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					Util.getResourceText("errorWrongMessage"), "");
		} finally {
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}
}
