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
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Album {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "album_id")
	private Long id;

	@Column(nullable = false, length = 15)
	private String name;

	private String titleImgUrl;

	@Column(nullable = false)
	private LocalDate createdAt;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ShareStatus shareStatus;

	@OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<SharedAlbum> sharedAlbums = new ArrayList<>();


	@OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
	private List<Feed> feed = new ArrayList<>();

}
