package com.dorea.petgree.pet.exception;

public class EmailSendException extends RuntimeException {
	public EmailSendException(String message) { super("Erro enviando email! " + message); }
}
