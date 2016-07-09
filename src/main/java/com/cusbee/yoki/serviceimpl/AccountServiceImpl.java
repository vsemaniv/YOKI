package com.cusbee.yoki.serviceimpl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cusbee.yoki.dao.AccountDao;
import com.cusbee.yoki.entity.Account;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.AccountModel;
import com.cusbee.yoki.repositories.UserRepository;
import com.cusbee.yoki.service.AccountService;
import com.cusbee.yoki.utils.AccountOperations;
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
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Add new account to database
	 */
	@Override
	public void add(Account user) {
		this.userDao.add(user);
	}
	
	/**
	 * Method check request parameters for null pointer
	 * and if all is clear, in case of OperationStatus
	 * choose what will do(CREATE account or UPDATE account)
	 */
	public Account parse(AccountModel request, AccountOperations operation)
			throws BaseException {

		if (Objects.isNull(request)) {
			throw new ApplicationException(ErrorCodes.User.EMPTY_REQUEST,
					"Request is empty");
		}

		if (Objects.isNull(request.getUsername())
				|| Objects.isNull(request.getPassword())) {
			throw new ApplicationException(ErrorCodes.User.EMPTY_FIELDS,
					"Field Username or Password still empty");
		}

		if (Objects.isNull(request.getEmail())) {
			throw new ApplicationException(ErrorCodes.User.EMPTY_FIELDS,
					"Field Email can't be empty");
		}

		Account user = userRepository.findByUsername(request.getUsername());

		switch (operation) {
		case CREATE:
			if (!Objects.isNull(user)) {
				throw new ApplicationException(ErrorCodes.User.ALREADY_EXIST,
						"User with this username already exist");
			}

			if (!validateAccount(request)) {
				throw new ApplicationException(ErrorCodes.User.ALREADY_EXIST,
						"This email already used");
			}
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
			user.setUsername(request.getUsername());
			user.setPassword(encryptPassword(request.getPassword()));
			user.setAuthority(request.getAuthority());
			user.setEmail(request.getEmail());
			user.setFirstname(request.getFirstname());
			user.setLastname(request.getLastname());
			user.setEnabled(Boolean.TRUE);
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
	boolean validateAccount(AccountModel request) {
		return userRepository.validateAccount(request.getEmail()) == null;
	}

	/**
	 * Return the list with all accounts
	 */
	@Override
	public List<Account> getAll() {
		return this.userDao.getAll();
	}

	/**
	 * Return account by requested ID
	 */
	@Override
	public Account getById(Long id) {
		return this.userDao.getById(id);
	}

	/**
	 * Method block or unblock account
	 */
	@Override
	public void activation(Account user, AccountOperations operation)
			throws BaseException {
		
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
	
	/**
	 * Check if reference is not null
	 */
	public void isNull(Account user) throws BaseException {
		if(Objects.isNull(user)){
			throw new ApplicationException(ErrorCodes.User.EMPTY_REQUEST, "This user is missing");
		}
	}
}
