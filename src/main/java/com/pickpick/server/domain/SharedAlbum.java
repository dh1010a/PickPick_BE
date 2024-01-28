package com.pickpick.server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SharedAlbum {

	@Id
	@GeneratedValue
	@Column(name = "sharedAlbum_id")
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private Users user;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "album_id")
	private Album album;

	public SharedAlbum(Users user, Album album) {
		this.user = user;
		this.album = album;
	}
}
