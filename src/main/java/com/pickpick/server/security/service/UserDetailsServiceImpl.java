package com.pickpick.server.security.service;

import com.pickpick.server.domain.Users;
import com.pickpick.server.repository.UsersRepository;
import com.pickpick.server.security.domain.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UsersRepository usersRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Users users = usersRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException(email));
		return new UserDetailsImpl(users);
	}
}
