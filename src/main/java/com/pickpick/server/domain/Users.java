package com.pickpick.server.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pickpick.server.domain.enums.PublicStatus;
import com.pickpick.server.domain.enums.ShareStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
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

	private LocalDate createdAt;

//	@JsonIgnore
//	@OneToMany(mappedBy = "users")
//	private List<SharedAlbum> sharedAlbums = new ArrayList<>();

}
