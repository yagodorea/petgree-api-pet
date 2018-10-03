package com.dorea.petgree.pet.domain.json;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address implements Serializable {

	private Long id;
	private String rua;
	private String cidade;
	private String estado;
	private String cep;
	private String complemento;
	private int numero;

}
