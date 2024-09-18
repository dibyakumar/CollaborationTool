package com.user.microservice.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.microservice.Exception.ServiceException;
import com.user.microservice.Repository.UserRepository;
import com.user.microservice.entity.User;
import com.user.microservice.models.SuccessResponse;
import com.user.microservice.models.UsersDto;
import com.user.microservice.specifications.UserSpecification;
import com.user.microservice.util.Userutility;

@Service
public class UserService {

	@Autowired
	private UserRepository urepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private Userutility utility;
	
	@Autowired
	private ForgotPasswordService forgotPasswordService;

	public List<UsersDto> getUsers(String organization, String filter) {
		List<UsersDto> listUserDto = new ArrayList<>();
		// if no filters send all users
		if (null == organization && null == filter) {
			List<User> allUsers = urepo.findAll();
			for (User user : allUsers) {
				listUserDto.add(UsersDto.builder().userName(user.getUserName()).email(user.getEmail())
						.organization(user.getOrganization()).build());
			}
		}

		// if both organization and filter present
		else if (!organization.isEmpty() && !filter.isEmpty() && null != organization && null != filter) {
			Specification<User> searchUserWithOrganizationAndFilters = UserSpecification
					.searchUserWithOrganizationAndFilters(organization, filter);
			List<User> allUser = urepo.findAll(searchUserWithOrganizationAndFilters);
			for (User user : allUser) {
				listUserDto.add(UsersDto.builder().userName(user.getUserName()).email(user.getEmail())
						.organization(user.getOrganization()).build());
			}
		}

		// if only organization
		else if (!organization.isEmpty() && null != organization) {
			List<User> allUsers = urepo.findByOrganization(organization);
			for (User user : allUsers) {
				listUserDto.add(UsersDto.builder().userName(user.getUserName()).email(user.getEmail())
						.organization(user.getOrganization()).build());
			}
		}

		// if only filter
		else if (!filter.isEmpty() && null != filter) {
			Specification<User> byFilter = UserSpecification.findByFilter(filter);
			List<User> userList = urepo.findAll(byFilter);
			for (User user : userList) {
				listUserDto.add(UsersDto.builder().userName(user.getUserName()).email(user.getEmail())
						.organization(user.getOrganization()).build());
			}
		}

		return listUserDto;
	}

	public SuccessResponse updatePassword(String oldPassword, String newPassword, boolean isForgot) {
		User user = utility.getUserFromUserName();
		if (!isForgot) {
			// match with encoded password
			if (passwordEncoder.matches(oldPassword, user.getPassword())) {
				user.setPassword(passwordEncoder.encode(newPassword));
				urepo.save(user);
			} else {
				throw new ServiceException("Password mismatch", HttpStatusCode.valueOf(400));
			}
		} else {
			if(forgotPasswordService.otpValidationResult(user.getEmail())) {
			user.setPassword(passwordEncoder.encode(newPassword));
			urepo.save(user);
			}
			else {
				throw new ServiceException("Time out", HttpStatusCode.valueOf(400));
			}
		}
		return SuccessResponse.builder().message("Successfully updated").build();
	}

	public SuccessResponse updateUserProfile(UsersDto dto) {
		User byUserName = utility.getUserFromUserName();
		if(null != dto.getOrganization()) {
			byUserName.setOrganization(dto.getOrganization());
		}
		if(null != dto.getUserName()) {
			byUserName.setUserName(dto.getUserName());
		}
		if(null != dto.getEmail()) {
			byUserName.setEmail(dto.getUserName());
		}
		
		urepo.save(byUserName);
		return SuccessResponse.builder().message("Profile Update SuccessFully").build();
	}

	public UsersDto getUserById(String id) {
		User user = utility.getUserFromUserName();
		return UsersDto.builder().email(user.getEmail()).userName(user.getUserName()).organization(user.getOrganization()).build();
	}

}
