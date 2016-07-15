package com.cusbee.yoki.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.dao.AccountDao;
import com.cusbee.yoki.entity.Account;
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.AccountModel;
import com.cusbee.yoki.repositories.AccountRepository;
import com.cusbee.yoki.service.AccountService;
import com.cusbee.yoki.utils.ErrorCodes;

/**
 * 
 * @author Dmytro Khodan
 * @date 28.06.2016
 * @project: yoki
 */

@Service(value = "UserService")
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDao userDao;

	@Autowired
	private AccountRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Add new account to database
	 */
	@Override
	@Transactional
	public void add(Account user) {
		this.userDao.add(user);
	}
	
	/**
	 * Return the list with all accounts
	 */
	@Override
	@Transactional
	public List<Account> getAll() {
		return userRepository.findAll();
	}

	/**
	 * Return account by requested ID
	 */
	@Override
	public Account getById(Long id) {
		return this.userDao.getById(id);
	}
	
	/**
	 * Method check request parameters for null pointer
	 * and if all is clear, in case of OperationStatus
	 * choose what will do(CREATE account or UPDATE account)
	 */
	@Transactional
	public Account parse(AccountModel request, CrudOperation operation)
			throws BaseException {

		if (Objects.isNull(request)) {
			throw new ApplicationException(ErrorCodes.User.EMPTY_REQUEST,
					"Request is empty");
		}
		
		Account user;
		switch (operation) {
		case CREATE:
			if (Objects.isNull(request.getUsername())
					|| Objects.isNull(request.getPassword())) {
				throw new ApplicationException(ErrorCodes.User.EMPTY_FIELDS,
						"Field Username or Password still empty");
			}
			if (Objects.isNull(request.getEmail())) {
				throw new ApplicationException(ErrorCodes.User.EMPTY_FIELDS,
						"Field Email can't be empty");
			}
			if (!isPresent(request.getUsername())) {
				throw new ApplicationException(ErrorCodes.User.ALREADY_EXIST,
						"User with this username already exist");
			}

			if (!checkForEmail(request)) {
				throw new ApplicationException(ErrorCodes.User.ALREADY_EXIST,
						"This email already used");
			}
			
			validate(request);
			user = new Account();
			user.setUsername(request.getUsername());
			user.setPassword(encryptPassword(request.getPassword()));
			user.setAuthority(request.getAuthority());
			user.setEmail(request.getEmail());
			user.setFirstname(request.getFirstname());
			user.setLastname(request.getLastname());
			user.setEnabled(Boolean.TRUE);
			return user;
		case UPDATE:
			
			if(Objects.isNull(request.getId())){
				throw new ApplicationException(ErrorCodes.User.EMPTY_FIELDS, "Field ID are empty");
			}
			user = getById(request.getId());
			
			if(Objects.isNull(user)) {
				throw new ApplicationException(ErrorCodes.User.EMPTY_REQUEST, "User with id:"+request.getId()+"are not present");
			}
			if(!Objects.isNull(request.getUsername())){
				if(!isPresent(request.getUsername())){
					throw new ApplicationException(ErrorCodes.User.ALREADY_EXIST, "Username " + request.getUsername() +" already present");
				}
				validUsername(request.getUsername());
				user.setUsername(request.getUsername());
			}
			if(!Objects.isNull(request.getPassword())){
				user.setPassword(encryptPassword(request.getPassword()));
			}
			if(!Objects.isNull(request.getAuthority())){
				user.setAuthority(request.getAuthority());
			}
			if(!Objects.isNull(request.getEmail())){
				validEmail(request.getEmail());
				user.setEmail(request.getEmail());
			}
			if(!Objects.isNull(request.getFirstname())){
				validFirstLastName(request.getFirstname());
				user.setFirstname(request.getFirstname());
			}
			if(!Objects.isNull(request.getLastname())){
				validFirstLastName(request.getLastname());
				user.setLastname(request.getLastname());
			}
			if(Objects.isNull(user.getOrders()) || user.getOrders().isEmpty()){
				user.setOrders(new ArrayList<Order>());
			}
			List<Order> orders = user.getOrders();
			user.getOrders().addAll(orders);
			return user;
		default:
			throw new ApplicationException(ErrorCodes.User.BAD_REQUEST,
					"Method undefined your move");
		}
	}

	/**
	 * method encrypt password before save account in database
	 * @param password
	 * @return
	 */
	public String encryptPassword(String password) {
		return passwordEncoder.encode(password);
	}

	/**
	 * Method check if requested email doesn't not exist in database for this moment
	 * @param request
	 * @return
	 */
	protected boolean checkForEmail(AccountModel request) {
		return userRepository.validateAccount(request.getEmail()) == null;
	}

	/**
	 * Method block or unblock account
	 */
	@Override
	@Transactional
	public void activation(Long id, CrudOperation operation)
			throws BaseException {
		
		Account user = getById(id);
		if(Objects.isNull(user)){
			throw new ApplicationException(ErrorCodes.User.EMPTY_REQUEST, "User is not present");
		}
		switch (operation) {
		case BLOCK:
			user.setEnabled(Boolean.FALSE);
			userDao.add(user);
			break;
		case UNBLOCK:
			user.setEnabled(Boolean.TRUE);
			userDao.add(user);
			break;
		default:
			throw new ApplicationException(ErrorCodes.User.BAD_REQUEST,
					"Method undefined your move");
		}
	}

	/**
	 * Method check if existing user is enabled(active/unblocked) in database
	 */
	@Override
	public void availability(String username) throws BaseException {
		if(userRepository.availability(username)==null){
			throw new ApplicationException(ErrorCodes.User.USER_UNVAILABLE, "User are blocked");
		}
	}
	
	protected boolean isPresent(String username) throws BaseException {
		if(username==null){
			return false;
		}
		return userRepository.findByUsername(username)==null;
	}
	
	/**
	 * Check if reference is not null
	 */
	public void isNull(Account user) throws BaseException {
		if(Objects.isNull(user)){
			throw new ApplicationException(ErrorCodes.User.EMPTY_REQUEST, "This user is missing");
		}
	}
	
	protected void validate(AccountModel request) throws BaseException {
		validEmail(request.getEmail());
		validFirstLastName(request.getFirstname());
		validFirstLastName(request.getLastname());
		validUsername(request.getUsername());
	}
	
	
	// VALIDATION PART
	protected boolean validEmail(String email) throws BaseException{
		Pattern pattern = Pattern.compile("^([a-z0-9.-]){1,20}[\\@]([a-z]){2,5}[\\.]([a-z]){2,4}$");
		Matcher matcher = pattern.matcher(email);
		if(!matcher.matches()){
			throw new ApplicationException(ErrorCodes.User.INVALID_EMAIL, "Email is not valid");
		}
		return matcher.matches();
	}
	
	protected boolean validFirstLastName(String name) throws BaseException {
		Pattern pattern = Pattern.compile("^([A-Z]){1}([a-z]){1,15}$");
		Matcher matcher = pattern.matcher(name);
		if(!matcher.matches()) {
			throw new ApplicationException(ErrorCodes.User.IVALID_FIRST_OR_LAST_NAME, "Invalid first/lastname");
		}
		return matcher.matches();
	}
	
	protected boolean validUsername(String username) throws BaseException{
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{5,15}$");
		Matcher matcher = pattern.matcher(username);
		if(!matcher.matches()){
			throw new ApplicationException(ErrorCodes.User.INVALID_USERNAME, "Invalid username. Username include only alphabet symbols and numbers");
		}
		return matcher.matches();
	}
 }
