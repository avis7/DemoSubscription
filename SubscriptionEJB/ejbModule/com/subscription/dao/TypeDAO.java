package com.subscription.dao;


import java.util.List;

import javax.ejb.Stateless;

import com.subscription.entities.Type;

@Stateless

public class TypeDAO extends AbstractDAO<Type, Integer> {

	public TypeDAO() {
		super(Type.class);
		
	}
public List<String> getTypeName(){
	return em.createNamedQuery("getTypeName", String.class).getResultList();
}
public Type getTypeByName(String name){
	return em.createNamedQuery("getTypeByName", Type.class).setParameter("name", name).getSingleResult();
}
}
