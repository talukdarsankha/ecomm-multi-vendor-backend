package com.xyz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.xyz.Response.OtpResponse;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public OtpResponse sendVerificationOtp(String userEmail, String subject, String text) throws Exception {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
			
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(text);
			mimeMessageHelper.setTo(userEmail);
			javaMailSender.send(mimeMessage);
			
			OtpResponse otpResponse = new OtpResponse();
			
			otpResponse.setMessage("We are Successfully sent a otp in your email");
			otpResponse.setStatus("success");
              return otpResponse;
			
		} catch (Exception e) {
			// TODO: handle exception
			OtpResponse otpResponse = new OtpResponse();
			otpResponse.setMessage("Failed to send email");
			otpResponse.setStatus("error");
              return otpResponse;
		}
	}

}
