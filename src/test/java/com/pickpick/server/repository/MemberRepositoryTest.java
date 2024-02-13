package com.pickpick.server.repository;

import com.pickpick.server.member.domain.enums.PublicStatus;
import com.pickpick.server.member.domain.enums.ShareStatus;
import com.pickpick.server.member.domain.Member;
import com.pickpick.server.member.repository.MemberRepository;
import jakarta.persistence.EntityManager;
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
class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;
	@Autowired
	EntityManager em;

	@AfterEach
	private void after() {
		em.clear();
	}

	private void clear(){
		em.flush();
		em.clear();
	}

	@Test
	public void 회원저장_성공() throws Exception{
		//given
		Member user = Member.builder().name("도현").email("dh1010a@naver.com").password("1234").phoneNum("01054888")
				.publicStatus(PublicStatus.PUBLIC).shareStatus(ShareStatus.SHAREABLE).build();
		//when
		Member saveUser = memberRepository.save(user);

		//then
		Member findUser = memberRepository.findByEmail(saveUser.getEmail()).orElseThrow(() -> new RuntimeException("조회 안됨"));

		assertThat(user).isSameAs(findUser);
		assertThat(user).isSameAs(saveUser);
	}

	@Test
	public void 이메일없이_가입시_오류() throws Exception{
		//given
		Member user = Member.builder()
				.publicStatus(PublicStatus.PUBLIC).shareStatus(ShareStatus.SHAREABLE).build();
		//then
		assertThrows(Exception.class, () -> memberRepository.save(user));
	}

	@Test
	public void 비밀번호없이_가입시_오류() throws Exception{
		//given
		Member user = Member.builder().name("도현").email("dh1010a@naver.com").phoneNum("01054888")
				.publicStatus(PublicStatus.PUBLIC).shareStatus(ShareStatus.SHAREABLE).build();
		//then
		assertThrows(Exception.class, () -> memberRepository.save(user));
	}

	@Test
	public void 회원가입시_이메일중복_오류() throws Exception{
		//given
		Member user = Member.builder().name("도현").email("dh1010a@naver.com").password("1234").phoneNum("01054888")
				.publicStatus(PublicStatus.PUBLIC).shareStatus(ShareStatus.SHAREABLE).build();
		Member user2 = Member.builder().name("현수").email("dh1010a@naver.com").password("0000").phoneNum("01054888")
				.publicStatus(PublicStatus.PUBLIC).shareStatus(ShareStatus.SHAREABLE).build();


		// when, then
		memberRepository.save(user);
		clear();

		assertThrows(Exception.class, () -> memberRepository.save(user2));
	}

	@Test
	public void 회원삭제_성공() throws Exception{
		//given
		Member user = Member.builder().name("도현").email("dh1010a@naver.com").password("1234").phoneNum("01054888")
				.publicStatus(PublicStatus.PUBLIC).shareStatus(ShareStatus.SHAREABLE).build();
		memberRepository.save(user);
		clear();
		//when
		memberRepository.delete(user);
		clear();
		//then
		assertThrows(Exception.class, () -> memberRepository.findByEmail(user.getEmail()).orElseThrow(() -> new Exception()));
	}

	@Test
	public void existByPhoneNum_성공() throws Exception {
		//given
		String phoneNum = "01054888620";
		Member user = Member.builder().name("도현").email("dh1010a@naver.com").password("1234").phoneNum(phoneNum)
				.publicStatus(PublicStatus.PUBLIC).shareStatus(ShareStatus.SHAREABLE).build();
		memberRepository.save(user);
		clear();

		//when,then
		assertThat(memberRepository.existsByPhoneNum(phoneNum)).isTrue();

	}

	@Test
	public void findByPhoneNum_성공() throws Exception{
		//given
		String phoneNum = "01054888620";
		Member user = Member.builder().name("도현").email("dh1010a@naver.com").password("1234").phoneNum(phoneNum)
				.publicStatus(PublicStatus.PUBLIC).shareStatus(ShareStatus.SHAREABLE).build();
		memberRepository.save(user);
		clear();
		//when, then
		assertThat(memberRepository.findByPhoneNum(phoneNum).get().getName()).isSameAs(user.getName());
		assertThat(memberRepository.findByPhoneNum(phoneNum).get().getEmail()).isSameAs(user.getEmail());

	}
}