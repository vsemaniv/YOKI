package com.cusbee.yoki.utils;

import com.cusbee.yoki.entity.Account;
import com.cusbee.yoki.entity.BaseEntity;
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.AccountModel;
import com.cusbee.yoki.model.CategoryModel;
import com.cusbee.yoki.model.RequestModel;
import com.cusbee.yoki.repositories.AccountRepository;
import com.cusbee.yoki.repositories.CategoryRepository;
import com.cusbee.yoki.service.AccountService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AccountService accountService;

    private static Validator validator = new Validator();

    public void validateAccountParseRequest(AccountModel request, CrudOperation operation) throws BaseException {
        validateRequestNotNull(request);
        switch (operation) {
            case CREATE:
                String username = request.getUsername();
                String email = request.getEmail();
                //username validation
                if (StringUtils.isEmpty(username)
                        || Objects.isNull(request.getNewPassword())) {
                    throw new ApplicationException(ErrorCodes.User.EMPTY_FIELDS,
                            "Field Username or Password still empty");
                }
                if (accountRepository.findByUsername(username) != null) {
                    throw new ApplicationException(ErrorCodes.User.ALREADY_EXIST,
                            "User with this username already exist");
                }
                //email validation
                if (StringUtils.isEmpty(email)) {
                    throw new ApplicationException(ErrorCodes.User.EMPTY_FIELDS,
                            "Field Email can't be empty");
                }
                if (accountRepository.validateAccount(email) != null) {
                    throw new ApplicationException(ErrorCodes.User.ALREADY_EXIST,
                            "This email already used");
                }
                validateAccountFields(request, true);
                break;
            case UPDATE:
                validateAccountFields(request, false);
                break;
            default:
                throw new ApplicationException(ErrorCodes.User.BAD_REQUEST, "Unsupported operation");
        }
    }

    public void validateCategory(CategoryModel request, CrudOperation status) throws BaseException {
        validateRequestNotNull(request);
        if (StringUtils.isEmpty(request.getName())) {
            throw new ApplicationException(ErrorCodes.Category.EMPTY_FIELD,
                    "Category name should not be empty!");
        }
        if (status == CrudOperation.CREATE && categoryRepository.findByName(request.getName()) != null) {
            throw new ApplicationException(ErrorCodes.Category.ALREADY_EXIST,
                    "This category already exists");
        }
        validateRegexCategoryName(request.getName());
    }

    public void validateRequestNotNull(RequestModel request) throws BaseException {
        if (Objects.isNull(request)) {
            throw new ApplicationException(ErrorCodes.Common.EMPTY_REQUEST,
                    request.getClass().getSimpleName() + " Request is empty");
        }
    }

    public void validateRequestIdNotNull(Long id) throws BaseException {
        if(Objects.isNull(id)) {
            throw new ApplicationException(ErrorCodes.Common.EMPTY_REQUEST_ID,
                    "Request id is empty");
        }
    }

    public void validateEntityNotNull(BaseEntity entity) throws ApplicationException {
        if (Objects.isNull(entity)) {
            throw new ApplicationException(ErrorCodes.Common.NOT_EXIST,
                    "Could not find "+entity.getClass().getSimpleName()+" in database or failed to retrieve it");
        }
    }

    private void validateAccountFields(AccountModel request, boolean createOperation) throws BaseException {
        validateRegexAccountUsername(request.getUsername());
        validateRegexAccountEmail(request.getEmail());
        validateRegexAccountFirstLastName(request.getFirstname());
        validateRegexAccountFirstLastName(request.getLastname());
        if(createOperation || StringUtils.isNotEmpty(request.getNewPassword())) {
            validateRegexAccountPassword(request.getNewPassword());
        }
    }

    // *** REGEX VALIDATION METHODS ***
    private boolean validateRegexAccountUsername(String username) throws BaseException{
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{5,15}$");
        Matcher matcher = pattern.matcher(username);
        if(!matcher.matches()){
            throw new ApplicationException(ErrorCodes.User.INVALID_USERNAME, "Invalid username. Username include only alphabet symbols and numbers and should be at least 5 and at last 15 symbols long");
        }
        return matcher.matches();
    }

    private boolean validateRegexAccountPassword(String password) throws BaseException{
        Pattern pattern = Pattern.compile("^.{8,}$");
        Matcher matcher = pattern.matcher(password);
        if(!matcher.matches()){
            throw new ApplicationException(ErrorCodes.User.INVALID_PASSWORD, "Password is too weak. Username should include at least 8 symbols");
        }
        return matcher.matches();
    }

    private boolean validateRegexAccountEmail(String email) throws BaseException{
        Pattern pattern = Pattern.compile("^([a-z0-9.-]){1,20}[\\@]([a-z]){2,10}[\\.]([a-z]){2,4}$");
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()){
            throw new ApplicationException(ErrorCodes.User.INVALID_EMAIL, "Email is not valid");
        }
        return matcher.matches();
    }

    private boolean validateRegexAccountFirstLastName(String name) throws BaseException {
        Pattern pattern = Pattern.compile("^([A-Z]){1}([a-z]){1,15}$");
        Matcher matcher = pattern.matcher(name);
        if(!matcher.matches()) {
            throw new ApplicationException(ErrorCodes.User.IVALID_FIRST_OR_LAST_NAME, "Invalid first/lastname");
        }
        return matcher.matches();
    }

    private boolean validateRegexCategoryName(String name) throws BaseException {
        Pattern pattern = Pattern.compile("^([A-Z]){1}([a-z]){5,25}$");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }



    public static Validator getValidator() {
        return validator;
    }

    private Validator(){}

}
