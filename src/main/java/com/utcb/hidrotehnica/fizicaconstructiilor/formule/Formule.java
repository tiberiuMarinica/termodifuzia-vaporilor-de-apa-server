package com.utcb.hidrotehnica.fizicaconstructiilor.formule;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.utcb.hidrotehnica.fizicaconstructiilor.dto.GraficDTO;
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
	
	
	//GRAFIC!!!!!!!!!!
	
	public GraficDTO creareGrafic(List<Strat> straturi, Formule f) throws IOException {
		
		GraficDTO gfDTO = new GraficDTO();
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		
		XYSeries xySeries1 = getXYSeries1(f, straturi);
		XYSeries xySeries2 = getXYSeries2(f, straturi);
		
		dataset.addSeries(xySeries1);
		dataset.addSeries(xySeries2);
		
		JFreeChart xylineChart = ChartFactory.createXYLineChart("Termodifuzia vaporilor de apa", "x(m)", "Pa", dataset, PlotOrientation.VERTICAL, true, true, false);
		
		XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) ((XYPlot)xylineChart.getPlot()).getRenderer();
	    renderer.setBaseShapesVisible(true);
		
	    adaugaMarkeriVerticali(dataset, xylineChart);
	    
	    
	    BufferedImage objBufferedImage = xylineChart.createBufferedImage(600,800);
	    ByteArrayOutputStream bas = new ByteArrayOutputStream();
	            try {
	                ImageIO.write(objBufferedImage, "png", bas);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }

	    gfDTO.width = objBufferedImage.getWidth();
	    gfDTO.height = objBufferedImage.getHeight();
	    gfDTO.content = bas.toByteArray();
	            
	    return gfDTO;
	}
	
	private XYSeries getXYSeries1(Formule f, List<Strat> straturi) {
		final XYSeries xySeries = new XYSeries("P(x)");
		
		Double sumaGrosimi = 0.0;
		xySeries.add(sumaGrosimi, f.Pi);
		
		/*Strat primulStrat = straturi.get(0);
		
		sumaGrosimi = sumaGrosimi + primulStrat.getD();
		xySeries.add(sumaGrosimi, f.Pi);*/
		
		
		for(int i = 0; i < straturi.size() - 1; i++) {

			Strat s = straturi.get(i);
			
			//Strat penultim = straturi.get(i-1);

			sumaGrosimi = sumaGrosimi + s.getD();
			xySeries.add(sumaGrosimi, s.P);
		}
		
		sumaGrosimi = sumaGrosimi + straturi.get(straturi.size() - 1).getD();
		
		xySeries.add(sumaGrosimi, f.Pe);
		
		//xySeries.add(sumaGrosimi + 0.05, f.Pe);
		
		
		return xySeries;
	}
	
	private XYSeries getXYSeries2(Formule f, List<Strat> straturi) {
		final XYSeries xySeries = new XYSeries("Ps(x)");
		
		Double sumaGrosimi = 0.0;
		xySeries.add(sumaGrosimi, f.PsThetaSi);
		
		/*Strat primulStrat = straturi.get(0);
		
		sumaGrosimi = sumaGrosimi + primulStrat.getD();
		xySeries.add(sumaGrosimi, f.PsThetaSi);*/
		
		for(int i = 0; i < straturi.size() - 1; i++) {
			
			Strat s = straturi.get(i);
			//Strat penultim = straturi.get(i-1);

			sumaGrosimi = sumaGrosimi + s.getD();
			xySeries.add(sumaGrosimi, s.PsTheta);
		}
		
		sumaGrosimi = sumaGrosimi + straturi.get(straturi.size() - 1).getD();
		
		xySeries.add(sumaGrosimi, f.PsThetaSe);
		
		//xySeries.add(sumaGrosimi + 0.05, f.PsThetaSe);
		
		return xySeries;
	}
	
	

	private void adaugaMarkeriVerticali(XYDataset dataset, JFreeChart xylineChart) {
		XYSeriesCollection dataset0 = (XYSeriesCollection) dataset;
	    XYSeries series0 = dataset0.getSeries(0);
	    //for(int k = 0; k < series0.getItems().size() - 1; k++){
	    for(Object i : series0.getItems()) {
	    	XYDataItem item = (XYDataItem) i;
	    	adaugaMarkeriVerticali(item.getXValue(), xylineChart);
	    }
	}

	private void adaugaMarkeriVerticali(Double x, JFreeChart xylineChart) {
		ValueMarker marker = new ValueMarker(x);
		marker.setPaint(Color.GREEN);
		
		XYPlot plot = (XYPlot) xylineChart.getPlot();
		plot.addDomainMarker(marker);
		
	}
	
	
}
