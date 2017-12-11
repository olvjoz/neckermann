package hu.neckermann.model;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@javax.xml.bind.annotation.XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@javax.xml.bind.annotation.XmlType(
	propOrder = {
		"country",
		"regions",
	}
)
public class Country {

	public Country() {
	}

	@XmlElement(required = true)
	private String country;
	
	@XmlElementWrapper(name="regions")
	@XmlElement(name="regio")
	private Set<String> regions;


	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public Set<String> getRegions() {
		return regions;
	}


	public void setRegions(Set<String> regions) {
		this.regions = regions;
	}

}
