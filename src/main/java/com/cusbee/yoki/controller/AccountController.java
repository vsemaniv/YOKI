package com.cusbee.yoki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.dto.YokiResult.Status;
import com.cusbee.yoki.entity.User;
import com.cusbee.yoki.model.UserModel;
import com.cusbee.yoki.service.UserService;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * 
 * @author Dmytro Khodan
 * @date 28.06.2016
 * @project: yoki
 */

@Controller
@RequestMapping(value="account", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

	@Autowired
	private UserService userService;
	
	@ApiOperation(value="Create new account")
	@RequestMapping(value="create", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody YokiResult<User> login(@RequestBody UserModel user){
		
		User userr = new User();
		userr.setUsername(user.getUsername());
		userr.setPassword(user.getPassword());
		userr.setEmail(user.getEmail());
		userr.setEnabled(Boolean.TRUE);
		userr.setAuthority(user.getAuthority());
		userService.add(userr);
		return new YokiResult<User>(Status.SUCCESS, "User created successful", userr);
	}
}
