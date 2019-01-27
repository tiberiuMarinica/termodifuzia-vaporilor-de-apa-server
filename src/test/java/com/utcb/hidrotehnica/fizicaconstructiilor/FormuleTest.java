package com.utcb.hidrotehnica.fizicaconstructiilor;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.utcb.hidrotehnica.fizicaconstructiilor.formule.Formule;
import com.utcb.hidrotehnica.fizicaconstructiilor.model.Strat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FormuleTest {

	@Test
	public void test() throws Exception {

		Double d1 = 0.12;
		Double d2 = 0.08;
		Double d3 = 0.08;
		Double d4 = 0.1;
		
		Double lambda1 = 1.74;
		Double lambda2 = 0.04;
		Double lambda3 = 1.74;
		Double lambda4 = 0.04;
		
		Double miu1 = 21.3;
		Double miu2 = 1.1;
		Double miu3 = 21.3;
		Double miu4 = 30d;
		
		Double ti = 20d;
		Double te = -15d;
		Double phi1 = 0.6;
		Double phi2 = 0.8;
		
		List<Strat> straturi = new ArrayList<>();
		straturi.add(new Strat(d1, lambda1, miu1));
		straturi.add(new Strat(d2, lambda2, miu2));
		straturi.add(new Strat(d3, lambda3, miu3));
		straturi.add(new Strat(d4, lambda4, miu4));

		Formule f = new Formule(ti, te, phi1, phi2, straturi);

		assertEquals(f.Pi, Double.valueOf(1402.170686281405));
		assertEquals(f.Pe, Double.valueOf(131.79589853311677));

		assertEquals(f.thetaSI, Double.valueOf(19.085099836790224));
		assertEquals(f.thetaSE, Double.valueOf(-14.692593545161515));

		assertEquals(f.PsThetaSi, Double.valueOf(2207.837284907343));
		assertEquals(f.PsThetaSe, Double.valueOf(169.49357241931764));

		creareGrafic(straturi, f);

	}
	
	private void creareGrafic(List<Strat> straturi, Formule f) throws IOException {
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		
		XYSeries xySeries1 = f.getXYSeries1(f, straturi);
		XYSeries xySeries2 = f.getXYSeries2(f, straturi);
		
		dataset.addSeries(xySeries1);
		dataset.addSeries(xySeries2);
		
		JFreeChart xylineChart = ChartFactory.createXYLineChart("Termodifuzia vaporilor de apa", "x(m)", "Pa", dataset, PlotOrientation.VERTICAL, true, true, false);
		
		XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) ((XYPlot)xylineChart.getPlot()).getRenderer();
	    renderer.setBaseShapesVisible(true);
		
	    f.adaugaMarkeriVerticali(dataset, xylineChart);
	    
		int width = 640; /* Width of the image */
		int height = 480; /* Height of the image */
		File file = new File("D:\\termodifuzia_vaporilor\\XYLineChart.jpeg");
		ChartUtilities.saveChartAsJPEG(file, xylineChart, width, height);
	}

}
