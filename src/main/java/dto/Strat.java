package dto;

import java.io.Serializable;

public class Strat implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * d in metri!
	 * @param d
	 * @param lambda
	 * @param miu
	 */
	public Strat(Double d, Double lambda, Double miu) {
		super();

		Rv = d * miu;

		R = d / lambda;
		
		this.d = d;
	}

	private Double d;
	
	public Double Rv;

	public Double R;

	public Double theta;
	
	public Double P;

	public Double PsTheta;
	
	public Double getD() {
		return this.d ;
	}
	
}

