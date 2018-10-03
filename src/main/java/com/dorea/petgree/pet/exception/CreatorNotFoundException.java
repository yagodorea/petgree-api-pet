package com.dorea.petgree.pet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CreatorNotFoundException extends RuntimeException {
	public CreatorNotFoundException(String error) { super("Usuário criador não encontrado! Erro: " + error); }
}
