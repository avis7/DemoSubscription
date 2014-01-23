package com.subscription.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.subscription.entities.User;
import com.subscription.helpfulenam.Duration;

@Entity
@NamedQueries({ @NamedQuery(name = "getUserSubscription", query = "select s from Subscription s join s.user u where u.id=:id"),
	@NamedQuery(name = "getSubscriptionByPayment", query = "select s from Subscription s join s.payment p where p.id=:id")})
public class Subscription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Temporal(TemporalType.DATE)
	private Date startDate;
	@Temporal(TemporalType.DATE)
	private Date endDate;
	@Enumerated(EnumType.STRING)
	private Duration duration;
	@OneToOne
	private Edition edition;
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	private BigDecimal price;
	@ManyToOne
	@JoinColumn(name="payment_id")
	private Payment payment;
	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Edition getEdition() {
		return edition;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEdition(Edition edition) {
		this.edition = edition;
	}

	public Subscription(){
		
	}
	public Subscription(Date startDate, Duration duration, Edition edition) {
		super();
		this.startDate = startDate;
		this.duration = duration;
		this.edition = edition;
		
	
	}
	
}
