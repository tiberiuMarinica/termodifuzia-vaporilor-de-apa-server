package com.utcb.hidrotehnica.fizicaconstructiilor.dto;

import java.io.Serializable;

public class StratDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	public Double d;
	
	public Double lambda;
	
	public Double miu;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StratDTO [d=");
		builder.append(d);
		builder.append(", lambda=");
		builder.append(lambda);
		builder.append(", miu=");
		builder.append(miu);
		builder.append("]");
		return builder.toString();
	}

	

}
