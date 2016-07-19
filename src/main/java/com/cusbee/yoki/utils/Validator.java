package com.cusbee.yoki.utils;

import com.cusbee.yoki.entity.Account;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.AccountModel;
import com.cusbee.yoki.repositories.AccountRepository;
import com.cusbee.yoki.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    @Autowired
    private static AccountRepository accountRepository;

    @Autowired
    private static AccountService accountService;

    public static void validateAccountCreationRequest(AccountModel request) throws BaseException {
        String username = request.getUsername();
        String email = request.getEmail();
        //username validation
        if (Objects.isNull(username)
                || Objects.isNull(request.getOldPassword())) {
            throw new ApplicationException(ErrorCodes.User.EMPTY_FIELDS,
                    "Field Username or Password still empty");
        }
        if (accountRepository.findByUsername(username) != null) {
            throw new ApplicationException(ErrorCodes.User.ALREADY_EXIST,
                    "User with this username already exist");
        }
        //email validation
        if (Objects.isNull(email)) {
            throw new ApplicationException(ErrorCodes.User.EMPTY_FIELDS,
                    "Field Email can't be empty");
        }
        if (accountRepository.validateAccount(email) != null) {
            throw new ApplicationException(ErrorCodes.User.ALREADY_EXIST,
                    "This email already used");
        }
        validateAccountFields(request, true);
    }

    public static Account validateAccountUpdateRequest(AccountModel request) throws BaseException {
        if (Objects.isNull(request.getId())) {
            throw new ApplicationException(ErrorCodes.User.EMPTY_FIELDS, "Field ID are empty");
        }
        Account account = accountService.get(request.getId());
        if (Objects.isNull(account)) {
            throw new ApplicationException(ErrorCodes.User.EMPTY_REQUEST, "User with id:" + request.getId() + "are not present");
        }
        validateAccountFields(request, false);
        return account;
    }



    // *** REGEX VALIDATION METHODS ***
    public static boolean validateAccountUsername(String username) throws BaseException{
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{5,15}$");
        Matcher matcher = pattern.matcher(username);
        if(!matcher.matches()){
            throw new ApplicationException(ErrorCodes.User.INVALID_USERNAME, "Invalid username. Username include only alphabet symbols and numbers and should be at least 5 and at last 15 symbols long");
        }
        return matcher.matches();
    }

    public static boolean validateAccountPassword(String password) throws BaseException{
        Pattern pattern = Pattern.compile("^.{8,}$");
        Matcher matcher = pattern.matcher(password);
        if(!matcher.matches()){
            throw new ApplicationException(ErrorCodes.User.INVALID_PASSWORD, "Password is too weak. Username should include at least 8 symbols");
        }
        return matcher.matches();
    }

    public static boolean validateAccountEmail(String email) throws BaseException{
        Pattern pattern = Pattern.compile("^([a-z0-9.-]){1,20}[\\@]([a-z]){2,10}[\\.]([a-z]){2,4}$");
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()){
            throw new ApplicationException(ErrorCodes.User.INVALID_EMAIL, "Email is not valid");
        }
        return matcher.matches();
    }

    public static boolean validateAccountFirstLastName(String name) throws BaseException {
        Pattern pattern = Pattern.compile("^([A-Z]){1}([a-z]){1,15}$");
        Matcher matcher = pattern.matcher(name);
        if(!matcher.matches()) {
            throw new ApplicationException(ErrorCodes.User.IVALID_FIRST_OR_LAST_NAME, "Invalid first/lastname");
        }
        return matcher.matches();
    }

    public static void validateAccountFields(AccountModel request, boolean createOperation) throws BaseException {
        validateAccountUsername(request.getUsername());
        validateAccountEmail(request.getEmail());
        validateAccountFirstLastName(request.getFirstname());
        validateAccountFirstLastName(request.getLastname());
        if(createOperation || request.getNewPassword() != null) {
            validateAccountPassword(request.getNewPassword());
        }
    }

}
