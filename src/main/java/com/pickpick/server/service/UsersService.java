package com.pickpick.server.service;

import static com.pickpick.server.config.SecurityConfig.passwordEncoder;

import com.pickpick.server.apiPayload.code.status.ErrorStatus;
import com.pickpick.server.apiPayload.exception.handler.UserHandler;
import com.pickpick.server.domain.Users;
import com.pickpick.server.domain.enums.PublicStatus;
import com.pickpick.server.domain.enums.ShareStatus;
import com.pickpick.server.dto.UserInfoDto;
import com.pickpick.server.dto.UserRequestDto;
import com.pickpick.server.repository.UsersRepository;
import com.pickpick.server.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UsersService {

	private final UsersRepository usersRepository;
	private final PasswordEncoder passwordEncoder;

	public Long save(UserRequestDto.UserSignupDto userRequestDto) {
		return usersRepository.save(Users.builder()
				.name(userRequestDto.getName())
				.email(userRequestDto.getEmail())
				.password(passwordEncoder().encode(userRequestDto.getPassword()))
				.phoneNum(userRequestDto.getPhoneNum())
				.imgUrl(userRequestDto.getImgUrl())
				.publicStatus(userRequestDto.getPublicStatus())
				.shareStatus(userRequestDto.getShareStatus())
				.build()
		).getId();
	}

	public boolean isExistByEmail(String email) {
		return usersRepository.existsByEmail(email);
	}

	public UserInfoDto getUserInfo(String email) {
		Users users = usersRepository.findByEmail(email).orElseThrow(() -> new UserHandler(ErrorStatus.MEMBER_NOT_FOUND));
		return UserInfoDto.builder()
				.name(users.getName())
				.email(users.getEmail())
				.imgUrl(users.getImgUrl())
				.phoneNum(users.getPhoneNum())
				.build();
	}

	public UserInfoDto getMyInfo() {
		Users users = usersRepository.findByEmail(SecurityUtil.getLoginEmail()).orElseThrow(() -> new UserHandler(ErrorStatus.MEMBER_NOT_FOUND));
		return UserInfoDto.builder()
				.name(users.getName())
				.email(users.getEmail())
				.imgUrl(users.getImgUrl())
				.phoneNum(users.getPhoneNum())
				.build();
	}

	public boolean isUserShareable(String email) {
		Users users = usersRepository.findByEmail(SecurityUtil.getLoginEmail()).orElseThrow(() -> new UserHandler(ErrorStatus.MEMBER_NOT_FOUND));
		return users.getShareStatus().equals(ShareStatus.SHAREABLE);
	}

	public boolean isUserPublic(String email) {
		Users users = usersRepository.findByEmail(SecurityUtil.getLoginEmail()).orElseThrow(() -> new UserHandler(ErrorStatus.MEMBER_NOT_FOUND));
		return users.getPublicStatus().equals(PublicStatus.PUBLIC);
	}

	public void deleteUser(String checkPassword, String email) {
		Users users = usersRepository.findByEmail(SecurityUtil.getLoginEmail()).orElseThrow(() -> new UserHandler(ErrorStatus.MEMBER_NOT_FOUND));
		if(!users.matchPassword(passwordEncoder, checkPassword) ) {
			throw new UserHandler(ErrorStatus.MEMBER_PASSWORD_NOT_MATCH);
		}
		usersRepository.delete(users);
	}
}
