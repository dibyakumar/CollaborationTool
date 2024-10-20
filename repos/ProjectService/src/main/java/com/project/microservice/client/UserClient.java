package com.project.microservice.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="USERSERVICE")
public interface UserClient {
 
}
