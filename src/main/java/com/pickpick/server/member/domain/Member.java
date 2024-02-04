package com.pickpick.server.member.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pickpick.server.feed.domain.Feed;
import com.pickpick.server.global.common.BaseEntity;
import com.pickpick.server.photo.domain.Photo;
import com.pickpick.server.album.domain.SharedAlbum;
import com.pickpick.server.member.domain.enums.PublicStatus;
import com.pickpick.server.member.domain.enums.ShareStatus;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "Member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true, length = 30)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String phoneNum;

	private String imgUrl;

	@Enumerated(EnumType.STRING)
	@JsonIgnore
	private PublicStatus publicStatus;

	@JsonIgnore
	@Enumerated(EnumType.STRING)
	private ShareStatus shareStatus;


	//== 수정 로직 ==/

	public void updateName(String name) {
		this.name = name;
	}

	public void updateImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public void updatePublicStatus(PublicStatus status) {
		this.publicStatus = status;
	}

	public void updateShareStatus(ShareStatus status) {
		this.shareStatus = status;
	}

	public boolean matchPassword(PasswordEncoder passwordEncoder, String checkPassword) {
		return passwordEncoder.matches(checkPassword, getPassword());
	}

	//== jwt 토큰 추가 ==//
	@Column(length = 1000)
	private String refreshToken;

	public void updateRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public void destroyRefreshToken() {
		this.refreshToken = null;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "member")
	private List<SharedAlbum> sharedAlbums = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<Feed> feeds = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<Photo> photos = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<Friendship> friendshipList = new ArrayList<>();

	//== 패스워드 암호화 ==//
	public void encodePassword(PasswordEncoder passwordEncoder){
		this.password = passwordEncoder.encode(password);
	}

}
