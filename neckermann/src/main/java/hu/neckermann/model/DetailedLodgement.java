package hu.neckermann.model;

import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@javax.xml.bind.annotation.XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@javax.xml.bind.annotation.XmlType(
	propOrder = {
		"hotelName",
		"location",
		"rating",
		"description",
		"longDescriptions",
		"features",
		"images",
	}
)
public class DetailedLodgement {

	public DetailedLodgement() {
	}

	@XmlAttribute(required = true)
	private String uri;

	@XmlElement(required = true)
	private String hotelName;
	
	@XmlElement(required = true)
	private String description;
	
	@XmlElementWrapper(name="longDescriptions")
	@XmlElement(name = "description")
	private List<LongDescription> longDescriptions;
	
	@XmlElement(required = true)
	private String location;
	
	@XmlElement(required = true)
	private String rating;
	
	@XmlElementWrapper(name="features")
	@XmlElement(name="feature")
	private Set<String> features;
	
	@XmlElementWrapper(name="images")
	@XmlElement(name="image")
	private Set<String> images;

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<String> getFeatures() {
		return features;
	}

	public void setFeatures(Set<String> features) {
		this.features = features;
	}

	public Set<String> getImages() {
		return images;
	}

	public void setImages(Set<String> images) {
		this.images = images;
	}

	public List<LongDescription> getLongDescriptions() {
		return longDescriptions;
	}

	public void setLongDescriptions(List<LongDescription> longDescriptions) {
		this.longDescriptions = longDescriptions;
	}
}
