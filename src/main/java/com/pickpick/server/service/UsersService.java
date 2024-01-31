package com.pickpick.server.service;

import static com.pickpick.server.config.SecurityConfig.passwordEncoder;

import com.pickpick.server.domain.Users;
import com.pickpick.server.domain.enums.PublicStatus;
import com.pickpick.server.domain.enums.ShareStatus;
import com.pickpick.server.dto.UserInfoDto;
import com.pickpick.server.dto.UserSignupDto;
import com.pickpick.server.repository.UsersRepository;
import com.pickpick.server.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UsersService {

	private final UsersRepository usersRepository;
	private final PasswordEncoder passwordEncoder;

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

	public boolean isExistByEmail(String email) {
		return usersRepository.existsByEmail(email);
	}

	public UserInfoDto getUserInfo(String email) throws Exception {
		Users users = usersRepository.findByEmail(email).orElseThrow(() -> new Exception("존재하지 않는 회원입니다"));
		return UserInfoDto.builder()
				.name(users.getName())
				.email(users.getEmail())
				.imgUrl(users.getImgUrl())
				.phoneNum(users.getPhoneNum())
				.build();
	}

	public UserInfoDto getMyInfo() throws Exception {
		Users users = usersRepository.findByEmail(SecurityUtil.getLoginEmail()).orElseThrow(() -> new Exception("존재하지 않는 회원입니다"));
		return UserInfoDto.builder()
				.name(users.getName())
				.email(users.getEmail())
				.imgUrl(users.getImgUrl())
				.phoneNum(users.getPhoneNum())
				.build();
	}

	public boolean isUserShareable(String email) throws Exception {
		Users users = usersRepository.findByEmail(SecurityUtil.getLoginEmail()).orElseThrow(() -> new Exception("존재하지 않는 회원입니다"));
		return users.getShareStatus().equals(ShareStatus.SHAREABLE);
	}

	public boolean isUserPublic(String email) throws Exception {
		Users users = usersRepository.findByEmail(SecurityUtil.getLoginEmail()).orElseThrow(() -> new Exception("존재하지 않는 회원입니다"));
		return users.getPublicStatus().equals(PublicStatus.PUBLIC);
	}

	public void deleteUser(String checkPassword, String email) throws Exception{
		Users users = usersRepository.findByEmail(SecurityUtil.getLoginEmail()).orElseThrow(() -> new Exception("존재하지 않는 회원입니다"));
		if(!users.matchPassword(passwordEncoder, checkPassword) ) {
			throw new Exception("비밀번호가 일치하지 않습니다");
		}
		usersRepository.delete(users);
	}
}
