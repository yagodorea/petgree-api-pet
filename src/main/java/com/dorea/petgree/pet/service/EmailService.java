package com.dorea.petgree.pet.service;

public interface EmailService {
	void sendEmail(String to,String from,String subject,String text);
}
