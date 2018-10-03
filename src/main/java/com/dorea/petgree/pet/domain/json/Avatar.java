package com.dorea.petgree.pet.domain.json;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Avatar implements Serializable {

	private Long id;
	private String name;
	private String imageUrl;
	private String bio;
	private int idade;

}
