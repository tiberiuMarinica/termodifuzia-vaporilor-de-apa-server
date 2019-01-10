package dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FormDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement
	public Double ti;

	@XmlElement
	public Double te;

	@XmlElement
	public Double phiInterior;

	@XmlElement
	public Double phiExterior;

	@XmlElement
	public transient List<Strat> straturi;

	public Double getTi() {
		return ti;
	}

	public void setTi(Double ti) {
		this.ti = ti;
	}

	public Double getTe() {
		return te;
	}

	public void setTe(Double te) {
		this.te = te;
	}

	public Double getPhiInterior() {
		return phiInterior;
	}

	public void setPhiInterior(Double phiInterior) {
		this.phiInterior = phiInterior;
	}

	public Double getPhiExterior() {
		return phiExterior;
	}

	public void setPhiExterior(Double phiExterior) {
		this.phiExterior = phiExterior;
	}

	public List<Strat> getStraturi() {
		return straturi;
	}

	public void setStraturi(List<Strat> straturi) {
		this.straturi = straturi;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((phiExterior == null) ? 0 : phiExterior.hashCode());
		result = prime * result + ((phiInterior == null) ? 0 : phiInterior.hashCode());
		result = prime * result + ((te == null) ? 0 : te.hashCode());
		result = prime * result + ((ti == null) ? 0 : ti.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormDTO other = (FormDTO) obj;
		if (phiExterior == null) {
			if (other.phiExterior != null)
				return false;
		} else if (!phiExterior.equals(other.phiExterior))
			return false;
		if (phiInterior == null) {
			if (other.phiInterior != null)
				return false;
		} else if (!phiInterior.equals(other.phiInterior))
			return false;
		if (te == null) {
			if (other.te != null)
				return false;
		} else if (!te.equals(other.te))
			return false;
		if (ti == null) {
			if (other.ti != null)
				return false;
		} else if (!ti.equals(other.ti))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FormDTO [ti=");
		builder.append(ti);
		builder.append(", te=");
		builder.append(te);
		builder.append(", phiInterior=");
		builder.append(phiInterior);
		builder.append(", phiExterior=");
		builder.append(phiExterior);
		builder.append("]");
		return builder.toString();
	}

}
