package com.pickpick.server.repository;

import com.pickpick.server.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

	Optional<Users> findByEmail(String email);

	boolean existsByEmail(String email);

	Optional<Users> findByPhoneNum(String phoneNum);

	boolean existsByPhoneNum(String phoneNum);

	Optional<Users> findByRefreshToken(String refreshToke);

}
