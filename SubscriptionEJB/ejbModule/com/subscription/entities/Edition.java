package com.subscription.entities;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

import com.subscription.helpfulenam.Period;

@Entity
@NamedQueries({ @NamedQuery(name = "getEditionsByType", query = "select e from Edition e where e.type.name=:type and e.deleted = false"),
	@NamedQuery(name = "count", query = "select count(e) from Edition e where e.type.name=:type"),
	@NamedQuery(name = "findActive", query = "select e from Edition e where e.deleted = false")})
@XmlRootElement
public class Edition {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private BigDecimal price;
	private String name;
	private String descriptionEN;
	private String descriptionUA;
	private boolean deleted=false;
	@Enumerated(EnumType.STRING)
	private Period period;
	@OneToOne(cascade = CascadeType.ALL/* , fetch=FetchType.LAZY */)
	private Image image;
	@ManyToOne
	private Type type;
	
	public Edition(){
	}
		
	public Edition(BigDecimal price, String name, String descriptionEN, String descriptionUA,
			Period period, Image image, Type type) {
		super();
		this.price = price;
		this.name = name;
		this.descriptionEN = descriptionEN;
		this.descriptionUA = descriptionUA;
		this.period = period;
		this.image = image;
		this.type = type;
	}




	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription(String locale) {
		if(locale.equals("ua")){
			return descriptionUA;
		}
		else return descriptionEN;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public String getDescriptionEN() {
		return descriptionEN;
	}

	public void setDescriptionEN(String descriptionEN) {
		this.descriptionEN = descriptionEN;
	}

	public String getDescriptionUA() {
		return descriptionUA;
	}

	public void setDescriptionUA(String descriptionUA) {
		this.descriptionUA = descriptionUA;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
}
