package com.dorea.petgree.pet.exception;

public class NotificationMisuseException extends RuntimeException {
	public NotificationMisuseException(String status) { super("Só é possível notificar o dono de um animal registrado como perdido! O status desse é " + status); }
}
