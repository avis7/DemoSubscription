package com.subscription.additional;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
@ManagedBean
@RequestScoped
public  class Util {
	public static String getResourceText(String key) {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			Application app = context.getApplication();
			ResourceBundle bundle = app.getResourceBundle(context, "msg");
			return MessageFormat.format(bundle.getString(key), new Object());
		} catch (MissingResourceException e) {
			return "???" + key + "???";
		}
	}
	
}
