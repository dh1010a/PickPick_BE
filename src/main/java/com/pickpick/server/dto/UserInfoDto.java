package com.pickpick.server.dto;

import com.pickpick.server.domain.enums.PublicStatus;
import com.pickpick.server.domain.enums.ShareStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoDto {

	private String email;
	private String name;
	private String phoneNum;
	private String imgUrl;
}
