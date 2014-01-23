package com.subscription.beans;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.subscription.additional.Util;
import com.subscription.calculator.DateCalculator;
import com.subscription.dao.ImageDAO;
import com.subscription.dao.UserDAO;
import com.subscription.entities.Image;
import com.subscription.entities.Subscription;
import com.subscription.entities.User;

@ManagedBean
@SessionScoped
public class RegistrationBean {
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private Date dob;
	private String gender;
	private String email;
	private String address;
	private byte[] content;
	@Inject
	private UserDAO dao;
	@Inject
	private ImageDAO imageDao;
	@ManagedProperty(value = "#{loginBean}")
	LoginBean login;

	@PostConstruct
	private void init() {
		content = imageDao.getImageByDestination("default").get(0)
				.getImageBlob();

	}

	public LoginBean getLogin() {
		return login;
	}

	public void setLogin(LoginBean login) {
		this.login = login;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String username) {
		this.userName = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void validateEmail(FacesContext context, UIComponent toValidate,
			Object value) throws ValidatorException {
		String emailStr = (String) value;
		if (-1 == emailStr.indexOf("@")) {
			FacesMessage message = new FacesMessage("Invalid email address");
			throw new ValidatorException(message);
		}
	}
public void validateDob(FacesContext context, UIComponent toValidate,
		Object value) throws ValidatorException {
		boolean goOn;
		goOn=DateCalculator.isAfterDay((Date) value, new Date());
		if(goOn){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR ,Util.getResourceText("validatorRegistrationDob"),"");
			throw new ValidatorException(message);
		}
}

	public String addConfirmedUser() {
		FacesMessage doneMessage = null;
		String redirect = null;
		try {
			User user = new User();
			user.setDob(dob);
			user.setEmail(email);
			user.setFirstName(firstName);
			user.setGender(gender);
			user.setLastName(lastName);
			user.setLogin(userName);
			user.setPassword(password);
			user.setRole("user");
			user.setSubscriptions(new ArrayList<Subscription>());
			user.setAddress(address);
			Image image = new Image();
			image.setDestination("user");
			image.setImageBlob(content);
			user.setImage(image);
			dao.create(user);
			login.setPassword(password);
			login.setUsername(userName);
			login.login();
			doneMessage = new FacesMessage(FacesMessage.SEVERITY_INFO,
					Util.getResourceText("registrationInfo"), Util.getResourceText("successfullyAddedUser"));
			redirect = "index?faces-redirect=true";
		} catch (Exception e) {
			doneMessage = new FacesMessage(FacesMessage.SEVERITY_WARN,
					Util.getResourceText("registrationError"), Util.getResourceText("failedToAddUser"));
		} finally {
			FacesContext.getCurrentInstance().addMessage(null, doneMessage);
		}
		return redirect;
	}

	public void loginCheck(FacesContext context, UIComponent component,
			Object value) {
		if (dao.getLoggedUser((String) value)!=null) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,"Error", Util.getResourceText("loginExist"));
			throw new ValidatorException(message);
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		content = event.getFile().getContents();
	}

	public StreamedContent showImage() {
		return new DefaultStreamedContent(new ByteArrayInputStream(content),
				"image/png");
	}
}
