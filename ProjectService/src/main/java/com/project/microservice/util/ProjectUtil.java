package com.project.microservice.util;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.github.benmanes.caffeine.cache.Cache;
import com.project.microservice.Exception.ServiceException;
import com.project.microservice.constants.ProjectConstants;
import com.project.microservice.model.MailRequest;


@Component
public class ProjectUtil {

	// All methods should be public,static and generic
	
	@Autowired
	private WebClient.Builder webClient;
	@Autowired
	private Cache<String, String> localCache;
	
	public String getCurrentUsername() {
	     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    
	    if (authentication != null && authentication.isAuthenticated()) {
	        Object principal = authentication.getPrincipal();
	        
	        if (principal instanceof UserDetails) {
	            return ((UserDetails) principal).getUsername(); // return the username
	        } else {
	            return principal.toString(); // In case of anonymous or non-UserDetails authentication
	        }
	    }
	    
	    throw new ServiceException("user Not found",HttpStatusCode.valueOf(400));
	}
	
	public String generateHtmlContentForNewTeamMember(String memeberName,String teamName) {
		return "<!DOCTYPE html>\n"
				+ "<html lang=\"en\">\n"
				+ "<head>\n"
				+ "    <meta charset=\"UTF-8\">\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
				+ "    <title>Team Invitation</title>\n"
				+ "    <style>\n"
				+ "        body {\n"
				+ "            font-family: Arial, sans-serif;\n"
				+ "            background-color: #f4f4f4;\n"
				+ "            padding: 20px;\n"
				+ "        }\n"
				+ "        .container {\n"
				+ "            background-color: #ffffff;\n"
				+ "            padding: 20px;\n"
				+ "            border-radius: 5px;\n"
				+ "            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);\n"
				+ "        }\n"
				+ "        .header {\n"
				+ "            font-size: 24px;\n"
				+ "            color: #333333;\n"
				+ "            margin-bottom: 20px;\n"
				+ "        }\n"
				+ "        .content {\n"
				+ "            font-size: 16px;\n"
				+ "            color: #555555;\n"
				+ "            line-height: 1.6;\n"
				+ "        }\n"
				+ "        .footer {\n"
				+ "            margin-top: 30px;\n"
				+ "            font-size: 14px;\n"
				+ "            color: #999999;\n"
				+ "        }\n"
				+ "        .highlight {\n"
				+ "            color: #0066cc;\n"
				+ "            font-weight: bold;\n"
				+ "        }\n"
				+ "    </style>\n"
				+ "</head>\n"
				+ "<body>\n"
				+ "    <div class=\"container\">\n"
				+ "        <div class=\"header\">Team Invitation</div>\n"
				+ "        <div class=\"content\">\n"
				+ "            <p>Hello <span class=\"highlight\">"+memeberName+"</span>,</p>\n"
				+ "            <p>We are excited to inform you that you have been added to the team \"<span class=\"highlight\">"+teamName+"</span>\" by <span class=\"highlight\">"+getCurrentUsername()+"</span>.</p>\n"
				+ "            <p>You can now collaborate with your team members and contribute to the project.</p>\n"
				+ "            <p>If you have any questions, feel free to reach out.</p>\n"
				+ "        </div>\n"
				+ "        <div class=\"footer\">\n"
				+ "            Best regards,<br>\n"
				+ "            "+"Collaboration Team"+"\n"
				+ "        </div>\n"
				+ "    </div>\n"
				+ "</body>\n"
				+ "</html>\n"
				+ "";
	}
	
	
	public String generateHtmlContentForNewTaskNotify(String memberName,String taskTitle,String projectName,LocalDate dueDate,String taskDescription) {
		return "<!DOCTYPE html>\n"
				+ "<html lang=\"en\">\n"
				+ "<head>\n"
				+ "    <meta charset=\"UTF-8\">\n"
				+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
				+ "    <title>Task Assignment Notification</title>\n"
				+ "    <style>\n"
				+ "        body {\n"
				+ "            font-family: Arial, sans-serif;\n"
				+ "            background-color: #f4f4f4;\n"
				+ "            margin: 0;\n"
				+ "            padding: 20px;\n"
				+ "        }\n"
				+ "        .container {\n"
				+ "            max-width: 600px;\n"
				+ "            background-color: #ffffff;\n"
				+ "            padding: 20px;\n"
				+ "            margin: auto;\n"
				+ "            border-radius: 8px;\n"
				+ "            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\n"
				+ "        }\n"
				+ "        .header {\n"
				+ "            background-color: #007bff;\n"
				+ "            color: white;\n"
				+ "            text-align: center;\n"
				+ "            padding: 10px 0;\n"
				+ "            border-radius: 8px 8px 0 0;\n"
				+ "        }\n"
				+ "        .content {\n"
				+ "            padding: 20px;\n"
				+ "            line-height: 1.6;\n"
				+ "            color: #333;\n"
				+ "        }\n"
				+ "        .content h2 {\n"
				+ "            color: #007bff;\n"
				+ "            margin-bottom: 10px;\n"
				+ "        }\n"
				+ "        .task-details {\n"
				+ "            background-color: #f9f9f9;\n"
				+ "            padding: 10px;\n"
				+ "            border-left: 4px solid #007bff;\n"
				+ "            margin-bottom: 20px;\n"
				+ "        }\n"
				+ "        .footer {\n"
				+ "            text-align: center;\n"
				+ "            color: #888;\n"
				+ "            font-size: 12px;\n"
				+ "            margin-top: 20px;\n"
				+ "        }\n"
				+ "        .button {\n"
				+ "            display: inline-block;\n"
				+ "            background-color: #007bff;\n"
				+ "            color: white;\n"
				+ "            padding: 10px 20px;\n"
				+ "            border-radius: 5px;\n"
				+ "            text-decoration: none;\n"
				+ "            margin-top: 20px;\n"
				+ "        }\n"
				+ "    </style>\n"
				+ "</head>\n"
				+ "<body>\n"
				+ "\n"
				+ "    <div class=\"container\">\n"
				+ "        <div class=\"header\">\n"
				+ "            <h1>Task Assignment Notification</h1>\n"
				+ "        </div>\n"
				+ "        \n"
				+ "        <div class=\"content\">\n"
				+ "            <h2>"+memberName+",</h2>\n"
				+ "            <p>You have been assigned a new task. Please find the details of the task below:</p>\n"
				+ "\n"
				+ "            <div class=\"task-details\">\n"
				+ "                <strong>Task Title:</strong> "+taskTitle+" <br>\n"
				+ "                <strong>Project:</strong> "+projectName+"<br>\n"
				+ "                <strong>Due Date:</strong> "+dueDate+"<br>\n"
				+ "                <strong>Description:</strong> "+taskDescription+" \n"
				+ "            </div>\n"
				+ "\n"
				+ "            <p>Please make sure to complete the task by the due date.</p>\n"
				+ "\n"
				+ "            <a href=\"[Task Link]\" class=\"button\">View Task</a>\n"
				+ "        </div>\n"
				+ "\n"
				+ "        <div class=\"footer\">\n"
				+ "            <p>Thank you!</p>\n"
				+ "            <p><strong>"+"Collaboration Team"+"</strong></p>\n"
				+ "        </div>\n"
				+ "    </div>\n"
				+ "\n"
				+ "</body>\n"
				+ "</html>\n"
				+ "";
	}

	public void notifyByMail(String email, String subject, String htmlContent) {
		webClient.build()
		.post()
		.uri(ProjectConstants.MAIL_URI)
		.header(ProjectConstants.AUTH,localCache.getIfPresent(getCurrentUsername()))
		.bodyValue(MailRequest.builder().to(email).subject(subject).content(htmlContent).build())
		.retrieve()
		.bodyToMono(Void.class) // this specifies we dont need any thing in response (Void) just only a success status 
		.subscribe();
	}
}
