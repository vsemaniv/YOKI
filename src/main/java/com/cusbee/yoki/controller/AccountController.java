package com.cusbee.yoki.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.entity.User;
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

	@ApiOperation(value="This method login user into system")
	@RequestMapping(value="login", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody YokiResult<User> login(){
		
		
		return new YokiResult<User>();
	}
}
