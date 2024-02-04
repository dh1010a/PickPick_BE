package com.pickpick.server.controller;

import com.pickpick.server.apiPayload.ApiResponse;
import com.pickpick.server.apiPayload.code.status.ErrorStatus;
import com.pickpick.server.apiPayload.exception.handler.UserHandler;
import com.pickpick.server.converter.UserDtoConverter;
import com.pickpick.server.dto.UserInfoDto;
import com.pickpick.server.dto.UserRequestDto;
import com.pickpick.server.service.FriendshipService;
import com.pickpick.server.service.UsersService;
import com.pickpick.server.util.SecurityUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class UserApiController {

	private final UsersService usersService;
	private final FriendshipService friendshipService;

	@PostMapping("/signup")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<String> signUp(@Valid @RequestBody UserRequestDto.CreateUserRequestDto request) throws Exception {
		UserRequestDto.UserSignupDto userSignupDto = UserDtoConverter.convertToUserSignupDto(request);
		usersService.save(userSignupDto);
		return ApiResponse.onSuccess("가입 email: " + userSignupDto.getEmail());
	}

	@PostMapping(value = "/user/img")
	public ApiResponse<String> uploadImg(ImgDto imgDto) throws Exception{
		usersService.uploadImg(imgDto.getImgUrl());
		return ApiResponse.onSuccess("업로드 성공");
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
	public ApiResponse<UserInfoDto> getUserInfo(@Valid @PathVariable("email") String email) {
		UserInfoDto userInfoDto = usersService.getUserInfo(email);
		return ApiResponse.onSuccess(userInfoDto);
	}

	@GetMapping("/user/myInfo")
	public ApiResponse<UserInfoDto> getMyInfo() {
		UserInfoDto userInfoDto = usersService.getMyInfo();
		return ApiResponse.onSuccess(userInfoDto);
	}

	@PostMapping("/user/update")
	public ApiResponse<String> updateUserInfo(@Valid @RequestBody UserRequestDto.UpdateUserRequestDto userRequestDto) {
		usersService.updateUserInfo(userRequestDto);
		return ApiResponse.onSuccess("회원정보 수정에 성공하였습니다.");
	}

	@DeleteMapping("/user")
	public ApiResponse<String> deleteUser(@Valid @RequestBody userDeleteDto userDeleteDto) throws Exception {
		usersService.deleteUser(userDeleteDto.getPassword(), SecurityUtil.getLoginEmail());
		return ApiResponse.onSuccess("회원 탈퇴에 성공하였습니다.");
	}

	@PostMapping("/user/friends/{email}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<String> sendFriendshipRequest(@Valid @PathVariable("email") String email) throws Exception {
		if(!usersService.isExistByEmail(email)) {
			throw new UserHandler(ErrorStatus.MEMBER_NOT_FOUND);
		}
		friendshipService.createFriendship(email);
		return ApiResponse.onSuccess(email + " 회원에게 친구 요청 전송 성공하였습니다.");
	}

	@GetMapping("/user/friends/received")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getWaitingFriendInfo() throws Exception {
		return friendshipService.getWaitingFriendList();
	}

	@GetMapping("/user/friends/sending/{email}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getSendingFriendInfo(@Valid @PathVariable("email") String email) throws Exception {
		return friendshipService.getSendingFriendList(email);
	}

	@PostMapping("/user/friends/approve/{friendshipId}")
	@ResponseStatus(HttpStatus.OK)
	public String approveFriendship (@Valid @PathVariable("friendshipId") Long friendshipId) throws Exception{
		return friendshipService.approveFriendshipRequest(friendshipId);
	}


	@Data
	static class userDeleteDto {
		private String password;
	}

	@Data
	static class ImgDto {
		private MultipartFile imgUrl;
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

}
