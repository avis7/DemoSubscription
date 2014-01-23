package com.subscription.beans;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;


import org.primefaces.context.RequestContext;

import com.subscription.additional.Util;
import com.subscription.dao.UserDAO;
import com.subscription.entities.User;
@ManagedBean(name="loginBean")
@SessionScoped
public class LoginBean
{
    private String username;  

    private String password;  
    @Inject
    private UserDAO dao;
    private String originalURL;
    private User currentUser= new User();
    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        originalURL = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);

        if (originalURL == null) {
            originalURL = externalContext.getRequestContextPath() + "/index.xhtml";
        } else {
            String originalQuery = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_QUERY_STRING);

            if (originalQuery != null) {
                originalURL += "?" + originalQuery;
            }
        }
    }
    
    public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public String getUsername() {  
        return username;  
    }  

    public void setUsername(String username) {  
        this.username = username;  
    }  

    public String getPassword() {  
        return password;  
    }  

    public void setPassword(String password) {  
        this.password = password;  
    }  
    public User getAuthentificateUser(){
    	return dao.getLoggedUser(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
    }
    public void login() throws IOException {  
    	FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        FacesMessage msg = null;  
        boolean loggedIn = false;  
        try {
            request.login(username, password);
            currentUser = dao.login(username, password);
            externalContext.getSessionMap().put("user", currentUser);
            
           // externalContext.redirect(originalURL);
            loggedIn = true;  
            
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, Util.getResourceText("welcome"), currentUser.getFirstName());
              
            
        } catch (ServletException e) {
            // Handle unknown username/password in request.login().
        	loggedIn = false;  
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, Util.getResourceText("loginError"), Util.getResourceText("invalidCredentials"));  
        }
        finally{
        context.addMessage(null, msg);  
        context.getExternalContext().getFlash().setKeepMessages(true);
        RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);  
        }   
      
         
    }  
    public void logout() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml");
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, Util.getResourceText("goodBye"),"");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
   