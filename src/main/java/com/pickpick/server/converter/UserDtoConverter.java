package com.pickpick.server.converter;


import com.pickpick.server.domain.enums.PublicStatus;
import com.pickpick.server.domain.enums.ShareStatus;
import com.pickpick.server.dto.UserRequestDto;

public class UserDtoConverter {

	public static UserRequestDto.UserSignupDto convertToUserSignupDto(UserRequestDto.CreateUserRequestDto request) {
		UserRequestDto.UserSignupDto userSignupDto = UserRequestDto.UserSignupDto.builder()
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
		return userSignupDto;
	}
}
