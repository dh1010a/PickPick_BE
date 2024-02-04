package com.pickpick.server.member.repository;

import com.pickpick.server.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByEmail(String email);

	boolean existsByEmail(String email);

	Optional<Member> findByPhoneNum(String phoneNum);

	boolean existsByPhoneNum(String phoneNum);

	Optional<Member> findByRefreshToken(String refreshToke);

}
