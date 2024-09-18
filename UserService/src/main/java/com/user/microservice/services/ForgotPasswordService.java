package com.user.microservice.services;

import java.util.concurrent.TimeUnit;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.user.microservice.Exception.ServiceException;
import com.user.microservice.Repository.UserRepository;
import com.user.microservice.constants.Constant;
import com.user.microservice.entity.User;
import com.user.microservice.models.SuccessResponse;
import com.user.microservice.util.Userutility;

import jakarta.mail.MessagingException;

@Service
public class ForgotPasswordService {
	
	@Autowired
	private Userutility utility;
	@Autowired
	private MailService mailServiec;
	@Autowired
	private UserRepository urepo;
	private String email;
	
	
	
	private Cache<String, String> cache;
	
	
	public ForgotPasswordService() {
		 // Initialize cache with a 2-minute expiration
		this.cache = Caffeine.newBuilder()
                .expireAfterWrite(4, TimeUnit.MINUTES)
                .maximumSize(1000) // Optional: limit cache size
                .build();
	}
	
	// store the otp in cache 
	public SuccessResponse forgotPassword() {
		String otp = utility.generateOtp();
		User user = urepo.findByUserName(utility.getCurrentUsername());
		email = user.getEmail();
		// stored in cache for 2min
		cache.put(email, otp);
		// send email
		try {
			mailServiec.sendEmail(user.getEmail(),Constant.SUBJECT,createHtmlContent(user.getUserName(),otp));
		} catch (MessagingException e) {
			throw new ServiceException(e.getMessage(), HttpStatusCode.valueOf(400));
		}
		return SuccessResponse.builder().message("Otp Send to your eamail please check ").build();
	}
	
	// validate the entered code 
	public SuccessResponse validateOtp(String otp) {
		@Nullable
		String ifPresent = cache.getIfPresent(email);
		if(null != ifPresent && ifPresent.equals(otp)) {
			cache.cleanUp();
			cache.put(email, "true");
			return SuccessResponse.builder().message("Otp Validation Successfull").build();
		}
		else {
			throw new ServiceException("Invalid/Expired Otp",HttpStatusCode.valueOf(400));
		}
	}
	
	
	// get otp validation result
	public Boolean otpValidationResult(String email) {
		return null != cache.getIfPresent(email)? Boolean.valueOf(cache.getIfPresent(email)):false;
	}
	
	
	private String createHtmlContent(String userName , String otp) {
		return "<!DOCTYPE html>\n"
				+ "<html lang=\"en\">\n"
				+ "<head>\n"
				+ "    <meta charset=\"UTF-8\">\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
				+ "    <title>Email</title>\n"
				+ "    <style>\n"
				+ "        body {\n"
				+ "            font-family: Arial, sans-serif;\n"
				+ "            background-color: #f4f4f4;\n"
				+ "            margin: 0;\n"
				+ "            padding: 0;\n"
				+ "        }\n"
				+ "        .email-container {\n"
				+ "            max-width: 600px;\n"
				+ "            margin: 0 auto;\n"
				+ "            background-color: #ffffff;\n"
				+ "            padding: 20px;\n"
				+ "            border-radius: 8px;\n"
				+ "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n"
				+ "        }\n"
				+ "        .header {\n"
				+ "            background-color: #4CAF50;\n"
				+ "            padding: 10px;\n"
				+ "            text-align: center;\n"
				+ "            color: white;\n"
				+ "            border-radius: 8px 8px 0 0;\n"
				+ "        }\n"
				+ "        h1 {\n"
				+ "            margin: 0;\n"
				+ "        }\n"
				+ "        .content {\n"
				+ "            padding: 20px;\n"
				+ "            text-align: left;\n"
				+ "        }\n"
				+ "        .content p {\n"
				+ "            font-size: 16px;\n"
				+ "            color: #333333;\n"
				+ "            line-height: 1.6;\n"
				+ "        }\n"
				+ "        .otp {\n"
				+ "            font-size: 24px;\n"
				+ "            font-weight: bold;\n"
				+ "            color: #4CAF50;\n"
				+ "            text-align: center;\n"
				+ "            margin-top: 20px;\n"
				+ "            margin-bottom: 20px;\n"
				+ "        }\n"
				+ "        .footer {\n"
				+ "            text-align: center;\n"
				+ "            padding: 10px;\n"
				+ "            color: #888888;\n"
				+ "            font-size: 12px;\n"
				+ "        }\n"
				+ "    </style>\n"
				+ "</head>\n"
				+ "<body>\n"
				+ "\n"
				+ "    <div class=\"email-container\">\n"
				+ "        <div class=\"header\">\n"
				+ "            <h1>Your One-Time Password (OTP)</h1>\n"
				+ "        </div>\n"
				+ "        <div class=\"content\">\n"
				+ "            <p>Dear "+userName+",</p>\n"
				+ "            <p>As per your request, here is your One-Time Password (OTP) to proceed with the password reset:</p>\n"
				+ "            <p class=\"otp\">"+otp+"</p>\n"
				+ "            <p>Please enter this code within the next 10 minutes to continue with the password reset process. If you didn't request this, please ignore this email.</p>\n"
				+ "            <p>If you have any questions, feel free to contact our support team.</p>\n"
				+ "        </div>\n"
				+ "        <div class=\"footer\">\n"
				+ "            <p>Thank you,<br>"+Constant.COMPANY_NAME+"</p>\n"
				+ "            <p>&copy; 2024 Your Company. All Rights Reserved.</p>\n"
				+ "        </div>\n"
				+ "    </div>\n"
				+ "\n"
				+ "</body>\n"
				+ "</html>\n"
				+ "";
	}
}
