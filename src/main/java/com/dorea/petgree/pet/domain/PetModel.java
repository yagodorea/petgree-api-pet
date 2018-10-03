package com.dorea.petgree.pet.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetModel {

	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Long id;

	private String name;

	private String type;

	private String gender;

	private String size;

	private String color;

	private String status;

	@JsonInclude(JsonInclude.Include.ALWAYS)
	private boolean spots;

	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String description;

	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String image_url;

	private String ong_email;

	private Float lat;

	private Float lon;

	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String created_by;

	public Float getLat() {
		return lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}

	public Float getLon() {
		return lon;
	}

	public void setLon(Float lon) {
		this.lon = lon;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isSpots() {
		return spots;
	}

	public void setSpots(boolean spots) {
		this.spots = spots;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getOng_email() {
		return ong_email;
	}

	public void setOng_email(String ong_email) {
		this.ong_email = ong_email;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
}
