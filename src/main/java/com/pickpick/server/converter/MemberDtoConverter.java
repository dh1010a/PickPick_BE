package com.pickpick.server.converter;


import com.pickpick.server.member.domain.enums.PublicStatus;
import com.pickpick.server.member.domain.enums.ShareStatus;
import com.pickpick.server.member.dto.MemberRequestDto;

public class MemberDtoConverter {

	public static MemberRequestDto.UserSignupDto convertToUserSignupDto(MemberRequestDto.CreateUserRequestDto request) {
		MemberRequestDto.UserSignupDto userSignupDto = MemberRequestDto.UserSignupDto.builder()
				.name(request.getName())
				.email(request.getEmail())
				.phoneNum(request.getPhoneNum())
				.password(request.getPassword())
//				.imgUrl(request.getImgUrl())
				.uploadImg(request.getUploadImg())
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
