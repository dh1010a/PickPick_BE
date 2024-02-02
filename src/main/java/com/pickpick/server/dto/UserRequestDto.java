package com.pickpick.server.dto;

import com.pickpick.server.domain.enums.PublicStatus;
import com.pickpick.server.domain.enums.ShareStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserRequestDto {


	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UserSignupDto {
		private String name;
		private String email;
		private String password;
		private String phoneNum;
		private String imgUrl;
		private PublicStatus publicStatus;
		private ShareStatus shareStatus;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CreateUserRequestDto {
		@NotEmpty
		private String name;
		@NotEmpty
		private String email;
		@NotEmpty
		private String password;
		@NotEmpty
		private String phoneNum;
		private String imgUrl;
		private String publicStatus;
		private String shareStatus;
	}


}
