package com.dorea.petgree.pet.service.impl;

import com.dorea.petgree.pet.exception.EmailSendException;
import com.dorea.petgree.pet.service.EmailService;
import com.sun.mail.smtp.SMTPMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class EmailServiceImpl implements EmailService {
	@Autowired
	JavaMailSender emailSender;

	public void sendEmail(String to,String from,String subject,String text) {
		try {
			Properties props = new Properties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", "email-smtp.us-east-1.amazonaws.com");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.auth", true);
			props.put("mail.smtp.starttls.enable", true);
			props.put("mail.smtp.starttls.required", true);
			Session session = Session.getDefaultInstance(props);
			MimeMessage message = new MimeMessage(session);
			message.setRecipients(Message.RecipientType.TO,to);
			message.setFrom(from);
			message.setSubject(subject);
			message.setText(text);
			message.setContent(text,"text/html; charset=utf-8");

			emailSender.send(message);
		} catch (MailException | MessagingException e) {
			throw new EmailSendException(e.getMessage());
		}
	}
}
