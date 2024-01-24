package com.pickpick.server.Repository;

import com.pickpick.server.domain.PublicStatus;
import com.pickpick.server.domain.ShareStatus;
import com.pickpick.server.domain.Users;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UsersRepositoryTest {

	@Autowired
	UsersRepository usersRepository;
	@Autowired
	EntityManager em;

	@AfterEach
	private void after() {
		em.clear();
	}

	@Test
	public void 회원저장_성공() throws Exception{
	    //given
		Users user = Users.builder().name("도현").email("dh1010a@naver.com").password("1234").phoneNum("01054888620").createdAt(LocalDate.now())
				.publicStatus(PublicStatus.PUBLIC).shareStatus(ShareStatus.SHAREABLE).build();
	    //when
		Users saveUser = usersRepository.save(user);

	    //then
		Users findUser = usersRepository.findByEmail(saveUser.getEmail()).orElseThrow(() -> new RuntimeException("조회 안됨"));

		assertThat(user).isSameAs(findUser);
		assertThat(user).isSameAs(saveUser);
	}
}