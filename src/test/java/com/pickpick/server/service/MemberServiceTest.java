package com.pickpick.server.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.pickpick.server.member.domain.enums.PublicStatus;
import com.pickpick.server.member.domain.enums.ShareStatus;
import com.pickpick.server.member.dto.MemberRequestDto;
import com.pickpick.server.member.service.MemberService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class MemberServiceTest {

	@Autowired
	MemberService memberService;

	@Autowired
	EntityManager em;

	@BeforeEach
	private void init() {
		memberService.save(MemberRequestDto.builder().name("도현").email("dh1010a@naver.com").password("1234").phoneNum("01054888")
				.publicStatus(PublicStatus.PUBLIC).shareStatus(ShareStatus.SHAREABLE).build());
		clear();
	}

	private void clear() {
		em.flush();
		em.clear();
	}

	@Test
	public void 중복체크_성공() throws Exception{
		String email = "dh1010a@naver.com";
		assertThat(memberService.isExistByEmail(email)).isTrue();
	}

}