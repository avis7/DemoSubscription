package com.subscription.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.subscription.entities.Image;

@Stateless
public class ImageDAO extends AbstractDAO<Image, Integer> {

	public ImageDAO() {
		super(Image.class);
	}

	public Image find(Long id) {
		return em.find(Image.class, id);
	}

	public List<Image> getImageByDestination(String destination) {
		return em.createNamedQuery("getImageByDestination", Image.class)
				.setParameter("destination", destination).getResultList();
	}

	public Image getImageById(Long id) {
		return em.createNamedQuery("getImageById", Image.class)
				.setParameter("id", id).getSingleResult();
	}
	public void removeWithMerge (Image i){
		
		em.remove(em.merge(i));
	}
}
