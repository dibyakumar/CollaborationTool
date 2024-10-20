package com.chatService.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.chatService.Exception.ServiceException;
import com.chatService.config.DynamicTopicName;
import com.chatService.entity.Team;
import com.chatService.entity.User;
import com.chatService.repository.TeamRepository;
import com.chatService.util.AppConstants;
import com.chatService.util.ChatUtility;

@Component
public class KafkaConsumer {
	
	@Autowired
	private ChatUtility utility;
	
	

	@KafkaListener(topics =AppConstants.KAFKA_TOPIC, groupId = AppConstants.GROUPID)
	public void notifyUsers(String message) {
		// message : sender : email : receiver
			String[] split = message.split(":");
			utility.notifyByMail(split[2],"New Message For you",htmlConent(split[3], split[1],split[0]));
	}
	
	
	private String htmlConent(String reciverName,String senderName , String message) {
		return "<!DOCTYPE html>\n"
				+ "<html lang=\"en\">\n"
				+ "<head>\n"
				+ "    <meta charset=\"UTF-8\">\n"
				+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
				+ "    <title>New Message Notification</title>\n"
				+ "    <style>\n"
				+ "        body {\n"
				+ "            font-family: Arial, sans-serif;\n"
				+ "            background-color: #f4f4f4;\n"
				+ "            margin: 0;\n"
				+ "            padding: 0;\n"
				+ "        }\n"
				+ "        .container {\n"
				+ "            background-color: #ffffff;\n"
				+ "            max-width: 600px;\n"
				+ "            margin: 0 auto;\n"
				+ "            padding: 20px;\n"
				+ "            border-radius: 8px;\n"
				+ "            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);\n"
				+ "        }\n"
				+ "        .header {\n"
				+ "            background-color: #007bff;\n"
				+ "            color: #ffffff;\n"
				+ "            padding: 10px 0;\n"
				+ "            text-align: center;\n"
				+ "            border-radius: 8px 8px 0 0;\n"
				+ "        }\n"
				+ "        .content {\n"
				+ "            padding: 20px;\n"
				+ "            color: #333333;\n"
				+ "        }\n"
				+ "        .message-info {\n"
				+ "            margin: 15px 0;\n"
				+ "            padding: 15px;\n"
				+ "            background-color: #f9f9f9;\n"
				+ "            border-left: 4px solid #007bff;\n"
				+ "            border-radius: 4px;\n"
				+ "        }\n"
				+ "        .footer {\n"
				+ "            margin-top: 30px;\n"
				+ "            padding: 10px;\n"
				+ "            background-color: #007bff;\n"
				+ "            color: #ffffff;\n"
				+ "            text-align: center;\n"
				+ "            border-radius: 0 0 8px 8px;\n"
				+ "        }\n"
				+ "        a {\n"
				+ "            color: #007bff;\n"
				+ "            text-decoration: none;\n"
				+ "        }\n"
				+ "    </style>\n"
				+ "</head>\n"
				+ "<body>\n"
				+ "    <div class=\"container\">\n"
				+ "        <div class=\"header\">\n"
				+ "            <h2>New Message in Your Team Chat</h2>\n"
				+ "        </div>\n"
				+ "        <div class=\"content\">\n"
				+ "            <p>Hello <strong>"+reciverName+"</strong>,</p>\n"
				+ "            <p>You have a new message : <strong></strong>.</p>\n"
				+ "            <div class=\"message-info\">\n"
				+ "                <p><strong>Message:</strong></p>\n"
				+ "                <p>"+message+"</p>\n"
				+ "                <p><strong>From:</strong>"+senderName+"</p>\n"
				+ "            </div>\n"
				+ "            <p>You can view and respond to this message by visiting your team's chat page:</p>\n"
				+ "            <p><a href=\"{{chatUrl}}\">Go to Chat</a></p>\n"
				+ "        </div>\n"
				+ "        <div class=\"footer\">\n"
				+ "            <p>Thank you for using our platform!</p>\n"
				+ "            <p>&copy; 2024 Team Collaboration</p>\n"
				+ "        </div>\n"
				+ "    </div>\n"
				+ "</body>\n"
				+ "</html>\n"
				+ "";
	}
}
