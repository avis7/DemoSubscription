package com.subscription.locale;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

@ManagedBean(name="language")
@SessionScoped
public class LanguageBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String localeCode;
	private static Locale currentLocale;
	

	private static Map<String,Locale> countries;
	static{
		
		countries = new LinkedHashMap<String,Locale>();
		countries.put("English", new Locale("en")); //label, value
		countries.put("Українська",  new Locale("ua"));
		FacesContext context = FacesContext.getCurrentInstance(); 
		currentLocale = context.getApplication().getDefaultLocale();
	}
//
//	public LanguageBean(){
//		FacesContext context = FacesContext.getCurrentInstance(); 
//		currentLocale = context.getApplication().getDefaultLocale();
//	}
	
	public Locale getCurrentLocale() {
		return currentLocale;
	}

	public Map<String, Locale> getCountriesInMap() {
		return countries;
	}


	public String getLocaleCode() {
		return localeCode;
	}


	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	//value change event listener
	public void countryLocaleCodeChanged(ValueChangeEvent e){

		String newLocaleValue = e.getNewValue().toString();

		//loop country map to compare the locale code
               for (Map.Entry<String, Locale> entry : countries.entrySet()) {

       	   if(entry.getValue().toString().equals(newLocaleValue)){
       		   currentLocale=entry.getValue();
       		FacesContext.getCurrentInstance()
       			.getViewRoot().setLocale(currentLocale);

       	  }
              }
	}

}
