package com.pickpick.server.controller;

import com.pickpick.server.domain.enums.PublicStatus;
import com.pickpick.server.domain.enums.ShareStatus;
import com.pickpick.server.dto.UserInfoDto;
import com.pickpick.server.dto.UserSignupDto;
import com.pickpick.server.service.UsersService;
import com.pickpick.server.util.SecurityUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class UserApiController {

	private final UsersService usersService;

	@PostMapping("/signup")
	@ResponseStatus(HttpStatus.OK)
	public String signUp(@Valid @RequestBody CreateMemberRequestDto request) throws IOException {
		System.out.println(" call controller");
		UserSignupDto userSignupDto = UserSignupDto.builder()
				.name(request.getName())
				.email(request.getEmail())
				.phoneNum(request.getPhoneNum())
				.password(request.getPassword())
				.imgUrl(request.getImgUrl())
				.publicStatus(PublicStatus.PUBLIC)
				.shareStatus(ShareStatus.NON_SHAREABLE)
				.build();
		if (request.getPublicStatus().equals("HIDDEN")) {
			userSignupDto.setPublicStatus(PublicStatus.HIDDEN);
		}
		if (request.getShareStatus().equals("NON_SHAREABLE")) {
			userSignupDto.setShareStatus(ShareStatus.SHAREABLE);
		}
		usersService.save(userSignupDto);
		return "회원가입 성공";
	}

	@PostMapping("/user/isDuplicated")
	@ResponseStatus(HttpStatus.OK)
	public boolean isDuplicated(@Valid @RequestBody EmailCheckRequestDto request) {
		if (usersService.isExistByEmail(request.getEmail())) {
			return true;
		}
		return false;
	}

	@GetMapping("/user/{email}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity getUserInfo(@Valid @PathVariable("email") String email) throws Exception{
		UserInfoDto userInfoDto = usersService.getUserInfo(email);
		return new ResponseEntity(userInfoDto, HttpStatus.OK);
	}

	@GetMapping("/user/myInfo")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity getMyInfo() throws Exception{
		UserInfoDto userInfoDto = usersService.getMyInfo();
		return new ResponseEntity(userInfoDto, HttpStatus.OK);
	}

	@DeleteMapping("/user")
	@ResponseStatus(HttpStatus.OK)
	public String deleteUser(@Valid @RequestBody userDeleteDto userDeleteDto) throws Exception {
		usersService.deleteUser(userDeleteDto.getPassword(), SecurityUtil.getLoginEmail());
		return "회원 탈퇴 성공";
	}


	@Data
	static class userDeleteDto {
		private String password;
	}

	@Data
	static class LoginRequestDto {
		@NotEmpty
		private String email;
		@NotEmpty
		private String password;
	}


	@Data
	static class EmailCheckRequestDto {
		@NotEmpty
		private String email;
	}

	@Data
	static class CreateMemberRequestDto {
		@NotEmpty
		private String name;
		@NotEmpty
		private String email;
		@NotEmpty
		private String password;
		@NotEmpty
		private String phoneNum;
		private String imgUrl;
		private String publicStatus;
		private String shareStatus;
	}
}
