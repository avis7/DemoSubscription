package com.subscription.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({ @NamedQuery(name = "getImageByDestination", query = "select i from Image i where i.destination = :destination"), 
	 @NamedQuery(name = "getImageById", query = "select i from Image i where i.id = :id")})
public class Image {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Long id;
	private String destination;
	@Basic(optional = false, fetch = FetchType.LAZY)
	@Lob
	@Column(name = "image_blob", length=10000000)    //This will generate MEGABLOB
	private byte[] imageBlob;
	
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public byte[] getImageBlob() {
		return imageBlob;
	}
	public void setImageBlob(byte[] imageBlob) {
		this.imageBlob = imageBlob;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
