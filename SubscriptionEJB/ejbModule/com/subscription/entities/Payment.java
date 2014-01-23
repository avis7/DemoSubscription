package com.subscription.entities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({ @NamedQuery(name = "getUserPayments", query = "select distinct p from Payment p join p.subscription s join s.user u where u.id=:id"), })
public class Payment {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
	private long id;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="payment_id")
	private List<Subscription> subscription;
	private BigDecimal price;
	@Temporal(TemporalType.DATE)
	private Date date;
	private String cardHolder;
	
	public String getCardHolder() {
		return cardHolder;
	}
	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
		public List<Subscription> getSubscription() {
		return subscription;
	}
	public void setSubscription(List<Subscription> subscription) {
		this.subscription = subscription;
	}
		public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
