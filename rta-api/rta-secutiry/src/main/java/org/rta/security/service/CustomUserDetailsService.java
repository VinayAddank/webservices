package org.rta.security.service;

import java.util.ArrayList;
import java.util.List;

import org.rta.core.entity.user.UserEntity;
import org.rta.core.repository.UserRepository;
import org.rta.security.model.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	public CustomUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUserName(username);

		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		} else {
			List<String> userRoles = new ArrayList<String>();
			userRoles.add(user.getUserType().toString());
			return new CustomUserDetail(user.getUserId(), user.getPassword(), user.getUserName(), user.getStatus(),
					userRoles);
		}
	}
}
