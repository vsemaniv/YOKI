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
import com.cusbee.yoki.entity.Account;
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.AccountModel;
import com.cusbee.yoki.repositories.AccountRepository;
import com.cusbee.yoki.service.NullPointerService;
import com.cusbee.yoki.service.AccountService;
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
	private AccountService userService;
	
	@Autowired
	private NullPointerService nullPointerService;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@ApiOperation(value="Creates user account")
	@RequestMapping(value="create", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<Account> login(@ApiModel(type=AccountModel.class, collection=false)@RequestBody AccountModel request) throws BaseException{
		Account user = userService.parse(request, CrudOperation.CREATE);
		userService.add(user);
		return new YokiResult<Account>(Status.SUCCESS, "User created successful", user);
	}
	
	@ApiOperation(value="Updates user account")
	@RequestMapping(value="update", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<Account> update(@ApiModel(type=AccountModel.class, collection=false) @RequestBody AccountModel request) throws BaseException {
		Account user = userService.parse(request, CrudOperation.UPDATE);
		userService.add(user);
		return new YokiResult<Account>(Status.SUCCESS, "User updated successful", user);
	}
	
	@ApiOperation(value="Block user")
	@RequestMapping(value="block/{id}", method=RequestMethod.POST)
	public YokiResult<Account> block(@ApiParam(required=true, value="The id of the account that should be unblocked", name="id")
	 							  @PathVariable("id") Long id) throws BaseException{
		nullPointerService.isNull(id);
		userService.activation(id, CrudOperation.BLOCK);
		return new YokiResult<Account>(Status.SUCCESS, "User blocked successful", null);
	}
	
	@ApiOperation(value="Unblock user")
	@RequestMapping(value="unblock/{id}", method=RequestMethod.POST)
	public YokiResult<Account> unblock(@ApiParam(required=true, value="The id of the account that should be unblocked", name="id")
									@PathVariable("id") Long id) throws BaseException {
		nullPointerService.isNull(id);
		userService.activation(id, CrudOperation.UNBLOCK);
		return new YokiResult<Account>(Status.SUCCESS, "User unblocked successful", null);
	}
	
	@ApiOperation(value="Get all users")
	@RequestMapping(value="getAll", method=RequestMethod.GET)
	public List<Account> getAll() throws BaseException {
		List<Account> users = accountRepository.findAll();
		return users;
	}
	
	@ApiOperation(value="get account by id")
	@RequestMapping(value="get/{id}", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<Account> get(@PathVariable("id") Long id) throws BaseException {
		return new YokiResult<Account>(Status.SUCCESS, "Successful request", userService.get(id));
	}
}
