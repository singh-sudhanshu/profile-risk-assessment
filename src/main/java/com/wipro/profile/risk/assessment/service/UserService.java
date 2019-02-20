package com.wipro.profile.risk.assessment.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.wipro.profile.risk.assessment.model.User;
import com.wipro.profile.risk.assessment.model.UserRegistrationDto;

public interface UserService extends UserDetailsService {
	
	User findUserByEmail(String email);
	User save(UserRegistrationDto registration);
}
