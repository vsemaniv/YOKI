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
<<<<<<< HEAD
<<<<<<< HEAD
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.model.AccountModel;
=======
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.AccountModel;
import com.cusbee.yoki.repositories.AccountRepository;
import com.cusbee.yoki.service.NullPointerService;
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.model.AccountModel;
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
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
<<<<<<< HEAD
<<<<<<< HEAD
	private AccountService service;
	
	@ApiOperation(value="Creates user account")
	@RequestMapping(value="create", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<Account> create(@ApiModel(type=AccountModel.class, collection=false)@RequestBody AccountModel request) {
		Account user = service.saveAccount(request, CrudOperation.CREATE);
=======
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
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
	private AccountService service;
	
	@ApiOperation(value="Creates user account")
	@RequestMapping(value="create", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<Account> create(@ApiModel(type=AccountModel.class, collection=false)@RequestBody AccountModel request) {
		Account user = service.saveAccount(request, CrudOperation.CREATE);
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
		return new YokiResult<Account>(Status.SUCCESS, "User created successful", user);
	}
	
	@ApiOperation(value="Updates user account")
	@RequestMapping(value="update", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
<<<<<<< HEAD
<<<<<<< HEAD
	public YokiResult<Account> update(@ApiModel(type=AccountModel.class, collection=false) @RequestBody AccountModel request) {
		Account user = service.saveAccount(request, CrudOperation.UPDATE);
=======
	public YokiResult<Account> update(@ApiModel(type=AccountModel.class, collection=false) @RequestBody AccountModel request) throws BaseException {
		Account user = userService.parse(request, CrudOperation.UPDATE);
		userService.add(user);
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
	public YokiResult<Account> update(@ApiModel(type=AccountModel.class, collection=false) @RequestBody AccountModel request) {
		Account user = service.saveAccount(request, CrudOperation.UPDATE);
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
		return new YokiResult<Account>(Status.SUCCESS, "User updated successful", user);
	}
	
	@ApiOperation(value="Block user")
	@RequestMapping(value="block/{id}", method=RequestMethod.POST)
	public YokiResult<Account> block(@ApiParam(required=true, value="The id of the account that should be unblocked", name="id")
<<<<<<< HEAD
<<<<<<< HEAD
	 							  @PathVariable("id") Long id) {
		return new YokiResult<Account>(Status.SUCCESS, "User blocked successfully", service.processActivation(id, false));
=======
	 							  @PathVariable("id") Long id) throws BaseException{
		nullPointerService.isNull(id);
		userService.activation(id, CrudOperation.BLOCK);
		return new YokiResult<Account>(Status.SUCCESS, "User blocked successful", null);
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
	 							  @PathVariable("id") Long id) {
		return new YokiResult<Account>(Status.SUCCESS, "User blocked successfully", service.processActivation(id, false));
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
	}
	
	@ApiOperation(value="Unblock user")
	@RequestMapping(value="unblock/{id}", method=RequestMethod.POST)
	public YokiResult<Account> unblock(@ApiParam(required=true, value="The id of the account that should be unblocked", name="id")
<<<<<<< HEAD
<<<<<<< HEAD
									@PathVariable("id") Long id) {
		return new YokiResult<Account>(Status.SUCCESS, "User unblocked successfully", service.processActivation(id, true));
=======
									@PathVariable("id") Long id) throws BaseException {
		nullPointerService.isNull(id);
		userService.activation(id, CrudOperation.UNBLOCK);
		return new YokiResult<Account>(Status.SUCCESS, "User unblocked successful", null);
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
									@PathVariable("id") Long id) {
		return new YokiResult<Account>(Status.SUCCESS, "User unblocked successfully", service.processActivation(id, true));
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
	}
	
	@ApiOperation(value="Get all users")
	@RequestMapping(value="getAll", method=RequestMethod.GET)
<<<<<<< HEAD
<<<<<<< HEAD
	public List<Account> getAll() {
		return service.getAll();
=======
	public List<Account> getAll() throws BaseException {
		List<Account> users = accountRepository.findAll();
		return users;
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
	public List<Account> getAll() {
		return service.getAll();
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
	}
	
	@ApiOperation(value="get account by id")
	@RequestMapping(value="get/{id}", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
<<<<<<< HEAD
<<<<<<< HEAD
	public YokiResult<Account> get(@PathVariable("id") Long id) {
		return new YokiResult<Account>(Status.SUCCESS, "Successful request", service.get(id));
=======
	public YokiResult<Account> get(@PathVariable("id") Long id) throws BaseException {
		return new YokiResult<Account>(Status.SUCCESS, "Successful request", userService.get(id));
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
	public YokiResult<Account> get(@PathVariable("id") Long id) {
		return new YokiResult<Account>(Status.SUCCESS, "Successful request", service.get(id));
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
	}
}
