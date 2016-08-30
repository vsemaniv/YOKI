package com.cusbee.yoki.controller;

import java.util.List;

import com.cusbee.yoki.model.IdModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.dto.YokiResult.Status;
import com.cusbee.yoki.entity.Account;
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.model.AccountModel;
import com.cusbee.yoki.service.AccountService;
import com.mangofactory.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiClass;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@ApiClass(value="Users account service")
@RestController
@RequestMapping(value="account", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

	@Autowired
	private AccountService service;
	
	@ApiOperation(value="Creates user account")
	@RequestMapping(value="create", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<Account> create(@ApiModel(type=AccountModel.class, collection=false)@RequestBody AccountModel request) {
		Account user = service.saveAccount(request, CrudOperation.CREATE);
		return new YokiResult<Account>(HttpStatus.OK, "User created successful", user);
	}
	
	@ApiOperation(value="Updates user account")
	@RequestMapping(value="update", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<Account> update(@ApiModel(type=AccountModel.class, collection=false) @RequestBody AccountModel request) {
		Account user = service.saveAccount(request, CrudOperation.UPDATE);
		return new YokiResult<Account>(HttpStatus.OK, "User updated successful", user);
	}
	
	@ApiOperation(value="Block user")
	@RequestMapping(value="block/{id}", method=RequestMethod.POST)
	public YokiResult<Account> block(@ApiParam(required=true, value="The id of the account that should be unblocked", name="id")
	 							  @PathVariable("id") Long id) {
		return new YokiResult<Account>(HttpStatus.OK, "User blocked successfully", service.processActivation(id, false));
	}
	
	@ApiOperation(value="Unblock user")
	@RequestMapping(value="unblock/{id}", method=RequestMethod.POST)
	public YokiResult<Account> unblock(@ApiParam(required=true, value="The id of the account that should be unblocked", name="id")
									@PathVariable("id") Long id) {
		return new YokiResult<Account>(HttpStatus.OK, "User unblocked successfully", service.processActivation(id, true));
	}
	
	@ApiOperation(value="Get all users")
	@RequestMapping(value="getAll", method=RequestMethod.GET)
	public List<Account> getAll() {
		return service.getAll();
	}
	
	@ApiOperation(value="get account by id")
	@RequestMapping(value="get", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<Account> get(@RequestBody IdModel idModel) {
		return new YokiResult<Account>(HttpStatus.OK, "Successful request", service.get(idModel.getId()));
	}
}
