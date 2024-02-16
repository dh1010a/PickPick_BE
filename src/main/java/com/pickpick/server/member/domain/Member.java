package com.pickpick.server.member.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pickpick.server.feed.domain.Feed;
import com.pickpick.server.global.common.BaseTimeEntity;
import com.pickpick.server.member.domain.enums.Status;
import com.pickpick.server.photo.domain.Photo;
import com.pickpick.server.album.domain.SharedAlbum;
import com.pickpick.server.member.domain.enums.PublicStatus;
import com.pickpick.server.member.domain.enums.ShareStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "Member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

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


	@Enumerated(EnumType.STRING)
	@ColumnDefault("ACTIVE")
	private Status status;

	private LocalDateTime deleteAt;

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

	public boolean isPublic() {
		return publicStatus.equals(PublicStatus.PUBLIC);
	}

	public boolean isShareAble() {
		return shareStatus.equals(ShareStatus.NON_SHAREABLE);
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
	@JsonIgnore
	private List<Feed> feeds = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	@JsonIgnore
	private List<Photo> photos = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	@JsonIgnore
	private List<Friendship> friendshipList = new ArrayList<>();

	//== 패스워드 암호화 ==//
	public void encodePassword(PasswordEncoder passwordEncoder){
		this.password = passwordEncoder.encode(password);
	}

}
