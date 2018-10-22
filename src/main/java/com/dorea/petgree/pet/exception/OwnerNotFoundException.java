package com.dorea.petgree.pet.exception;

public class OwnerNotFoundException extends RuntimeException {
	public OwnerNotFoundException() { super("Dono não encontrado!"); }
}
