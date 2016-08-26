package com.cusbee.yoki.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import com.cusbee.yoki.entity.Authority;
import com.cusbee.yoki.service.ActivationService;
import com.cusbee.yoki.service.ValidatorService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.dao.AccountDao;
import com.cusbee.yoki.entity.Account;
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.exception.ApplicationException;
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
    private AccountDao dao;

    @Autowired
    private AccountRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ValidatorService validatorService;

    @Autowired
    private ActivationService activationService;

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
        validatorService.validateRequestIdNotNull(id, Account.class);
        Account account = dao.get(id);
        validatorService.validateEntityNotNull(account, Account.class);
        return account;
    }

    /**
     * Method check request parameters for null pointer
     * and if all is clear, in case of OperationStatus
     * choose what will do(CREATE account or UPDATE account)
     */
    @Transactional
    public Account saveAccount(AccountModel request, CrudOperation operation) {
        Account account;
        switch (operation) {
            case CREATE:
                account = new Account();
                account.setPassword(encryptPassword(request.getNewPassword()));
                account.setAuthorities(new ArrayList<Authority>(request.getAuthorities()));
                account.setEnabled(Boolean.TRUE);
                break;
            case UPDATE:
                account = get(request.getId());
                if (StringUtils.isNotEmpty(request.getNewPassword()) && oldPasswordIsCorrect(account.getPassword(), request.getOldPassword())) {
                    account.setPassword(encryptPassword(request.getNewPassword()));
                }
                break;
            default:
                throw new ApplicationException(ErrorCodes.User.BAD_REQUEST,
                        "Unknown user operation");
        }
        account.setUsername(request.getUsername());
        account.setEmail(request.getEmail());
        account.setFirstname(request.getFirstname());
        account.setLastname(request.getLastname());
        return dao.save(account);
    }

    /**
     * Method block or unblock account
     */
    @Override
    @Transactional
    public Account processActivation(Long id, boolean activate) {
        Account account = get(id);
        activationService.processActivation(account, activate);
        return dao.save(account);
    }

    /**
     * Method check if existing user is enabled(active/unblocked) in database
     */
    @Override
    public void validateUserEnabled(String username) {
        if (userRepository.availability(username) == null) {
            throw new ApplicationException(ErrorCodes.User.USER_UNAVAILABLE, "User is blocked");
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

    /**
     * Checks whether old password, that was entered by user in order
     * to change the password is correct.
     * @param passwordFromDB - current password
     * @param enteredPassword - password entered by user
     * @return
     * @throws ApplicationException
     */
    public boolean oldPasswordIsCorrect(String passwordFromDB, String enteredPassword) {
        String encodedPassword = passwordEncoder.encode(enteredPassword);
        if(passwordFromDB.equals(encodedPassword)) {
            return true;
        } else {
            throw new ApplicationException(ErrorCodes.User.WRONG_OLD_PASSWORD, "Password entered in the \"Current password\" field is incorrect.");
        }
    }
}
