package com.ums.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ums.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendVerificationEmail(String toEmail, String token) {

		SimpleMailMessage mail = new SimpleMailMessage();

		mail.setTo(toEmail);

		mail.setSubject("Verify Your Account");

		mail.setText("Welcome to User Management System.\n\n" + "Click below link to activate your account.\n\n"
				+ "http://localhost:8080/verify?token=" + token);

		mailSender.send(mail);

	}

	@Override
	public void sendResetPasswordEmail(String email, String token) {

		SimpleMailMessage mail = new SimpleMailMessage();

		mail.setTo(email);

		mail.setSubject("Reset Password");

		mail.setText("Click below link to reset password.\n\n" + "http://localhost:8080/reset-password?token=" + token);

		mailSender.send(mail);

	}

}