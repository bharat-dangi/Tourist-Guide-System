package com.minorproject.tourist.guide.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="rating")
public class Rating {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int userId;
	
	private int placeId;
	
	private float preference;
	


	public Rating(int userId, int placeId, float preference) {
		
		this.userId = userId;
		this.placeId = placeId;
		this.preference = preference;
	}


	public Rating() {
		
	}


	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int filmId) {
		this.placeId = filmId;
	}

	public float getPreference() {
		return preference;
	}

	public void setPreference(float preference) {
		this.preference = preference;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	

	
	
	

}
