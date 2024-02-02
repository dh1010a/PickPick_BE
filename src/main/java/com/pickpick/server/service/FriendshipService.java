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

		// 현재 로그인 되어있는 사람 (보내는 사람)
		String fromEmail = SecurityUtil.getLoginEmail();

		// 유저 정보를 모두 가져옴
		Users fromUser = usersRepository.findByEmail(fromEmail).orElseThrow(() -> new Exception("회원 조회 실패"));
		Users toUser = usersRepository.findByEmail(toEmail).orElseThrow(() -> new Exception("회원 조회 실패"));

		// 받는 사람측에 저장될 친구 요청
		Friendship friendshipFrom = Friendship.builder()
				.users(fromUser)
				.userEmail(fromEmail)
				.friendEmail(toEmail)
				.status(FriendshipStatus.WAITING)
				.isFrom(true) // 받는 사람은 이게 보내는 요청인지 아닌지 판단할 수 있다. (어디서 부터 받은 요청 인가?)
				.build();

		// 보내는 사람 쪽에 저장될 친구 요청
		Friendship friendshipTo = Friendship.builder()
				.users(toUser)
				.userEmail(toEmail)
				.friendEmail(fromEmail)
				.status(FriendshipStatus.WAITING)
				.isFrom(false)
				.build();

		// 각각의 유저 리스트에 저장
		fromUser.getFriendshipList().add(friendshipTo);
		toUser.getFriendshipList().add(friendshipFrom);

		// 저장을 먼저 하는 이유는, 그래야 서로의 친구요청 번호가 생성되기 떄문이다.
		friendshipRepository.save(friendshipTo);
		friendshipRepository.save(friendshipFrom);

		// 매칭되는 친구요청의 아이디를 서로 저장한다.
		friendshipTo.setCounterpartId(friendshipFrom.getId());
		friendshipFrom.setCounterpartId(friendshipTo.getId());

	}

	// 받은 친구 요청 중, 수락 되지 않은 요청을 조회하는 메서드
	@Transactional
	public ResponseEntity<?> getWaitingFriendList() throws Exception {
		// 현재 로그인한 유저의 정보를 불러온다
		Users users = usersRepository.findByEmail(SecurityUtil.getLoginEmail()).orElseThrow(() -> new Exception("회원 조회 실패"));
		List<Friendship> friendshipList = users.getFriendshipList();
		// 조회된 결과 객체를 담을 Dto 리스트
		List<WaitingFriendListDto> result = new ArrayList<>();

		for (Friendship x : friendshipList) {
			// 보낸 요청이 아니고 && 수락 대기중인 요청만 조회
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
		// 결과 반환
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
		// 누를 친구 요청과 매칭되는 상대방 친구 요청 둘다 가져옴
		Friendship friendship = friendshipRepository.findById(friendshipId).orElseThrow(() -> new Exception("친구 요청 조회 실패"));
		Friendship counterFriendship = friendshipRepository.findById(friendship.getCounterpartId()).orElseThrow(() -> new Exception("친구 요청 조회 실패"));

		// 둘다 상태를 ACCEPT로 변경함
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
