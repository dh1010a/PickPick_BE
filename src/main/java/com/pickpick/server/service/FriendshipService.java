package com.pickpick.server.service;

import com.pickpick.server.domain.Friendship;
import com.pickpick.server.domain.Users;
import com.pickpick.server.domain.enums.FriendshipStatus;
import com.pickpick.server.repository.FriendshipRepository;
import com.pickpick.server.repository.UsersRepository;
import com.pickpick.server.util.SecurityUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendshipService {

	private final FriendshipRepository friendshipRepository;
	private final UsersRepository usersRepository;


	public void createFriendship(String toEmail) throws Exception{

		String fromEmail = SecurityUtil.getLoginEmail();

		Users fromUser = usersRepository.findByEmail(fromEmail).orElseThrow(() -> new Exception("회원 조회 실패"));
		Users toUser = usersRepository.findByEmail(toEmail).orElseThrow(() -> new Exception("회원 조회 실패"));

		Friendship friendshipFrom = Friendship.builder()
				.users(fromUser)
				.userEmail(fromEmail)
				.friendEmail(toEmail)
				.status(FriendshipStatus.WAITING)
				.isFrom(true)
				.build();
		Friendship friendshipTo = Friendship.builder()
				.users(toUser)
				.userEmail(toEmail)
				.friendEmail(fromEmail)
				.status(FriendshipStatus.WAITING)
				.isFrom(false)
				.build();

		fromUser.getFriendshipList().add(friendshipTo);
		toUser.getFriendshipList().add(friendshipFrom);

		friendshipRepository.save(friendshipTo);
		friendshipRepository.save(friendshipFrom);

		friendshipTo.setCounterpartId(friendshipFrom.getId());
		friendshipFrom.setCounterpartId(friendshipTo.getId());

	}

	@Transactional
	public ResponseEntity<?> getWaitingFriendList(String email) throws Exception {
		Users users = usersRepository.findByEmail(email).orElseThrow(() -> new Exception("회원 조회 실패"));
		List<Friendship> friendshipList = users.getFriendshipList();
		List<WaitingFriendListDto> result = new ArrayList<>();

		for (Friendship x : friendshipList) {
			if (!x.isFrom() && x.getStatus() == FriendshipStatus.WAITING) {
				Users friend = usersRepository.findByEmail(x.getFriendEmail()).orElseThrow(() -> new Exception("회원 조회 실패"));
				WaitingFriendListDto dto = WaitingFriendListDto.builder()
						.friendshipId(x.getId())
						.friendEmail(friend.getEmail())
						.friendName(friend.getName())
						.status(x.getStatus())
						.imgUrl(friend.getImgUrl())
						.build();
				result.add(dto);
			}
		}
		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	public ResponseEntity<?> getSendingFriendList(String email) throws Exception{
		Users users = usersRepository.findByEmail(email).orElseThrow(() -> new Exception("회원 조회 실패"));
		List<Friendship> friendshipList = users.getFriendshipList();
		List<WaitingFriendListDto> result = new ArrayList<>();

		for (Friendship x : friendshipList) {
			if (x.isFrom()) {
				Users friend = usersRepository.findByEmail(x.getFriendEmail()).orElseThrow(() -> new Exception("회원 조회 실패"));
				WaitingFriendListDto dto = WaitingFriendListDto.builder()
						.friendshipId(x.getId())
						.friendEmail(friend.getEmail())
						.friendName(friend.getName())
						.status(x.getStatus())
						.imgUrl(friend.getImgUrl())
						.build();
				result.add(dto);
			}
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	public String approveFriendshipRequest(Long friendshipId) throws Exception {
		Friendship friendship = friendshipRepository.findById(friendshipId).orElseThrow(() -> new Exception("친구 요청 조회 실패"));
		Friendship counterFriendship = friendshipRepository.findById(friendship.getCounterpartId()).orElseThrow(() -> new Exception("친구 요청 조회 실패"));
		friendship.acceptFriendshipRequest();
		counterFriendship.acceptFriendshipRequest();

		return "승인 성공";
	}

	@Data
	@Builder
	static class WaitingFriendListDto {
		private Long friendshipId;
		private String friendEmail;
		private String friendName;
		private FriendshipStatus status;
		private String imgUrl;
	}
}
