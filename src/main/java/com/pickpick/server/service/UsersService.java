package com.pickpick.server.service;

import static com.pickpick.server.config.SecurityConfig.passwordEncoder;

import com.pickpick.server.domain.Users;
import com.pickpick.server.dto.AddUserRequestDto;
import com.pickpick.server.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UsersService {
	private final UsersRepository usersRepository;

	public Long save(AddUserRequestDto addUserRequestDto) {
		return usersRepository.save(Users.builder()
				.name(addUserRequestDto.getName())
				.email(addUserRequestDto.getEmail())
				.password(passwordEncoder().encode(addUserRequestDto.getPassword()))
				.phoneNum(addUserRequestDto.getPhoneNum())
				.imgUrl(addUserRequestDto.getImgUrl())
				.publicStatus(addUserRequestDto.getPublicStatus())
				.shareStatus(addUserRequestDto.getShareStatus())
				.build()
		).getId();
	}
}
