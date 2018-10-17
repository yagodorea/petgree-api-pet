package com.dorea.petgree.pet.specification;

public class PetFilter {
	private String type;
	private String color;
	private String gender;
	private String raca;
	private String pelo;
	private String size;
	private String status;

	public void setAllFilters(String type, String color, String gender, String raca, String pelo, String size, String status) {
		this.type = type;
		this.color= color;
		this.gender = gender;
		this.raca = raca;
		this.pelo = pelo;
		this.size = size;
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
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
}
