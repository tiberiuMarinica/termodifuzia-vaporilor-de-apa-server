package com.utcb.hidrotehnica.fizicaconstructiilor.dto;

import java.io.Serializable;
import java.util.Arrays;

public class FormDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	public Double tempInt;

	public Double tempExt;

	public Double phiInt;

	public Double phiExt;

	public StratDTO[] straturi;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FormDTO [tempInt=");
		builder.append(tempInt);
		builder.append(", tempExt=");
		builder.append(tempExt);
		builder.append(", phiInt=");
		builder.append(phiInt);
		builder.append(", phiExt=");
		builder.append(phiExt);
		builder.append(", straturi=");
		builder.append(Arrays.toString(straturi));
		builder.append("]");
		return builder.toString();
	}

	
	
}
