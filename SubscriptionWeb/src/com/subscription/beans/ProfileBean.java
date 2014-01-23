package com.subscription.beans;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.subscription.additional.Util;
import com.subscription.calculator.DateCalculator;
import com.subscription.dao.PaymentDAO;
import com.subscription.dao.SubscriptionDAO;
import com.subscription.dao.UserDAO;
import com.subscription.entities.Payment;
import com.subscription.entities.Subscription;
import com.subscription.entities.User;

@ManagedBean
@SessionScoped
public class ProfileBean {
	private User currentUser;
	private String oldPassword;
	private String newPassword;
	private boolean editable;
	@Inject
	private UserDAO userDao;
	@Inject
	private PaymentDAO paymentDao;
	@Inject
	private SubscriptionDAO subscriptionDao;
	private List<Payment> userPayments;

	@PostConstruct
	private void init() {
		currentUser = userDao.getLoggedUser(FacesContext.getCurrentInstance()
				.getExternalContext().getRemoteUser());
		setEditable(false);
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public User getCurrentUser() {

		return currentUser;
	}

	public StreamedContent showImage() {
		return new DefaultStreamedContent(new ByteArrayInputStream(currentUser
				.getImage().getImageBlob()), "image/png");
	}

	public List<Payment> getUserPayments() {
		userPayments = paymentDao.getUserPayments(currentUser.getId());
		return userPayments;
	}

	public List<Subscription> getUserSubscriptions() {
		return subscriptionDao.getUserSubscription(currentUser.getId());
	}

	public void save() {
		if ((!newPassword.equals("")) && (!oldPassword.equals(""))) {
			currentUser.setPassword(newPassword);
		}
		
		if ((!newPassword.equals("")) & oldPassword.equals("")) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					Util.getResourceText("changeOldPasswordTitle"),
					Util.getResourceText("changeOldPasswordMessage"));
			FacesContext.getCurrentInstance().addMessage(null,message);
			
		}
		else{
		setEditable(false);
		userDao.update(currentUser);
		}
	}

	public String getDaysLeft(Subscription subscription) {
		Date today = new Date();
		Date startDay = subscription.getStartDate();
		int daysToStart = DateCalculator.daysInterval(today, startDay, false);
		if (daysToStart == 0) {
			Date endDate = subscription.getEndDate();
			int daysLeft = DateCalculator.daysInterval(today, endDate, false);
			return "" + daysLeft;
		} else
			return Util.getResourceText("haventStart");

	}

	public void validateOldPassword(FacesContext context,
			UIComponent toValidate, Object value) throws ValidatorException {
		value = (String) value;
		if ((!value.equals(currentUser.getPassword())) && (!value.equals(""))) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					Util.getResourceText("validatorUserOldPassword"), "");
			throw new ValidatorException(message);
		}
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
