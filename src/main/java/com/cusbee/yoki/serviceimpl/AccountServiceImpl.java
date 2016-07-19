package com.cusbee.yoki.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.cusbee.yoki.utils.Validator;
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
    public Account get(Long id) {
        return this.userDao.get(id);
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
        Account account;
        switch (operation) {
            case CREATE:
                Validator.validateAccountCreationRequest(request);

                account = new Account();
                account.setUsername(request.getUsername());
                account.setPassword(encryptPassword(request.getNewPassword()));
                account.setAuthority(request.getAuthority());
                account.setEmail(request.getEmail());
                account.setFirstname(request.getFirstname());
                account.setLastname(request.getLastname());
                account.setEnabled(Boolean.TRUE);
                return account;
            case UPDATE:

                if (Objects.isNull(request.getId())) {
                    throw new ApplicationException(ErrorCodes.User.EMPTY_FIELDS, "Field ID are empty");
                }
                account = get(request.getId());
                if (Objects.isNull(account)) {
                    throw new ApplicationException(ErrorCodes.User.EMPTY_REQUEST, "User with id:" + request.getId() + "are not present");
                }
                if (!Objects.isNull(request.getNewPassword()) && oldPasswordIsCorrect(account.getPassword(), request.getOldPassword())) {
                    account.setPassword(encryptPassword(request.getNewPassword()));
                }
                if (Objects.isNull(account.getOrders()) || account.getOrders().isEmpty()) {
                    account.setOrders(new ArrayList<Order>());
                }
                Validator.validateAccountUpdateRequest(request);

                account.setAuthority(request.getAuthority());
                account.setEmail(request.getEmail());
                account.setFirstname(request.getFirstname());
                account.setLastname(request.getLastname());
                account.setUsername(request.getUsername());
                return account;
            default:
                throw new ApplicationException(ErrorCodes.User.BAD_REQUEST,
                        "Unknown user operation");
        }
    }

    /**
     * Method block or unblock account
     */
    @Override
    @Transactional
    public void activation(Long id, CrudOperation operation)
            throws BaseException {

        Account user = get(id);
        if (Objects.isNull(user)) {
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
    public void validateUserEnabled(String username) throws BaseException {
        if (userRepository.availability(username) == null) {
            throw new ApplicationException(ErrorCodes.User.USER_UNVAILABLE, "User is blocked");
        }
    }

    //TODO How is this method supposed to be used? Could not find usages.

    /**
     * Check if reference is not null
     */
    public void isNull(Account user) throws BaseException {
        if (Objects.isNull(user)) {
            throw new ApplicationException(ErrorCodes.User.EMPTY_REQUEST, "This user is missing");
        }
    }

    /**
     * method encrypt password before save account in database
     *
     * @param password
     * @return
     */
    @Override
    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean oldPasswordIsCorrect(String passwordFromDB, String enteredPassword) throws ApplicationException {
        String encodedPassword = passwordEncoder.encode(enteredPassword);
        if(passwordFromDB.equals(encodedPassword)) {
            return true;
        } else {
            throw new ApplicationException(ErrorCodes.User.WRONG_OLD_PASSWORD, "Password entered in the \"Current password\" field is incorrect.");
        }
    }

}
