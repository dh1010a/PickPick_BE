package com.pickpick.server.member.service;

import static com.pickpick.server.global.config.SecurityConfig.passwordEncoder;

import com.pickpick.server.global.apiPayload.ApiResponse;
import com.pickpick.server.global.apiPayload.code.status.ErrorStatus;
import com.pickpick.server.global.apiPayload.exception.handler.MemberHandler;
import com.pickpick.server.global.util.SecurityUtil;
import com.pickpick.server.member.domain.Member;
import com.pickpick.server.member.domain.enums.PublicStatus;
import com.pickpick.server.member.domain.enums.ShareStatus;
import com.pickpick.server.member.domain.enums.Status;
import com.pickpick.server.member.dto.MemberDto;
import com.pickpick.server.global.file.FileService;
import com.pickpick.server.global.file.exception.FileException;
import com.pickpick.server.member.dto.MemberRequestDto;
import com.pickpick.server.member.dto.MemberRequestDto.MemberSignupDto;
import com.pickpick.server.member.dto.MemberRequestDto.UpdateMemberRequestDto;
import com.pickpick.server.member.dto.MemberResponseDto.IsDuplicateDTO;
import com.pickpick.server.member.repository.MemberRepository;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final FileService fileService;

	private static final String USER_PROFILE_IMG_PATH = "\\profileImg\\";

	public Long save(MemberSignupDto userRequestDto) throws Exception, FileException {

		Member member = Member.builder()
				.name(userRequestDto.getName())
				.email(userRequestDto.getEmail())
				.password(passwordEncoder().encode(userRequestDto.getPassword()))
				.phoneNum(userRequestDto.getPhoneNum())
//				.imgUrl(userRequestDto.getImgUrl())
				.publicStatus(userRequestDto.getPublicStatus())
				.shareStatus(userRequestDto.getShareStatus())
				.build();

//		userRequestDto.getUploadImg().ifPresent(file -> users.updateImgUrl(fileService.save(file)));

		Long id = memberRepository.save(member).getId();


		return id;
	}

	public Long delete(MemberRequestDto.DeleteDTO request){
		Member member = memberRepository.findById(request.getId()).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));;

		member.setStatus(Status.DELETE);
		return memberRepository.save(member).getId();
	}
	public String isDuplicated(MemberRequestDto.EmailCheckRequestDto request){
		if (isExistByEmail(request.getEmail())) {
			return "true";
		}
		return "false";
	}

	public boolean isExistByEmail(String email) {
		return memberRepository.existsByEmail(email);
	}

	public MemberDto getMemberInfo(String email) {
		Member member = memberRepository.findByEmail(email).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
		return MemberDto.builder()
				.name(member.getName())
				.email(member.getEmail())
				.imgUrl(member.getImgUrl())
				.phoneNum(member.getPhoneNum())
				.build();
	}

	public MemberDto getMyInfo() {
		Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail()).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
		return MemberDto.builder()
				.name(member.getName())
				.email(member.getEmail())
				.imgUrl(member.getImgUrl())
				.phoneNum(member.getPhoneNum())
				.build();
	}

	public void uploadImg(MultipartFile imgUrl) {
		Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail()).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
		member.updateImgUrl(fileService.saveProfileImg(imgUrl));
	}

	public void updateMemberInfo(UpdateMemberRequestDto dto) {
		Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail()).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
		member.updateName(dto.getName());
		member.updateImgUrl(dto.getImgUrl());

		if (dto.getPublicStatus().equals(PublicStatus.PUBLIC.toString())) {
			member.updatePublicStatus(PublicStatus.HIDDEN);
		} else {
			member.updatePublicStatus(PublicStatus.PUBLIC);
		}

		if (dto.getShareStatus().equals(ShareStatus.SHAREABLE.toString())) {
			member.updateShareStatus(ShareStatus.NON_SHAREABLE);
		} else {
			member.updateShareStatus(ShareStatus.SHAREABLE);
		}
	}

	public boolean isMemberShareable(String email) {
		Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail()).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
		return member.getShareStatus().equals(ShareStatus.SHAREABLE);
	}

	public boolean isMemberPublic(String email) {
		Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail()).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
		return member.getPublicStatus().equals(PublicStatus.PUBLIC);
	}

	public void deleteMember(String checkPassword, String email) {
		Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail()).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
		if(!member.matchPassword(passwordEncoder, checkPassword) ) {
			throw new MemberHandler(ErrorStatus.MEMBER_PASSWORD_NOT_MATCH);
		}
		memberRepository.delete(member);
	}
}
