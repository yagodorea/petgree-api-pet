package com.dorea.petgree.pet.domain.json;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {

	private Long id;
	private Avatar avatar;
	private String email;
	private Set<String> telefones;
	private Address endereco;
	private Set<Long> owned;

}
