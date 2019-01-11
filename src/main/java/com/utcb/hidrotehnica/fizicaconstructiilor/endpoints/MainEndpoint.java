package com.utcb.hidrotehnica.fizicaconstructiilor.endpoints;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.utcb.hidrotehnica.fizicaconstructiilor.dto.FormDTO;
import com.utcb.hidrotehnica.fizicaconstructiilor.dto.GraficDTO;
import com.utcb.hidrotehnica.fizicaconstructiilor.dto.ResponseDTO;
import com.utcb.hidrotehnica.fizicaconstructiilor.dto.StratDTO;
import com.utcb.hidrotehnica.fizicaconstructiilor.formule.Formule;
import com.utcb.hidrotehnica.fizicaconstructiilor.model.Strat;


@Path("/grafic")
public class MainEndpoint {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MainEndpoint.class);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/create/{formDTO}")
	public Response getGraphic(@PathParam("formDTO")String string) {
		
		String responseJson = "";
		Gson gson = new Gson();
		try {
		
			String json = new String(Base64.getDecoder().decode(string), StandardCharsets.UTF_8);
			
			FormDTO formDTO = gson.fromJson(json, FormDTO.class);
			
			List<Strat> straturi = new ArrayList<>();
			for(StratDTO sDTO : formDTO.straturi) {
				
				straturi.add(new Strat(sDTO.d, sDTO.lambda, sDTO.miu));
				
			}
			
			Formule f = new Formule(formDTO.tempInt, formDTO.tempExt, formDTO.phiInt, formDTO.phiExt, straturi);
			
			ResponseDTO respDTO = new ResponseDTO();
			respDTO.Pi = f.Pi;
			respDTO.Pe = f.Pe;
			respDTO.thetaSI = f.thetaSI;
			respDTO.thetaSE = f.thetaSE;
			respDTO.PsThetaSi = f.PsThetaSi;
			respDTO.PsThetaSe = f.PsThetaSe;
			respDTO.straturi = straturi;
			
			GraficDTO graficDTO = f.creareGrafic(straturi, f);
			
			
			respDTO.graficBase64 = Base64.getEncoder().encodeToString(graficDTO.content);
			respDTO.inaltimeGrafic = graficDTO.height;
			respDTO.latimeGrafic = graficDTO.width;
			
			responseJson = gson.toJson(respDTO);
			
			System.out.println(responseJson);
		}catch(Exception e) {
			gson.toJson("Server error");
			LOGGER.error(e.getMessage(), e);
		}
		
		return Response.status(200).entity(responseJson).build();
	}
	
	
}
