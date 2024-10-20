package com.user.microservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.user.microservice.models.SuccessResponse;
import com.user.microservice.models.UsersDto;
import com.user.microservice.services.ForgotPasswordService;
import com.user.microservice.services.UserService;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/v1/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private ForgotPasswordService forgotPassword;
	
	@GetMapping("/search")
	public ResponseEntity<List<UsersDto>> searchUser(@RequestParam(required=false) String organization , @RequestParam(required=false) String filter){
		return new ResponseEntity<List<UsersDto>>(userService.getUsers(organization,filter),HttpStatus.OK);
	}
	
	@PostMapping("/resetPassword")
	public ResponseEntity<SuccessResponse> updatePassword(@RequestParam(required=false) String oldPassword,@RequestParam String newPassword,@RequestParam(required=false) boolean isForgot){
		return ResponseEntity.ok(userService.updatePassword(oldPassword,newPassword,isForgot));
	}
	
	@GetMapping("/forgotPassword")
	public ResponseEntity<SuccessResponse> forgotPassword(){
		return new ResponseEntity<SuccessResponse>(forgotPassword.forgotPassword(),HttpStatus.OK);
	}
	
	@GetMapping("/validateOtp")
	public ResponseEntity<SuccessResponse> validatepPassword(@RequestParam String otp){
		return new ResponseEntity<SuccessResponse>(forgotPassword.validateOtp(otp),HttpStatus.OK);
	}
	
	@PostMapping("/updateProfile")
	public ResponseEntity<SuccessResponse> postMethodName(@RequestBody UsersDto dto) {
		return ResponseEntity.ok(userService.updateUserProfile(dto));
	}
	
	@GetMapping("/byId/{id}")
	public ResponseEntity<UsersDto> getByUserId(@PathVariable String id){
		return ResponseEntity.ok(userService.getUserById(id));
	}
	
	
}
