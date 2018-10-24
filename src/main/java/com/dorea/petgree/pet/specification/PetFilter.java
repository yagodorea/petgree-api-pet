package com.dorea.petgree.pet.specification;

public class PetFilter {
	private String type;
	private String[] colors;
	private String gender;
	private String raca;
	private String pelo;
	private String size;
	private String status;
	private Double lat;
	private Double lon;
	private Double radius;

	public void setAllFilters(String type, String[] colors, String gender, String raca, String pelo, String size, String status, Double lat, Double lon, Double radius) {
		this.type = type;
		this.colors= colors;
		this.gender = gender;
		this.raca = raca;
		this.pelo = pelo;
		this.size = size;
		this.status = status;
		this.lat = lat;
		this.lon = lon;
		this.radius = radius;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getColors() {
		return colors;
	}

	public void setColors(String[] colors) {
		this.colors = colors;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRaca() {
		return raca;
	}

	public void setRaca(String raca) {
		this.raca = raca;
	}

	public String getPelo() {
		return pelo;
	}

	public void setPelo(String pelo) {
		this.pelo = pelo;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}
}
