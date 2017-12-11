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
public class SearchResults {

	@XmlAttribute(required = true)
	private int itemsTotal;

	@XmlAttribute(required = true)
	private int from;

	@XmlAttribute(required = true)
	private int to;

	@XmlElement(name = "lodgement")
	private List<Lodgement> lodgements;

	public SearchResults() {
	}

	public SearchResults(int itemsTotal, int from, int to, List<Lodgement> lodgements) {
		this.itemsTotal = itemsTotal;
		this.from = from;
		this.to = to;
		this.lodgements = lodgements;
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

	public List<Lodgement> getLodgements() {
		return lodgements;
	}

	public void setLodgements(List<Lodgement> lodgements) {
		this.lodgements = lodgements;
	}
}
