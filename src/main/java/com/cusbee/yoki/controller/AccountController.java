package com.cusbee.yoki.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.dto.YokiResult.Status;
import com.cusbee.yoki.entity.User;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.UserModel;
import com.cusbee.yoki.service.UserService;
import com.cusbee.yoki.utils.AccountOperations;
import com.mangofactory.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiClass;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 
 * @author Dmytro Khodan
 * @date 28.06.2016
 * @project: yoki
 */

@ApiClass(value="Users account service")
@RestController
@RequestMapping(value="account", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

	@Autowired
	private UserService userService;
	
	@ApiOperation(value="Creates user account")
	@RequestMapping(value="create", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<User> login(@ApiModel(type=UserModel.class, collection=false)@RequestBody UserModel request) throws BaseException{
		User user = userService.parse(request, AccountOperations.CREATE);
		userService.add(user);
		return new YokiResult<User>(Status.SUCCESS, "User created successful", user);
	}
	
	@ApiOperation(value="Updates user account")
	@RequestMapping(value="update", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<User> update(@ApiModel(type=UserModel.class, collection=false) @RequestBody UserModel request) throws BaseException {
		
		User user = userService.parse(request, AccountOperations.UPDATE);
		userService.add(user);
		return new YokiResult<User>(Status.SUCCESS, "User updated successful", user);
	}
	
	@ApiOperation(value="Block user")
	@RequestMapping(value="block/{id}", method=RequestMethod.POST)
	public YokiResult<User> block(@ApiParam(required=true, value="The id of the account that should be unblocked", name="id")
	 							  @PathVariable("id") Long id) throws BaseException{
		User user = userService.getById(id);
		userService.activation(user, AccountOperations.BLOCK);
		return new YokiResult<User>(Status.SUCCESS, "User blocked successful", user);
	}
	
	@ApiOperation(value="Unblock user")
	@RequestMapping(value="unblock/{id}", method=RequestMethod.POST)
	public YokiResult<User> unblock(@ApiParam(required=true, value="The id of the account that should be unblocked", name="id")
									@PathVariable("id") Long id) throws BaseException {
		User user = userService.getById(id);
		userService.activation(user, AccountOperations.UNBLOCK);
		return new YokiResult<User>(Status.SUCCESS, "User unblocked successful", user);
	}
	
	@ApiOperation(value="Get all users")
	@RequestMapping(value="getAll", method=RequestMethod.GET)
	public List<User> getAll() throws BaseException {
		List<User> users = userService.getAll();
		return users;
	}
}
