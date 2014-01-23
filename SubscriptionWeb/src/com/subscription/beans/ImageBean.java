package com.subscription.beans;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import javax.inject.Inject;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.subscription.additional.Util;
import com.subscription.dao.ImageDAO;
import com.subscription.entities.Image;

@ManagedBean
@SessionScoped
public class ImageBean {
	@Inject
	private ImageDAO dao;
	private HashMap<Long, Image> allImages;
	private List<String> indexList;
	@PostConstruct
	public void init(){
		allImages = new HashMap<Long, Image>();
		indexList = new ArrayList<String>();
		for (Image i : dao.getImageByDestination("main")) {
			indexList.add(i.getId().toString());
			allImages.put(i.getId(), i);
		}
		
	}

	public void deleteImage(String index) {
		FacesMessage errorMessage = null;
		try {
			Image image = allImages.get(Long.parseLong(index));
			dao.removeWithMerge(image);
			indexList.remove(index);
			allImages.remove(image.getId());
		} catch (Exception e) {
			errorMessage = new FacesMessage(FacesMessage.SEVERITY_WARN,
					Util.getResourceText("deleteError"), Util.getResourceText("failedToDeleteImage"));
			FacesContext.getCurrentInstance().addMessage(null, errorMessage);
		}
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
			return new DefaultStreamedContent(new ByteArrayInputStream(
					allImages.get(Long.parseLong(id)).getImageBlob()),
					"image/png");
		}
	}

	public List<String> getIndexList() {
		return indexList;
	}

	public HashMap<Long, Image> getAllImages() {
		return allImages;
	}

}
