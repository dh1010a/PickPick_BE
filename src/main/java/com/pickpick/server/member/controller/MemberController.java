package com.pickpick.server.member.controller;

import com.pickpick.server.global.apiPayload.ApiResponse;
import com.pickpick.server.global.apiPayload.code.status.ErrorStatus;
import com.pickpick.server.global.apiPayload.exception.handler.MemberHandler;
import com.pickpick.server.global.converter.MemberDtoConverter;
import com.pickpick.server.member.dto.MemberDto;
import com.pickpick.server.member.dto.MemberRequestDto;
import com.pickpick.server.member.dto.MemberRequestDto.MemberSignupDto;
import com.pickpick.server.member.service.FriendshipService;
import com.pickpick.server.member.service.MemberService;
import com.pickpick.server.global.util.SecurityUtil;
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

import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class MemberController {

	private final MemberService memberService;
	private final FriendshipService friendshipService;

	@PostMapping("/signup")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<String> signUp(@Valid @RequestBody MemberRequestDto.CreateMemberRequestDto request) throws Exception {
		MemberSignupDto memberSignupDto = MemberDtoConverter.convertToUserSignupDto(request);
		memberService.save(memberSignupDto);
		return ApiResponse.onSuccess("가입 email: " + memberSignupDto.getEmail());
	}

	@PostMapping(value = "/member/img")
	public ApiResponse<String> uploadImg(ImgDto imgDto) throws Exception{
		memberService.uploadImg(imgDto.getImgUrl());
		return ApiResponse.onSuccess("업로드 성공");
	}

	@PostMapping("/member/isDuplicated")
	@ResponseStatus(HttpStatus.OK)
	public boolean isDuplicated(@Valid @RequestBody EmailCheckRequestDto request) {
		if (memberService.isExistByEmail(request.getEmail())) {
			return true;
		}
		return false;
	}

	@GetMapping("/member/{email}")
	public ApiResponse<MemberDto> getUserInfo(@Valid @PathVariable("email") String email) {
		MemberDto memberDto = memberService.getMemberInfo(email);
		return ApiResponse.onSuccess(memberDto);
	}

	@GetMapping("/member/myInfo")
	public ApiResponse<MemberDto> getMyInfo() {
		MemberDto memberDto = memberService.getMyInfo();
		return ApiResponse.onSuccess(memberDto);
	}

	@PostMapping("/member/update")
	public ApiResponse<String> updateUserInfo(@Valid @RequestBody MemberRequestDto.UpdateMemberRequestDto userRequestDto) {
		memberService.updateMemberInfo(userRequestDto);
		return ApiResponse.onSuccess("회원정보 수정에 성공하였습니다.");
	}

	@DeleteMapping("/member")
	public ApiResponse<String> deleteUser(@Valid @RequestBody userDeleteDto userDeleteDto) throws Exception {
		memberService.deleteMember(userDeleteDto.getPassword(), SecurityUtil.getLoginEmail());
		return ApiResponse.onSuccess("회원 탈퇴에 성공하였습니다.");
	}

	@PostMapping("/member/friends/{email}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<String> sendFriendshipRequest(@Valid @PathVariable("email") String email) throws Exception {
		if(!memberService.isExistByEmail(email)) {
			throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND);
		}
		friendshipService.createFriendship(email);
		return ApiResponse.onSuccess(email + " 회원에게 친구 요청 전송 성공하였습니다.");
	}

	@GetMapping("/member/friends/received")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getWaitingFriendInfo() throws Exception {
		return friendshipService.getWaitingFriendList();
	}

	@GetMapping("/member/friends/sending/{email}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getSendingFriendInfo(@Valid @PathVariable("email") String email) throws Exception {
		return friendshipService.getSendingFriendList(email);
	}

	@PostMapping("/member/friends/approve/{friendshipId}")
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
