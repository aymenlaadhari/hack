package model;

import com.google.android.gms.maps.model.LatLng;

public class Members {
	private String image, name, community;
	private LatLng position;
	
	
	public Members() {
		super();
	}
	
	public Members(String image, String name, String community, LatLng position) {
		super();
		this.image = image;
		this.name = name;
		this.community = community;
		this.position = position;
	}

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public LatLng getPosition() {
		return position;
	}
	public void setPosition(LatLng position) {
		this.position = position;
	}
	

}
