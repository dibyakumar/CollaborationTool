package com.user.microservice.specifications;


import org.springframework.data.jpa.domain.Specification;

import com.user.microservice.entity.User;

import jakarta.persistence.criteria.Predicate;


public class UserSpecification {
	
	public static Specification<User> searchUserWithOrganizationAndFilters(String organization,String filter){
		return (root,query,criteriabuilder)->{
			Predicate equal = criteriabuilder.equal(root.get("organization"), organization);
			Predicate like = criteriabuilder.like(root.get("userName"), filter.replace('*', '%'));
			return criteriabuilder.and(equal,like);
		};
	}

	public static Specification<User>  findByFilter(String filter) {
		return (root,query,criteriaBuilder)->{
			return criteriaBuilder.like(root.get("userName"), filter.replace('*', '%'));
		};
	}
}
