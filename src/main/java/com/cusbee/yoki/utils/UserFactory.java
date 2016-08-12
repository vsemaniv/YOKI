package com.cusbee.yoki.utils;

import org.springframework.security.core.authority.AuthorityUtils;

import com.cusbee.yoki.entity.Account;
import com.cusbee.yoki.security.YokiUser;

/**
 * 
 * @author Dmytro Khodan
 * @date 09.07.2016
 * @project: yoki
 */
public class UserFactory {

	public static YokiUser create(Account user) {
		return new YokiUser(user.getId(), user.getUsername(),
				user.getPassword(), user.getEmail(),
				user.getLastPasswordResetDate(),
				AuthorityUtils.commaSeparatedStringToAuthorityList(user
						.getAuthority()));
	}
}
