package com.subscription.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import com.subscription.entities.Edition;

@Stateless
public class EditionDAO extends AbstractDAO<Edition, Integer> {

	public EditionDAO() {
		super(Edition.class);
	}

	public List<Edition> getEditionByType(String type) {
		return em.createNamedQuery("getEditionsByType", Edition.class)
				.setParameter("type", type).getResultList();
	}

	public int count(String type) {
		return Integer.parseInt(((Long) em.createNamedQuery("count")
				.setParameter("type", type).getSingleResult()).toString());
	}

	public List<Edition> getResultList(int first, int pageSize, String type) {
		return em.createNamedQuery("getEditionsByType", Edition.class)
				.setFirstResult(first).setMaxResults(pageSize)
				.setParameter("type", type).getResultList();
	}
	@Override 
	public List<Edition> findAll(){
		List<Edition>result = em.createNamedQuery("findActive",Edition.class).getResultList();
		return result;
	}
}
