package com.pickpick.server.dto;


import com.pickpick.server.domain.enums.PublicStatus;
import com.pickpick.server.domain.enums.ShareStatus;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class AddUserRequestDto {

	private String name;
	private String email;
	private String password;
	private String phoneNum;
	private String imgUrl;
	private PublicStatus publicStatus;
	private ShareStatus shareStatus;


}
