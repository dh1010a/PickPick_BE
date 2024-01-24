package com.pickpick.server.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Users {

	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	private String phoneNum;

	private String imgUrl;

	@Enumerated(EnumType.STRING)
	@JsonIgnore
	private PublicStatus publicStatus;

	@JsonIgnore
	@Enumerated(EnumType.STRING)
	private ShareStatus shareStatus;

	private LocalDate createdAt;

	@JsonIgnore
	@OneToMany(mappedBy = "users")
	private List<SharedAlbum> sharedAlbums = new ArrayList<>();

	@Builder
	public Users(String name, String email, String password, String phoneNum, String imgUrl, PublicStatus publicStatus, ShareStatus shareStatus) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.phoneNum = phoneNum;
		this.imgUrl = imgUrl;
		this.publicStatus = publicStatus;
		this.shareStatus = shareStatus;
		this.createdAt = LocalDate.now();
	}
}
