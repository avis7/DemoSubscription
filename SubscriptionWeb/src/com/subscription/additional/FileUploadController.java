package com.subscription.additional;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;

import com.subscription.beans.ImageBean;
import com.subscription.dao.ImageDAO;
import com.subscription.entities.Image;
@ManagedBean
@RequestScoped
public class FileUploadController {
	@Inject
	private ImageDAO dao;
	@ManagedProperty(value="#{imageBean}")
	private ImageBean imageBean;
	
	 public void handleFileUpload(FileUploadEvent event) {  
	        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");  
	        FacesContext.getCurrentInstance().addMessage(null, msg); 
	        byte[] content = event.getFile().getContents();
	        Image image = new Image();
	        image.setImageBlob(content);
	        image.setDestination("main");
	        dao.create(image);
	        image = dao.update(image);
	        imageBean.getAllImages().put(image.getId(),image);
	        imageBean.getIndexList().add(image.getId().toString());
	    }

	public ImageBean getImageBean() {
		return imageBean;
	}

	public void setImageBean(ImageBean imageBean) {
		this.imageBean = imageBean;
	}  
	 
}
