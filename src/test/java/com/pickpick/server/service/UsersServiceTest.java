package com.pickpick.server.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.pickpick.server.domain.enums.PublicStatus;
import com.pickpick.server.domain.enums.ShareStatus;
import com.pickpick.server.dto.UserSignupDto;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class UsersServiceTest {

	@Autowired
	UsersService usersService;

	@Autowired
	EntityManager em;

	@BeforeEach
	private void init() {
		usersService.save(UserSignupDto.builder().name("도현").email("dh1010a@naver.com").password("1234").phoneNum("01054888")
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
		assertThat(usersService.isExistByEmail(email)).isTrue();
	}

}