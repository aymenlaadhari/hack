package model;

import java.util.Date;

import com.google.android.gms.maps.model.LatLng;

public class Meetup {
	private String name,cause,description;
	private LatLng place;
	private Date date;

	
		
	public Meetup() {
		super();
	}
	public Meetup(String name, String cause, String description, LatLng place,
			Date date) {
		super();
		this.name = name;
		this.cause = cause;
		this.description = description;
		this.place = place;
		this.date = date;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LatLng getPlace() {
		return place;
	}
	public void setPlace(LatLng place) {
		this.place = place;
	}
	

}
