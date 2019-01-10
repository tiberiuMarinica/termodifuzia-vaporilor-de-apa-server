package com.utcb.hidrotehnica.fizicaconstructiilor.endpoints;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dto.FormDTO;


@Path("/grafic")
public class MainEndpoint {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MainEndpoint.class);
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.TEXT_PLAIN)
	@Path("/create")
	public Response getGraphic(FormDTO formDTO) {
		
		
		
		return null;
	}
	
	
}
