package hu.neckermann.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Countries {

	@XmlAttribute(required = true)
	private int itemsTotal;

	@XmlAttribute(required = true)
	private int from;

	@XmlAttribute(required = true)
	private int to;

	@XmlElement(name = "countries")
	private List<Country> countries;

	public Countries() {
	}

	public Countries(int itemsTotal, int from, int to, List<Country> countries) {
		this.itemsTotal = itemsTotal;
		this.from = from;
		this.to = to;
		this.countries = countries;
	}

	public int getItemsTotal() {
		return itemsTotal;
	}

	public void setItemsTotal(int itemsTotal) {
		this.itemsTotal = itemsTotal;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public List<Country> getCountries() {
		return countries;
	}

	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}

}
