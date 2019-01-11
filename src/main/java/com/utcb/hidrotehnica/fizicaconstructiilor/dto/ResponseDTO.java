package com.utcb.hidrotehnica.fizicaconstructiilor.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.utcb.hidrotehnica.fizicaconstructiilor.model.Strat;

public class ResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	public Double Pi;
	public Double Pe;
	public Double thetaSI;
	public Double thetaSE;
	public Double PsThetaSi;
	public Double PsThetaSe;
	
	public List<Strat> straturi;
	
	public String graficBase64;
	public int inaltimeGrafic;
	public int latimeGrafic;

	
	
}
