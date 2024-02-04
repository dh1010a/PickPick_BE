package com.pickpick.server.member.repository;

import com.pickpick.server.member.domain.Friendship;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

	Optional<Friendship> findByCounterpartId(Long id);

}
