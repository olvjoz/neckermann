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
	private Lodgement lodgement;

	public SingleResult() {
	}

	public SingleResult(Lodgement lodgement) {
		this.lodgement = lodgement;
	}

	public Lodgement getLodgement() {
		return lodgement;
	}

	public void setLodgement(Lodgement lodgement) {
		this.lodgement = lodgement;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
