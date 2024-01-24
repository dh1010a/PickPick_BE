package com.pickpick.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
	@GeneratedValue
	@Column(name = "album_id")
	private Long idx;

	private String name;

	private String titleImgUrl;

	@OneToMany(mappedBy = "album")
	@JsonIgnore
	private List<SharedAlbum> sharedAlbums = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	private AlbumType type;

}
