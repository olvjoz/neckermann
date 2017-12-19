package hu.neckermann.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SingleResult {

	@XmlElement(name = "lodgement")
	private DetailedLodgement lodgement;

	public SingleResult() {
	}

	public SingleResult(DetailedLodgement lodgement) {
		this.lodgement = lodgement;
	}

	public DetailedLodgement getLodgement() {
		return lodgement;
	}

	public void setLodgement(DetailedLodgement lodgement) {
		this.lodgement = lodgement;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
