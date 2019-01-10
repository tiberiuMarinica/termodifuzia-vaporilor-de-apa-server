package com.utcb.hidrotehnica.fizicaconstructiilor.formule;

import java.util.List;

import com.utcb.hidrotehnica.fizicaconstructiilor.model.Strat;

public class Formule {

	private static final Double Rsi = 0.125;
	private static final Double Rse = 0.042;
	
	public Double Pi;
	public Double Pe;
	
	private Double Rv;
	public Double Rt;
	
	public Double thetaSI;
	public Double thetaSE;
	
	public Double PsThetaSi;
	public Double PsThetaSe;
	
	public Formule(Double ti, Double te, Double phiInt, Double phiExt, List<Strat> straturi) {
		
		Pi = Ps(ti) * phiInt;
		Pe = Ps(te) * phiExt;
		
		Rv = calculeazaRv(straturi);
		Rt = calculeazaRt(straturi);
		
		thetaSI = calculeazaThetaSI(ti, te);
		thetaSE = calculeazaThetaSE(ti, te);
		
		seteazaTheta(ti, te, straturi);
		seteazaP(straturi);
		
		PsThetaSi = Ps(thetaSI);
		PsThetaSe = Ps(thetaSE);
		
		seteazaPsTheta(straturi);
	}

	private void seteazaPsTheta(List<Strat> straturi) {
		
		for(int i = 0; i < straturi.size() - 1; i++) {
			
			Strat stratCurent = straturi.get(i);
			
			stratCurent.PsTheta = Ps(stratCurent.theta);
		}
	}

	private void seteazaTheta(Double ti, Double te, List<Strat> straturi) {

		for(int i = 0; i < straturi.size() - 1; i++) {
		
			Double sumaRPanaLaI = 0.0;
			for(int k = 0; k <= i; k++) {
				sumaRPanaLaI = sumaRPanaLaI + straturi.get(k).R;
			}
			
			straturi.get(i).theta = ti - ((Rsi + sumaRPanaLaI)/Rt)*(ti - te);
		}
		
	}

	private Double calculeazaThetaSE(Double ti, Double te) {
		return ti - ((Rt - Rse)/Rt) *(ti - te);
	}

	private Double calculeazaThetaSI(Double ti, Double te) {
		return ti - (Rsi/Rt)*(ti - te);
	}

	public static Double Ps(Double theta) {
		
		Double exponent;
		
		if(theta >= 0) {
			exponent = (17.269 * theta) / (237.3 + theta);
		}else {
			exponent = (21.875 * theta) / (265.5 + theta);
		}
		
		return 610.5 * Math.pow(Math.E, exponent);
	}

	private Double calculeazaRv(List<Strat> straturi) {
		
		Double Rv = 0d;
		
		for(Strat s : straturi) {
			
			Rv = Rv + s.Rv;
		}
		
		return Rv;
	}
	
	private Double calculeazaRt(List<Strat> straturi) {
		
		Double Rt = 0d;
		
		for(Strat s : straturi) {
			
			Rt = Rt + s.R;
		}
		
		Rt = Rt + Rsi + Rse;
		
		return Rt;
	}
	
	private void seteazaP(List<Strat> straturi) {
		
		if(Rv == null) {
			throw new IllegalStateException("Rv este null!");
		}

		for(int i = 0; i < straturi.size() - 1; i++) {
			
			Double sumaRvPanaLaI = 0.0;
			for(int k = 0; k <= i; k++) {
				sumaRvPanaLaI = sumaRvPanaLaI + straturi.get(k).Rv;
			}
			
			straturi.get(i).P = Pi - (sumaRvPanaLaI *(Pi - Pe) ) / Rv;
		}
		
	}
	
}
