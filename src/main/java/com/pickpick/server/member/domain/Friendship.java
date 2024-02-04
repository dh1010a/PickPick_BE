package com.pickpick.server.member.domain;

import com.pickpick.server.global.common.BaseEntity;
import com.pickpick.server.member.domain.enums.FriendshipStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//양쪽 유저에 저장. 두개로.
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friendship extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	private String memberEmail;
	private String friendEmail;
	private FriendshipStatus status;
	private boolean isFrom;

	private Long counterpartId;

	public void acceptFriendshipRequest() {
		status = FriendshipStatus.ACCEPT;
	}

	public void setCounterpartId(Long id) {
		counterpartId = id;
	}

}
