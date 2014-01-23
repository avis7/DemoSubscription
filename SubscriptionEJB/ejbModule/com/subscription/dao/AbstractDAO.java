package com.subscription.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;

import javax.persistence.TypedQuery;

public abstract class AbstractDAO<T, PK extends Serializable> implements
		GenericDAO<T, PK> {
	@PersistenceContext(unitName = "SubscriptionPU")
	 EntityManager em;
	
	private Class<T> type;

	public AbstractDAO(Class<T> type) {
		this.type = type;
	}

		public void create(T o) {
		
		em.persist(o);
	}

	public T read(PK id) {
		
		return em.find(type, id);
	}

	public T update(T o) {
		
		 return em.merge(o);
	}

	public void delete(T o) {
		
		em.remove(em.merge(o));
	}
	public List<T> findAll() {
		String query = "SELECT t FROM " + type.getName() + " t";
		TypedQuery<T> q = em.createQuery(query, type);
		return q.getResultList();
	}
}