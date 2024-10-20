package com.chatService.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.chatService.Exception.ServiceException;
import com.chatService.config.DynamicTopicName;
import com.chatService.entity.Message;
import com.chatService.entity.Team;
import com.chatService.entity.User;
import com.chatService.model.MessagesDto;
import com.chatService.model.SuccessResponse;
import com.chatService.repository.MessageRepository;
import com.chatService.repository.TeamRepository;
import com.chatService.repository.UserRepository;
import com.chatService.util.AppConstants;
import com.chatService.util.ChatUtility;

@Service
public class ChatService {

	@Autowired
	private KafkaAdmin kafkaAdmin;
	
	@Autowired
	private MessageRepository messageRepo;
	
	@Autowired
	private UserRepository urepo;
	
	@Autowired
	private ChatUtility utility;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplete;

	@Autowired
	private TeamRepository teamrepo;
	
	private static final Logger logger =  LoggerFactory.getLogger(ChatService.class);
	
//	// to manually control consumer start and stop
//	@Autowired
//	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
//	
	@Autowired
	private TeamRepository teamRepo; 

	public SuccessResponse createTopicInKafka(String topicName) {
		try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
			NewTopic newTopic = new NewTopic(topicName, (short) 1, (short) 3);
			CreateTopicsResult topics = adminClient.createTopics(Collections.singleton(newTopic));
			topics.all().whenComplete((v, throwable) -> {
			    if (throwable != null) {
			        System.err.println("Failed to create topic: " + throwable.getMessage());
			    } else {
			        System.out.println("Topic created successfully");
			    }
			});
			return SuccessResponse.builder().message("Topic created").build();
		} catch (Exception e) {
			throw new ServiceException("Topic creation failed", HttpStatusCode.valueOf(400));
		}
	}

	public SuccessResponse sendMessage(String message) {
		// get the team id from sender and notify them
		String currentUsername = utility.getCurrentUsername();
		User user = urepo.findByUserName(currentUsername);
		Team team = teamrepo.findByUserId(user.getId());
		if(null == team)
			throw new ServiceException("Team Not found", HttpStatusCode.valueOf(400));
//		teamrepo.findBy
		Message msg = new Message();
		msg.setMessageText(message);
		msg.setSenderId(currentUsername);
		msg.setTeamId(team.getTeam_id().toString());
		msg.setTimestamp(System.currentTimeMillis()+"");
		messageRepo.save(msg);
		
		
		
		
		// for each members send the notification
		for(User usr : team.getMembers()) {
		String metadata = ":"+utility.getCurrentUsername()+":"+usr.getEmail()+":"+usr.getUserName();
		if(usr.getId() != user.getId()) {
		CompletableFuture<SendResult<String, String>> future = kafkaTemplete.send(AppConstants.KAFKA_TOPIC, message+metadata);
		future.thenAccept(result -> {
		    // Success callback
			logger.info("Notification Sent seccessfully");
		}).exceptionally(ex -> {
		    // Failure callback
				logger.error("Failed to send message: " + ex.getMessage());
		    return null;
		});
		}
		
		}
		
		return SuccessResponse.builder().message("Message sent").build();
	}

	public List<MessagesDto> getMessage(Long teamId) {
		List<Message> messages = messageRepo.findByTeamId(teamId.toString());
		List<MessagesDto> listMsg = new ArrayList<>();
		for(Message msg : messages) {
			listMsg.add(MessagesDto.builder().text(msg.getMessageText()).senderName(msg.getSenderId()).build());
		}
		return listMsg;
	}

}
