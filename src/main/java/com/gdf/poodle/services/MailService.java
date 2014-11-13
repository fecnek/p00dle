package com.gdf.poodle.services;

import org.springframework.mail.MailSender;

public interface MailService {

	public abstract void setMailSender(MailSender mailSender);

	public abstract void sendMail(String from, String to, String subject,
			String msg);

}