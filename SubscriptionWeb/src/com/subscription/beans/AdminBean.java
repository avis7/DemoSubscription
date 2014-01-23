package com.subscription.beans;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.subscription.additional.Util;
import com.subscription.dao.EditionDAO;
import com.subscription.dao.ImageDAO;
import com.subscription.dao.PaymentDAO;
import com.subscription.dao.TypeDAO;
import com.subscription.dao.UserDAO;
import com.subscription.entities.Edition;
import com.subscription.entities.Image;
import com.subscription.entities.Payment;
import com.subscription.entities.Type;
import com.subscription.entities.User;
import com.subscription.helpfulenam.Period;

@ManagedBean
@SessionScoped
public class AdminBean {
	private Edition newEdition;
	private String type;
	private byte[] content;
	private User chosenOne;
	private List<User> users;
	private List<User> filteredUsers;
	@Inject
	private EditionDAO editionDao;
	@Inject
	private ImageDAO imageDao;
	@Inject
	private TypeDAO typeDao;
	@Inject
	private UserDAO userDao;
	@Inject
	private PaymentDAO paymentDao;
	private List<Payment> userPayments;
	@PostConstruct
	private void init() {
		newEdition= new Edition();
		content = imageDao.getImageByDestination("default_magazine").get(0)
				.getImageBlob();
		users = userDao.findAll();
		chosenOne = new User();
	}

	public void addEdition() {
		FacesMessage msg = null;
		try {
			Image image = new Image();
			image.setImageBlob(content);
			image.setDestination("edition");
			Type type = typeDao.getTypeByName(this.type);
			newEdition.setImage(image);
			newEdition.setType(type);
			editionDao.create(newEdition);
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO,newEdition.getName()+" "+Util.getResourceText("newEdition"),
					Util.getResourceText("newEditionAdded"));
			newEdition= new Edition();
			content = imageDao.getImageByDestination("default_magazine").get(0)
					.getImageBlob();
		} catch (Exception e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					Util.getResourceText("errorWrongMessage"), "");
		} finally {
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		content = event.getFile().getContents();
	}

	public StreamedContent showImage() {
		return new DefaultStreamedContent(new ByteArrayInputStream(content),
				"image/png");
	}

	public List<String> getTypeValues() {
		return typeDao.getTypeName();
	}
	public List<Payment> getUserPayments(User chosenOne ){
		if(!this.chosenOne.equals(chosenOne)){
		this.chosenOne=chosenOne;
		userPayments = paymentDao.getUserPayments(chosenOne.getId());
		}
		return userPayments;	
	}
	public List<User> getUsers() {
		return users;
	}

	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public User getChosenOne() {
		return chosenOne;
	}

	public List<User> getFilteredUsers() {
		return filteredUsers;
	}

	public void setFilteredUsers(List<User> filteredUsers) {
		this.filteredUsers = filteredUsers;
	}

	public void setChosenOne(User chosenOne) {
		this.chosenOne = chosenOne;
	}

	public Edition getNewEdition() {
		return newEdition;
	}

	public void setNewEdition(Edition newEdition) {
		this.newEdition = newEdition;
	}

}
