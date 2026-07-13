package com.ums.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {

	void sendVerificationEmail(String toEmail, String token);

	void sendResetPasswordEmail(String email, String token);
}
