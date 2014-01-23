package com.subscription.beans;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.SortOrder;

import com.subscription.additional.Util;
import com.subscription.dao.EditionDAO;
import com.subscription.dao.ImageDAO;
import com.subscription.dao.TypeDAO;
import com.subscription.entities.Edition;
import com.subscription.helpfulenam.Duration;
import com.subscription.helpfulenam.Period;

@ManagedBean
@SessionScoped
public class EditionBean {
	@Inject
	private EditionDAO editionDao;
	@Inject
	private TypeDAO typeDao;
	@Inject
	private ImageDAO imageDao;
	private List<Edition> editionList;
	
	private LazyDataModel<Edition> model;
	private boolean editable;
	private Duration duration;
	private Edition currentEdition;
	@ManagedProperty(value = "#{cartBean}")
	private CartBean cartBean;
	
	
	@PostConstruct
	private void init(){
	
	editable = false;
	currentEdition = new Edition();
	}
	
	public SelectItem[] getDurationValues() {
		SelectItem[] items = new SelectItem[Duration.values().length];
		int i = 0;
		for (Duration d : Duration.values()) {
			items[i++] = new SelectItem(d, Util.getResourceText(d.getLable()));
		}
		return items;
	}

	public SelectItem[] getPeriodValues() {
		SelectItem[] items = new SelectItem[Period.values().length];
		int i = 0;
		for (Period d : Period.values()) {
			items[i++] = new SelectItem(d, Util.getResourceText(d.getLable()));
		}
		return items;
	}

	public CartBean getCartBean() {
		return cartBean;
	}

	public void setCartBean(CartBean cartBean) {
		this.cartBean = cartBean;
	}

	public List<Edition> getEditionList() {
		return editionList;
	}

	public void setEditionList(List<Edition> editionList) {
		this.editionList = editionList;
	}

		public List<Edition> getList(final String type) {
		
		editionList = editionDao.getEditionByType(type);
		
	
//		 model = new LazyDataModel<Edition> () {
//		
//		 /**
//		 *
//		 */
//		 private static final long serialVersionUID = 5268304259837401106L;
//		 private List<Edition> data = new ArrayList<Edition>();
//		
//		 @Override
//		 public List<Edition> load(int first, int pageSize, String sortField,
//		 SortOrder sortOrder, Map<String, String> filters) {
//		 model.setRowCount(editionDao.count(type));
//		 return editionDao.getResultList(first, pageSize, type);
//		 }
//		 @Override
//		 public void setRowIndex(int rowIndex) {
//		 /*
//		 * The following is in ancestor (LazyDataModel):
//		 * this.rowIndex = rowIndex == -1 ? rowIndex : (rowIndex % pageSize);
//		 */
//		 if (rowIndex == -1 || getPageSize() == 0) {
//		 super.setRowIndex(-1);
//		 }
//		 else
//		 super.setRowIndex(rowIndex % getPageSize());
//		 }
//		
//		
//		 };
//		
//		 model.setRowCount(editionDao.count(type));
//		 return model;
		return editionList;
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

			return new DefaultStreamedContent(new ByteArrayInputStream(imageDao
					.getImageById(Long.parseLong(id)).getImageBlob()),
					"image/png");
		}
	}

	public void makeOrder(Edition edition) {
		cartBean.addToCart(edition, duration);
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
				.put("edition", edition);

	}
	public void updateEdition() {
		editionDao.update(currentEdition);
		editable = false;
	}
	public void changeEdition(Edition edition){
			currentEdition=edition;
		setEditable(true);
	}
	public void delete(Edition edition){
		if(edition!=null){
		try{
			editionList.remove(edition);
			edition.setDeleted(true);
			editionDao.update(edition);
			
		}catch(Exception e){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,Util.getResourceText("deleteError"),"");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	}
	public LazyDataModel<Edition> getModel() {
		return model;
	}

	

	

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public List<String> getTypes() {
		return typeDao.getTypeName();
	}

	public Edition getCurrentEdition() {
		return currentEdition;
	}

	public void setCurrentEdition(Edition currentEdition) {
		this.currentEdition = currentEdition;
	}
	
	
	
}
