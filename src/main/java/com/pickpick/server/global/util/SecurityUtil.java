package com.pickpick.server.global.util;

import com.pickpick.server.global.security.domain.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
	public static String getLoginEmail(){
		UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getMember().getEmail();
	}
}
