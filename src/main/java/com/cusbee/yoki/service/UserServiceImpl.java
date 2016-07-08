package com.cusbee.yoki.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cusbee.yoki.dao.UserDao;
import com.cusbee.yoki.entity.User;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.UserModel;
import com.cusbee.yoki.repositories.UserRepository;
import com.cusbee.yoki.utils.AccountOperations;
import com.cusbee.yoki.utils.ErrorCodes;

/**
 * 
 * @author Dmytro Khodan
 * @date 28.06.2016
 * @project: yoki
 */

@Service(value = "UserService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void add(User user) {
		this.userDao.add(user);
	}

	public User parse(UserModel request, AccountOperations operation)
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

		User user = userRepository.findByUsername(request.getUsername());

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
			user = new User();
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

	public String encryptPassword(String password) {
		return passwordEncoder.encode(password);
	}

	boolean validateAccount(UserModel request) {
		return userRepository.validateAccount(request.getEmail()) == null;
	}

	@Override
	public List<User> getAll() {
		return this.userDao.getAll();
	}

	@Override
	public User getById(Long id) {
		return this.userDao.getById(id);
	}

	@Override
	public void activation(User user, AccountOperations operation)
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

	@Override
	public void availability(String username) throws BaseException {
		if(userRepository.availability(username)==null){
			throw new ApplicationException(ErrorCodes.User.USER_UNVAILABLE, "User are blocked");
		}
	}
}
