package com.cusbee.yoki.utils;

import org.springframework.security.core.authority.AuthorityUtils;

import com.cusbee.yoki.entity.User;
import com.cusbee.yoki.security.YokiUser;


public class UserFactory {

	public static YokiUser create(User user) {
		return new YokiUser(user.getId(), user.getUsername(),
				user.getPassword(), user.getEmail(),
				user.getLastPasswordResetDate(),
				AuthorityUtils.commaSeparatedStringToAuthorityList(user
						.getAuthority()));
	}
}
