package com.gdf.poodle.services;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MailServiceImplementation implements MailService {
	
		private MailSender mailSender;
	 
		/* (non-Javadoc)
		 * @see com.gdf.poodle.services.MailService#setMailSender(org.springframework.mail.MailSender)
		 */
		@Override
		public void setMailSender(MailSender mailSender) {
			this.mailSender = mailSender;
		}
	 
		/* (non-Javadoc)
		 * @see com.gdf.poodle.services.MailService#sendMail(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
		 */
		@Override
		public void sendMail(String from, String to, String subject, String msg) {
	 
			SimpleMailMessage message = new SimpleMailMessage();
	 
			message.setFrom(from);
			message.setTo(to);
			message.setSubject(subject);
			message.setText(msg);
			mailSender.send(message);	
		}

}
