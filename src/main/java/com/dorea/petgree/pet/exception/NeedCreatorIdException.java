package com.dorea.petgree.pet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NeedCreatorIdException extends RuntimeException {
	public NeedCreatorIdException() { super("Precisa enviar o Id do usuário!"); }
}
