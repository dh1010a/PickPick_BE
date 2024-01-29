package com.pickpick.server.service;

import static com.pickpick.server.config.SecurityConfig.passwordEncoder;

import com.pickpick.server.domain.Users;
import com.pickpick.server.dto.UserSignupDto;
import com.pickpick.server.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UsersService {
	private final UsersRepository usersRepository;

	public Long save(UserSignupDto userSignupDto) {
		return usersRepository.save(Users.builder()
				.name(userSignupDto.getName())
				.email(userSignupDto.getEmail())
				.password(passwordEncoder().encode(userSignupDto.getPassword()))
				.phoneNum(userSignupDto.getPhoneNum())
				.imgUrl(userSignupDto.getImgUrl())
				.publicStatus(userSignupDto.getPublicStatus())
				.shareStatus(userSignupDto.getShareStatus())
				.build()
		).getId();
	}
}
