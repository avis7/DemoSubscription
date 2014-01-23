package com.demo.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.subscription.dao.EditionDAO;
import com.subscription.entities.Edition;



@Path("/edition")
@Stateless
public class UserService {
@Inject EditionDAO dao;
@GET
@Produces(MediaType.APPLICATION_XML)
@Path("/listAll")
public List<Edition> getAll() {
	return dao.findAll();
}
}
