package com.pickpick.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pickpick.server.domain.enums.AlbumType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Album {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "album_id")
	private Long id;

	private String name;

	private String titleImgUrl;

	@OneToMany(mappedBy = "album")
	@JsonIgnore
	private List<SharedAlbum> sharedAlbums = new ArrayList<>();

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private AlbumType type;

	@OneToMany(mappedBy = "album")
	private List<Feed> feed = new ArrayList<>();

}
