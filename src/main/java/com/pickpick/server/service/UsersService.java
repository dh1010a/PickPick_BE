package com.pickpick.server.service;

import static com.pickpick.server.config.SecurityConfig.passwordEncoder;

import com.pickpick.server.apiPayload.code.status.ErrorStatus;
import com.pickpick.server.apiPayload.exception.handler.UserHandler;
import com.pickpick.server.domain.Users;
import com.pickpick.server.domain.enums.PublicStatus;
import com.pickpick.server.domain.enums.ShareStatus;
import com.pickpick.server.dto.UserInfoDto;
import com.pickpick.server.dto.UserRequestDto;
import com.pickpick.server.global.file.FileService;
import com.pickpick.server.global.file.exception.FileException;
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
	private final FileService fileService;

	public Long save(UserRequestDto.UserSignupDto userRequestDto) throws Exception, FileException {

		Users users = Users.builder()
				.name(userRequestDto.getName())
				.email(userRequestDto.getEmail())
				.password(passwordEncoder().encode(userRequestDto.getPassword()))
				.phoneNum(userRequestDto.getPhoneNum())
//				.imgUrl(userRequestDto.getImgUrl())
				.publicStatus(userRequestDto.getPublicStatus())
				.shareStatus(userRequestDto.getShareStatus())
				.build();

		userRequestDto.getUploadImg().ifPresent(file -> users.updateImgUrl(fileService.save(file)));

		Long id = usersRepository.save(users).getId();


		return id;
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

	public void updateUserInfo(UserRequestDto.UpdateUserRequestDto dto) {
		Users users = usersRepository.findByEmail(SecurityUtil.getLoginEmail()).orElseThrow(() -> new UserHandler(ErrorStatus.MEMBER_NOT_FOUND));
		users.updateName(dto.getName());
		users.updateImgUrl(dto.getImgUrl());

		if (dto.getPublicStatus().equals(PublicStatus.PUBLIC.toString())) {
			users.updatePublicStatus(PublicStatus.HIDDEN);
		} else {
			users.updatePublicStatus(PublicStatus.PUBLIC);
		}

		if (dto.getShareStatus().equals(ShareStatus.SHAREABLE.toString())) {
			users.updateShareStatus(ShareStatus.NON_SHAREABLE);
		} else {
			users.updateShareStatus(ShareStatus.SHAREABLE);
		}
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
