package com.pickpick.server.controller;

import com.pickpick.server.domain.enums.PublicStatus;
import com.pickpick.server.domain.enums.ShareStatus;
import com.pickpick.server.dto.AddUserRequestDto;
import com.pickpick.server.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class UserApiController {

	private final UsersService usersService;

	@PostMapping("/user/signup")
	public String signUp(@RequestParam("username") String name,
						 @RequestParam("email") String email,
						 @RequestParam("password") String password,
						 @RequestParam("phoneNum") String phoneNum,
						 @RequestParam("publicStatus") String publicStatus,
						 @RequestParam("shareStatus") String shareStatus,
						 @RequestParam(value = "imgUrl", required = false) String imgUrl
	) throws IOException {
		System.out.println(" call controller");
		AddUserRequestDto addUserRequestDto = AddUserRequestDto.builder()
				.name(name)
				.email(email)
				.phoneNum(phoneNum)
				.password(password)
				.publicStatus(PublicStatus.PUBLIC)
				.shareStatus(ShareStatus.SHAREABLE)
				.imgUrl(imgUrl)
				.build();
		if (publicStatus.equals("HIDDEN")) {
			addUserRequestDto.setPublicStatus(PublicStatus.HIDDEN);
		}
		if (shareStatus.equals("NON_SHAREABLE")) {
			addUserRequestDto.setShareStatus(ShareStatus.NON_SHAREABLE);
		}
		usersService.save(addUserRequestDto);
		return "회원가입 성공";
	}
}
