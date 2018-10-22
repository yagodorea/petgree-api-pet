package com.dorea.petgree.pet.domain;


import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Transient;
//
//@NamedNativeQueries({
//		@NamedNativeQuery(
//				name = "gc_dist",
//				query = "EXEC [procedure_name] :param1 :param2",
//				resultClass = PetDistance.class
//		)
//})
//
//@Entity
public class PetDistance {

//	@Transient
	private Long id;

//	@Transient
	private Double distance;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}
}
