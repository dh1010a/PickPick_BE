package com.pickpick.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pickpick.server.domain.enums.ShareStatus;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

@Entity
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Album {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "album_id")
	private Long id;

	@Column(nullable = false, length = 15)
	private String name;

	private String titleImgUrl;

	private LocalDate createdAt;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ShareStatus shareStatus;

	@OneToMany(mappedBy = "album")
	@JsonIgnore
	private List<SharedAlbum> sharedAlbums = new ArrayList<>();


	@OneToMany(mappedBy = "album")
	private List<Feed> feed = new ArrayList<>();

}
