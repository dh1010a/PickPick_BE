package com.pickpick.server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Album {

	@Id
	@GeneratedValue
	@Column(name = "album_id")
	private Long idx;

	private String name;

	private String titleImgUrl;

	@Enumerated(EnumType.STRING)
	private AlbumType type;

}
