package com.pickpick.server.member.dto;

import com.pickpick.server.member.domain.enums.PublicStatus;
import com.pickpick.server.member.domain.enums.ShareStatus;
import jakarta.validation.constraints.NotEmpty;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

public class MemberRequestDto {


	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UserSignupDto {
		private String name;
		private String email;
		private String password;
		private String phoneNum;
		private Optional<MultipartFile> uploadImg;
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
		private Optional<MultipartFile> uploadImg;
		private String publicStatus;
		private String shareStatus;
	}

	@Data
	@Builder
	public static class UpdateUserRequestDto {
		@NotEmpty
		private String name;
		private String imgUrl;
		private String publicStatus;
		private String shareStatus;
	}


}
