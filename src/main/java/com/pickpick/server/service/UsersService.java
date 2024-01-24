package com.pickpick.server.service;

import com.pickpick.server.domain.Users;
import com.pickpick.server.dto.AddUserRequestDto;
import com.pickpick.server.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {
	private final UsersRepository usersRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public Long save(AddUserRequestDto addUserRequestDto) {
		return usersRepository.save(Users.builder()
				.name(addUserRequestDto.getName())
				.email(addUserRequestDto.getEmail())
				.password(bCryptPasswordEncoder.encode(addUserRequestDto.getPassword()))
				.phoneNum(addUserRequestDto.getPhoneNum())
				.imgUrl(addUserRequestDto.getImgUrl())
				.publicStatus(addUserRequestDto.getPublicStatus())
				.shareStatus(addUserRequestDto.getShareStatus())
				.build()
		).getId();
	}
}
