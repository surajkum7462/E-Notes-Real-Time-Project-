package com.suraj.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.suraj.dto.EmailRequest;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailService {
	
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String mailFrom;
	
	
	public void send(EmailRequest emailReq) throws Exception, MessagingException
	{
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		
		helper.setFrom(mailFrom,emailReq.getTitle());
		helper.setTo(emailReq.getTo());
		helper.setSubject(emailReq.getSubject());
		helper.setText(emailReq.getMessage(),true);
		
		mailSender.send(mimeMessage);
		
	}

}
